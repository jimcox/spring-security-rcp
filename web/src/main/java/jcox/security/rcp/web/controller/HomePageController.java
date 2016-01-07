package jcox.security.rcp.web.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jcox.security.rcp.api.DateService;

@Controller
public class HomePageController {

	private final static Logger logger = LoggerFactory.getLogger(HomePageController.class);

	@Autowired
	DateService dateService;
	
	 @RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
	 public String homePage(Model model)
	 {
		 logger.debug("home page requested");
		 
		 Date date = dateService.getDate();
		 
		 model.addAttribute("datetime", date);
		 
		 return "home";

	 }
	
	
}
