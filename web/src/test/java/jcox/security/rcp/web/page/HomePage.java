package jcox.security.rcp.web.page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage {

	private final static Logger logger = LoggerFactory.getLogger(HomePage.class);
	
	private final WebDriver driver;

	
	private HomePage(WebDriver driver) {

		this.driver = driver;
	}


	public static HomePage to(WebDriver driver, String hostAndContext) {
		
		String url = "http://" + hostAndContext + "/ui/home.html";
		logger.debug("navagating to {}", url);
		 driver.get(url);
		 
		 HomePage homePage = new HomePage(driver);		 
		 return homePage;
	}

	
	public boolean isCurrentPage() {
		return StringUtils.equals(driver.getTitle(), "Spring Security RCP demo");
	}

	
}
