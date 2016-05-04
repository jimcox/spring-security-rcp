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


package jcox.security.rcp.client.config;


import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;


import jcox.security.rcp.api.DateService;
import jcox.security.rcp.api.SecurityService;

@Configuration
public class RemotingConfig {

	@Bean
	public HttpComponentsHttpInvokerRequestExecutor httpInvokerRequestExecutor() throws Exception {
		
		return new HttpComponentsHttpInvokerRequestExecutor(httpClient());
		
	}
	
	private HttpClient httpClient() throws Exception {
		
	    SSLContextBuilder builder = new SSLContextBuilder();
	    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		
	    SSLContext sslContext = builder.build();
	    
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
        		 builder.build(),
        		NoopHostnameVerifier.INSTANCE);
        
        HttpClient httpClient = HttpClients.custom()
        		.setSSLContext(sslContext)
        		.setSSLSocketFactory(sslSocketFactory)
        		//.setDefaultRequestConfig(defaultRequestConfig)
        		.build();
        
        return httpClient;
		
	}
	
    @Bean
    public HttpInvokerProxyFactoryBean dateServiceHttpInvokerProxyFactoryBean() throws Exception {
    	
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(DateService.class);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:8080/web/remoting/DateService");
        httpInvokerProxyFactoryBean.setHttpInvokerRequestExecutor(httpInvokerRequestExecutor());
        return httpInvokerProxyFactoryBean;
    }
	
    
    @Bean
    public HttpInvokerProxyFactoryBean authenticationManagerHttpInvokerProxyFactoryBean() throws Exception {
    	
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(SecurityService.class);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:8080/web/remoting/RemoteAuthenticationManager");
        httpInvokerProxyFactoryBean.setHttpInvokerRequestExecutor(httpInvokerRequestExecutor());
        return httpInvokerProxyFactoryBean;
    }

	
}
