package jcox.security.rcp.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import jcox.security.rcp.api.DateService;
import jcox.security.rcp.api.SecurityService;

@Configuration
@ComponentScan(basePackages = {"jcox.security.rcp.service"})
@Import({SecurityConfig.class})
public class AppConfig {

	@Autowired
	DateService dateService;
	
	@Autowired
	SecurityService securityService;

	
	@Bean(name="/DateService")
	public HttpInvokerServiceExporter dateServiceRemotingExporter() {
		
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setService(dateService);
		exporter.setServiceInterface(DateService.class);
		return exporter;
	}	
	               
	@Bean(name = "/RemoteAuthenticationManager")
	public HttpInvokerServiceExporter authenticationManagerRemotingExporter() {

		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();

		exporter.setService(securityService);
		exporter.setServiceInterface(SecurityService.class);
		return exporter;
	}

	
}
