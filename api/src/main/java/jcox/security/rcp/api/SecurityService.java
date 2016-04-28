package jcox.security.rcp.api;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface SecurityService {

	public Authentication authenticate(UsernamePasswordAuthenticationToken authRequest);
	
}
