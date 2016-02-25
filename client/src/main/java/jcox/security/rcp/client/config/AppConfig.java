package jcox.security.rcp.client.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RemotingConfig.class)
@ComponentScan("jcox.security.rcp.client.service")
public class AppConfig {

	
	
}
