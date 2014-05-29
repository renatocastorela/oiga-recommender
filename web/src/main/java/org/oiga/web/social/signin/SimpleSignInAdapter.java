package org.oiga.web.social.signin;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.oiga.model.entities.User;
import org.oiga.model.services.UserService;
import org.oiga.web.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

public class SimpleSignInAdapter implements SignInAdapter {
	static Logger logger = LoggerFactory.getLogger(SimpleSignInAdapter.class); 

	private final RequestCache requestCache;
	@Inject
	private UserService userService;
	
	@Inject
	public SimpleSignInAdapter(RequestCache requestCache) {
		logger.info("Sign Adapter iniciando");
		this.requestCache = requestCache;
	}

	
	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		logger.debug("Iniciando session en Oiga!! : '"+userId+"'");
		User user;
		if( connection.getKey().getProviderId().equals("facebook")){
			user = userService.getUserRepository().findByFacebookUid(userId);
		}else{
			user = userService.getUserRepository().findByEmail(userId);
		}
		UserUtils.signIn(user);
		addCookie(request);
		return extractOriginalUrl(request);
	}

	private String extractOriginalUrl(NativeWebRequest request) 
	{
		if(request == null){
			return null;
		}
		HttpServletRequest nativeReq = request
				.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse nativeRes = request
				.getNativeResponse(HttpServletResponse.class);
		SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
		if (saved == null) {
			return null;
		}
		requestCache.removeRequest(nativeReq, nativeRes);
		removeAutheticationAttributes(nativeReq.getSession(false));
		return saved.getRedirectUrl();
	}

	private void removeAutheticationAttributes(HttpSession session) {
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	private void addCookie(NativeWebRequest request){
        HttpServletResponse response = request.getNativeResponse(HttpServletResponse.class);
		Cookie connected = new Cookie("oiga.rememberMe", "true");
		connected.setMaxAge(60 * 60 * 24*360*10);
		connected.setPath("/");
		response.addCookie(connected);
	}

}
