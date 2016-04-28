package jcox.security.rcp.client.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.SwingUtilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServiceInvocatorTest {

	
	@Mock Supplier<String> supplier; 
	
	//@Mock Consumer<String> consumer;
	
	ServiceInvocator<String> invocator;
	
	String actual = null;
	boolean wasEventDispatchThread = false;
	
	CountDownLatch consumerCompleted;
	
	/**
	 * Test that the producer hands off to the consumer
	 * AND the consumer completes it's work on the event dispatch thread.
	 * 
	 * @throws Exception
	 */
	@Test
	public void happyPath() throws Exception {
		
		when(supplier.get()).thenReturn("OK");
		
		consumerCompleted = new CountDownLatch(1);
				
		invocator = new ServiceInvocator<String>(supplier,

				(String in) -> {
					actual = in;
					wasEventDispatchThread = SwingUtilities.isEventDispatchThread();
					consumerCompleted.countDown();
				});
		
		invocator.invoke();
		
		consumerCompleted.await();
		
		assertEquals("OK", actual );
		assertTrue(wasEventDispatchThread);
	}
	
}
