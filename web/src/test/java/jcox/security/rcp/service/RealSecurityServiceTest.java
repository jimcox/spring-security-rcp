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

package jcox.security.rcp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class RealSecurityServiceTest {
	
	@Mock UsernamePasswordAuthenticationToken authRequest;
	
	@Mock Authentication result;
	
	@Mock AuthenticationManager authenticationManager;
	
	@InjectMocks RealSecurityService securityService;
	
	@Test
	public void successfulAuthentication() {
		
		when(authenticationManager.authenticate(authRequest) ).thenReturn(result);
		
		Authentication actual = securityService.authenticate(authRequest);
		
		assertNotNull(actual);
		
		assertEquals(result, actual);
		
		assertEquals(result, SecurityContextHolder.getContext().getAuthentication());
		
	}
	
	@Test
	public void authenticationManagerReturnsNull() {
		
		when(authenticationManager.authenticate(authRequest) ).thenReturn(null);
		
		Authentication actual = securityService.authenticate(authRequest);
		
		assertNull(actual);
		assertNull(SecurityContextHolder.getContext().getAuthentication());
		
	}
	
	@Test(expected=BadCredentialsException.class)
	public void authenticationManagerThrowsAuthenticationException() {

		doThrow(new BadCredentialsException("bad credentials!")).when(authenticationManager).authenticate(authRequest);
		
		securityService.authenticate(authRequest);
		
	}
	
}
