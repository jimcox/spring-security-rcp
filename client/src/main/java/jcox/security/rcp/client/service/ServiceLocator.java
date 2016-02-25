package jcox.security.rcp.client.service;

import org.springframework.stereotype.Component;

import jcox.security.rcp.api.DateService;

@Component
public class ServiceLocator {

	public static DateService DATE_SERVICE;
	
	public static DateService getDateService() {
		
		return DATE_SERVICE;
	}
	
	static void setDateService(DateService dateService) {
		DATE_SERVICE = dateService;
	}
	
}
