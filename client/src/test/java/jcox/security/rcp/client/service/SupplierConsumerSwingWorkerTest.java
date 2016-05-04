/*
 
 Copyright 2016 James Cox <james.s.cox@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */

package jcox.security.rcp.client.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.anyString;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.SwingUtilities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class SupplierConsumerSwingWorkerTest {

	private final static Logger logger = LoggerFactory.getLogger(SupplierConsumerSwingWorkerTest.class);
	
	@Mock Supplier<String> supplier; 
	
	@Mock Consumer<String> consumer;
	
	SupplierConsumerSwingWorker<String, Object> worker;
	
	String actual = null;
	boolean wasEventDispatchThread = false;
	CountDownLatch latch = null;
	boolean exceptionHandled = false;

	
	final String production = "OK";
	
	
	@Before
	public void beforeEachTest() {
		 actual = null;
		 wasEventDispatchThread = false;
		 exceptionHandled = false;

		latch = new CountDownLatch(1);;
	}
	
	/**
	 * Test that the producer hands off to the consumer
	 * AND the consumer completes it's work on the event dispatch thread.
	 * 
	 * @throws Exception
	 */
	@Test(timeout=5000L)
	public void happyPath() throws Exception {
		
		when(supplier.get()).thenReturn(production);
		
		worker = new SupplierConsumerSwingWorker<String, Object>(supplier,

				(String in) -> {
					actual = in;
					wasEventDispatchThread = SwingUtilities.isEventDispatchThread();
					latch.countDown();
				},
				
				(ExecutionException ee) -> logger.info("Exception in Swing Worker", ee)
				
				);
		
		worker.execute();
				
		latch.await();
		
		assertEquals(production, actual );
		assertTrue(wasEventDispatchThread);
	}
	
	/**
	 * Test that the handler processes Supplier Exceptions.
	 * 
	 * @throws Exception
	 */
	@Test(timeout=5000L)
	public void executionException() throws Exception {
		
		doThrow(new RuntimeException("RuntimeException!")).when(supplier).get();
		
		worker = new SupplierConsumerSwingWorker<String, Object>(supplier,

				consumer,
				
				(ExecutionException ee) -> {
					
					logger.info("Exception in Swing Worker", ee);
					exceptionHandled = true;
					latch.countDown();

				}
				
				);
		
		worker.execute();
		latch.await();
		assertTrue(exceptionHandled);
		verify(consumer,never()).accept(anyString());
		
	}
	
	
}
