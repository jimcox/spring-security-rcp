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

package jcox.security.rcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.awt.EventQueue;

import jcox.security.rcp.client.config.AppConfig;
import jcox.security.rcp.client.ui.ApplicationWindow;

public class Client {

	

	private final static Logger logger = LoggerFactory.getLogger(Client.class);
	
	
	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext ctx =
			     new AnnotationConfigApplicationContext(AppConfig.class);
		
		ctx.registerShutdownHook();

		EventQueue.invokeLater( () -> {
				try {
					ApplicationWindow window = new ApplicationWindow();
					window.setVisible(true);
				} catch (Exception e) {
					logger.error("Error Displaying Application window", e);
				}
			}
		);

		
	}
	
}
