package org.oiga.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(HttpServletRequest request){
		System.out.println("Serving: "+request.getContextPath());
		return "home";
	}
	
	@RequestMapping("/home")
	public String homeTest(HttpServletRequest request){
		System.out.println("Serving: "+request.getContextPath());
		return "home";
	}


}
