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
