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

import javax.swing.JOptionPane;

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
		
		SupplierConsumerSwingWorker<T,Object> worker = new SupplierConsumerSwingWorker<T,Object>(

				supplier,
				consumer,
				(ExecutionException ee ) -> handleException(ee));
				
		worker.execute();		
			
	}
	
	
	private void handleException(ExecutionException ee ) {
		
		logger.info("Caught exception on Service Invocation", ee);
		
		Throwable rootCause = ExceptionUtils.getRootCause(ee); 
 	   
 	   if(rootCause instanceof NoHttpResponseException && is403(rootCause.getMessage())) {
 		   
 		   handleAuthn();
 		   
 	   } else {
 		   
 		  JOptionPane.showMessageDialog(null,
 				 rootCause.getMessage(),
				    "Service Error",
				    JOptionPane.ERROR_MESSAGE);		        	   

 	   }
		
	}
	
	//this is ugly.  It would be nice if NoHttpResponseException exposed the Response code
	private boolean is403(String message) {
		
		return StringUtils.contains(message, "403");
		
	}
	
	void handleAuthn() {

		logger.debug("Attempt Authn");
		LoginDialog login = new LoginDialog(ServiceInvocator.this);
		login.setVisible(true);
		
	}
}
