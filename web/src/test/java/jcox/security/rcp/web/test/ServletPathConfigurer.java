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
