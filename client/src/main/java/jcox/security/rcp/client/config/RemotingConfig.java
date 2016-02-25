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
    public HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean() throws Exception {
    	
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(DateService.class);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:8080/web/remoting/DateService");
        httpInvokerProxyFactoryBean.setHttpInvokerRequestExecutor(httpInvokerRequestExecutor());
        return httpInvokerProxyFactoryBean;
    }
	
	
}