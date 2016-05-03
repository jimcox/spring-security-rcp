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
