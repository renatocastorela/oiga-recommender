package org.oiga.web.controllers;

import java.util.Collections;
import java.util.Locale;

import javax.validation.Valid;

import org.oiga.model.entities.Role;
import org.oiga.model.entities.User;
import org.oiga.model.exceptions.DuplicateUserException;
import org.oiga.model.services.UserService;
import org.oiga.web.dto.SignupForm;
import org.oiga.web.social.signin.SignInUtils;
import org.oiga.web.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    protected static final String ERROR_CODE_EMAIL_EXIST = "NotExist.user.email";
    protected static final String USER_ATTR = "user";
    protected static final String VIEW_NAME_REGISTRATION_PAGE = "user/signup";
    
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    
    
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signupForm(Model model) {
		if(!model.containsAttribute("signupForm")){
			model.addAttribute("signupForm", new SignupForm());
		}
		return "users/signup";
	}
    
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String signinForm(){
		return "users/signin";
	}
	
    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request){
    	if(formBinding.hasErrors()){
    		return null;
    	}
    	User user = registerNewUser(form, formBinding);
    	if(user != null){
    		UserUtils.signIn(user);
			return "redirect:/";
    	}
		return null;
    }
    
    @RequestMapping(value="/signin", method = RequestMethod.POST)
    public String signin(@RequestParam("email") String email, @RequestParam("password") String password,
    		Model model,
    		BindingResult formBinding){
    	try{
    		User user = userService.getUserRepository().findByEmail(email);
    		if(user == null){
    			throw new UsernameNotFoundException("Usuario no encontrado");
    		}
    		if(!user.getPassword().equals(password)){
    			throw new BadCredentialsException("Password erroneo");
    		}
    		UserUtils.signIn(user);
    		return "redirect:/";
    	}catch(UsernameNotFoundException e){
    		formBinding.rejectValue("email",  "error.users.notFound", 
    				messageSource.getMessage("error.users.notFound", 
    						new String[]{email}, 
    						Locale.getDefault()));
    	}catch(BadCredentialsException be){
    		formBinding.rejectValue("password",  "error.users.badCredentials", 
    				messageSource.getMessage("error.users.badCredentials", 
    						new String[]{password}, 
    						Locale.getDefault()));
    	}
    	
		return null;
    }
    
    private User registerNewUser(SignupForm form, BindingResult formBinding){
    	try{
    		User user = new User();
    		user.setFirstName(form.getFirstName());
    		user.setLastName(form.getLastName());
    		user.setEmail(form.getEmail());
    		user.setUsername(form.getEmail());
    		user.setPassword(form.getPassword());
    		user.setImageUrl("resources/img/defaultUserPic.jpg");
			user.setRoles(Collections.singleton(new Role("ROLE_OIGA_USER")));
    		userService.registerNewUser(user);
    		return user;
    	}catch(DuplicateUserException e){
    		formBinding.rejectValue("email",  "error.users.duplicateEmail", 
    				messageSource.getMessage("error.users.duplicateEmail", 
    						new String[]{form.getEmail()}, 
    						Locale.getDefault()));
    		return null;
    	}
    }
}
