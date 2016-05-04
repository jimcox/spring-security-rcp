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
