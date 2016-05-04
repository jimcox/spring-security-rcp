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

package jcox.security.rcp.web.test;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

public class ServletPathConfigurer implements MockMvcConfigurer {


	private final static Logger logger = LoggerFactory.getLogger(ServletPathConfigurer.class);	

	private final String urlPattern;
	private final String replacement;


	public ServletPathConfigurer(String urlPattern, String replacement) {
		super();
		this.urlPattern = urlPattern;
		this.replacement = replacement;
	}


	@Override
	public RequestPostProcessor beforeMockMvcCreated(ConfigurableMockMvcBuilder<?> builder,
			WebApplicationContext context) {
		return new RequestPostProcessor(){

			@Override
			public MockHttpServletRequest postProcessRequest(
					MockHttpServletRequest request) {

				request.setRequestURI(StringUtils.replace(request.getRequestURI(), urlPattern, replacement, 1));
				request.setServletPath(StringUtils.replace(request.getServletPath(), urlPattern, replacement, 1));

				logger.debug("modified request URI is {}", request.getRequestURI());

				logger.debug("modified servletPath is {}", request.getServletPath());

				return request;
			}
		};
	}


	@Override
	public void afterConfigurerAdded(ConfigurableMockMvcBuilder<?> builder) {

	}


}
