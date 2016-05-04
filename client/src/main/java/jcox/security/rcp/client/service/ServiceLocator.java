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
