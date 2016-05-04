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

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SupplierConsumerSwingWorker<T,V> extends SwingWorker<T, V> {

	private final static Logger logger = LoggerFactory.getLogger(SupplierConsumerSwingWorker.class);
	
	private final Supplier<T> supplier; 
	
	private final Consumer<T> consumer;
	
	private final Consumer<ExecutionException> handler;
		
	public SupplierConsumerSwingWorker(Supplier<T> supplier, Consumer<T> consumer, Consumer<ExecutionException> handler) {
		super();
		this.supplier = supplier;
		this.consumer = consumer;
		this.handler = handler;
	
	}

	@Override
	protected T doInBackground() throws Exception {
		logger.debug("doInBackground()"  );
		return supplier.get();
	}

	@Override
	protected void done() {
	
		logger.debug("done()");
		try {
			consumer.accept(get());
		} catch (InterruptedException e) {

			logger.warn("Thread has been interrupted.");
			 Thread.currentThread().interrupt();
		
		} catch (ExecutionException ee) {
			handler.accept(ee);
		}
		 
	}
	
	

}
