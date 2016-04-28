package jcox.security.rcp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jcox.security.rcp.api.SecurityService;

@Service
public class RealSecurityService implements SecurityService {

	private final static Logger logger = LoggerFactory.getLogger(RealSecurityService.class);
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Override
	public Authentication authenticate(UsernamePasswordAuthenticationToken authRequest) {
			
		logger.info("Trying to authenticate: {}", authRequest);

		Authentication result = authenticationManager.authenticate(authRequest);
		
		if(result == null) {
			return null;
		}
		
		SecurityContextHolder.getContext().setAuthentication(result);
		
		return result;
		
	}

}
