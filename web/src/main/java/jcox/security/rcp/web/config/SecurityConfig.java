package jcox.security.rcp.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.rcp.RemoteAuthenticationManager;
import org.springframework.security.authentication.rcp.RemoteAuthenticationManagerImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

//	@Bean
//	@Autowired
//	public RemoteAuthenticationManager remoteAuthenticationManager(AuthenticationManager authenticationManager) {
//		
//		RemoteAuthenticationManagerImpl remoteAuthenticationManager = new RemoteAuthenticationManagerImpl();
//		remoteAuthenticationManager.setAuthenticationManager(authenticationManager);
//		return remoteAuthenticationManager;
//	}

	 @Configuration
	 @Order(1)                                                        
	 public static class RemotingWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		 @Bean(name="authenticationManager")
		 @Override
		 public AuthenticationManager authenticationManagerBean() throws Exception {
			 return super.authenticationManagerBean();
		 }


		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.antMatcher("/remoting/**")
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/remoting/RemoteAuthenticationManager").permitAll()
			.antMatchers("/remoting/**").authenticated();
			//.and()
			//.httpBasic();
		}
	 }
	
	 @Configuration
	 @Order(2)                                                        
	 public static class BrowserWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http
			.csrf().disable()
			.authorizeRequests()
			//.antMatchers("/ui/media/**").permitAll()
			//.antMatchers("/ui/javascript/**").permitAll()
			.antMatchers("/ui/**").hasRole("USER")
			.and().formLogin().defaultSuccessUrl("/ui/home").permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
			}
	 	}

		@Autowired
		public void configureInMemoryAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception
		{
			auth.inMemoryAuthentication().withUser("guest").password("password").roles("USER");
		}

	
}
