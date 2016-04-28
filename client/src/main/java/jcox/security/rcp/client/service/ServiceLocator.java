package jcox.security.rcp.client.service;

import org.springframework.stereotype.Component;

import jcox.security.rcp.api.DateService;
import jcox.security.rcp.api.SecurityService;

@Component
public class ServiceLocator {

	public static DateService DATE_SERVICE;
	
	public static SecurityService AUTHENTICATION_MANAGER;
	
	public static DateService getDateService() {
		
		return DATE_SERVICE;
	}
	
	static void setDateService(DateService dateService) {
		DATE_SERVICE = dateService;
	}
	
	public static SecurityService getRemoteAuthenticationManager() {
		return AUTHENTICATION_MANAGER;
	}
	
	static void setRemoteAuthenticationManager(SecurityService authenticationManager){
		AUTHENTICATION_MANAGER = authenticationManager;
	}
}
