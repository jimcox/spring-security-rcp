package jcox.security.rcp.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import jcox.security.rcp.api.DateService;

@Configuration
@ComponentScan(basePackages = {"jcox.security.rcp.service"})
public class AppConfig {

	@Autowired
	DateService dateService;
	
	@Bean
	public HttpInvokerServiceExporter remotingExporter() {
		
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setService(dateService);
		exporter.setServiceInterface(DateService.class);
		return exporter;
	}	
	
}
