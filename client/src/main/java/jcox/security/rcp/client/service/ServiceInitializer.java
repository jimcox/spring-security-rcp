package jcox.security.rcp.client.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jcox.security.rcp.api.DateService;

@Component
public class ServiceInitializer implements InitializingBean {

	@Autowired
	DateService dateService;

	@Override
	public void afterPropertiesSet() throws Exception {

		ServiceLocator.setDateService(dateService);
		
	}
	
}
