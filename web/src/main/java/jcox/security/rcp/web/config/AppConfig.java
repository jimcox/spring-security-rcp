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
