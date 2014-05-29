package org.oiga.web.controllers;

import org.oiga.web.dto.SigninForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("navbar")
public class NavbarController {
	
	@RequestMapping("signinModal")
	public String showSigninModal(Model model) {
		model.addAttribute("signinForm", new SigninForm());
		return "navbar/signinModal";
	}
	
	@RequestMapping("signupModal")
	public String showSignupModal(){
		return "navbar/signupModal";
	}
	
	@RequestMapping("userFunctions")
	public String showUserFunctions(){
		return "navbar/userFunctions";
	}
}
