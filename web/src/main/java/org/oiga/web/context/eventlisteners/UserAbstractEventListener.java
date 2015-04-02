package org.oiga.web.context.eventlisteners;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.oiga.model.entities.User;
import org.oiga.services.UserEventService;
import org.oiga.services.UserService;
import org.oiga.web.dto.UserDetails;
import org.oiga.web.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class UserAbstractEventListener {
	private static final Logger LOG = LoggerFactory
			.getLogger(UserAbstractEventListener.class.getName());
	
	@Inject
	private HttpServletRequest request;
	@Inject
	private UserService userService;
	@Inject
	private UserEventService userEventService;

	public User getUser(){
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		HttpSession session = request.getSession(true);
		Cookie facebookUid = WebUtils.getCookie(request, "oiga.facebookUid");
		if(UserUtils.hasRole("ROLE_OIGA_USER")){
			LOG.debug("El usuario "+username+" a visto un evento con permisos: "+authentication.getAuthorities());
			UserDetails details = (UserDetails) authentication.getPrincipal();
			user = details.getUser();
		}else if( facebookUid != null ){
			LOG.debug("El usuario de facebook con facebook id "+facebookUid.getValue()+" a visto un evento ");
			user = userService.findByFacebookUid(facebookUid.getValue());
		}else if(UserUtils.hasRole("ROLE_ANONYMOUS")){
			LOG.debug("El usuario anonimo con session id "+session.getId()+" a visto un evento ");
			user = userService.findBySessionId(session.getId());
			if(user == null){
				user = userService.registerAnonymous(session.getId());
			}
		}
		return user;
	}

	public UserEventService getUserEventService() {
		return userEventService;
	}

	public void setUserEventService(UserEventService userEventService) {
		this.userEventService = userEventService;
	}
	
	
}
