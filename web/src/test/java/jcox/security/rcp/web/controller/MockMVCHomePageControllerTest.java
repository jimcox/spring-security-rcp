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

package jcox.security.rcp.web.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jcox.security.rcp.web.config.AppConfig;
import jcox.security.rcp.web.config.MvcConfig;
import jcox.security.rcp.web.page.HomePage;
import jcox.security.rcp.web.test.ServletPathConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfig.class, AppConfig.class})
@WebAppConfiguration
public class MockMVCHomePageControllerTest {

	
	private WebDriver driver;

	@Autowired
	WebApplicationContext context;
	
	@Before
	public void setup() throws Exception {
		
		MockMvc mockMvc = MockMvcBuilders
				
				.webAppContextSetup(context)
				.apply(new ServletPathConfigurer("/ui", ""))

				//.apply(springSecurity())
				.alwaysDo(print())
				.build();
				
		
		driver = MockMvcHtmlUnitDriverBuilder
				.mockMvcSetup(mockMvc)
				.contextPath("/web")
				.useMockMvcForHosts("localhost", "http://localhost")
				.build();

	}

	@After
	public void destroy() {
		if(driver != null) {
			driver.close();
		}
	}
	
	
	@Test
	public void accessHomePage() {
		
		HomePage homePage = HomePage.to(driver, "localhost/web");
		
		assertTrue(homePage.isCurrentPage());
		
	}

	
	
	
	
}
