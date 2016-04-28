package jcox.security.rcp.client.service;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.NoHttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jcox.security.rcp.client.ui.LoginDialog;

public class ServiceInvocator<T>  {

	private final static Logger logger = LoggerFactory.getLogger(ServiceInvocator.class);
	
	private final Supplier<T> supplier; 
	
	private final Consumer<T> consumer;
	
	public ServiceInvocator(Supplier<T> supplier, Consumer<T> consumer) {
		super();
		this.supplier = supplier;
		this.consumer = consumer;
	}

	public void invoke( ) {
		
		SwingWorker<T,Object> worker = new SwingWorker<T, Object>() {

			@Override
			protected T doInBackground() throws Exception {
				logger.debug("doInBackground()"  );
				return supplier.get();
			}
			
		      @Override
		       protected void done() {
		           try {
		        	   
						logger.debug("done()"  );

		        	   consumer.accept(get());
		           
		       
		           } catch (Exception ex) {
		        	   
		        	   logger.info("Caught exception", ex);
		        	   
		        	   Throwable rootCause = ExceptionUtils.getRootCause(ex); 
		        	   
		        	   if(rootCause instanceof NoHttpResponseException && is403(ex.getMessage())) {
		        		   handleAuthn((NoHttpResponseException)rootCause);
		        	   } else {
		        		   
		        		   logger.info("bailing...");
		        		   
		        		   JOptionPane.showMessageDialog(null,
		        				    ex.getMessage(),
		        				    "Network error",
		        				    JOptionPane.ERROR_MESSAGE);		        	   
		        		   }
		        	   
		        	   
		           }
		       }

			private void handleAuthn(NoHttpResponseException ex) {

				logger.debug("Time to re-auth!");
				LoginDialog login = new LoginDialog(ServiceInvocator.this);
				login.setVisible(true);
				
			}
			
			//this is ugly.  It would be nice if NoHttpResponseException exposed the Response code
			private boolean is403(String message) {
				
				return StringUtils.contains(message, "403");
				
			}

		};
		
		worker.execute();
	}
	
}
