package org.oiga.web.social.signin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(LogoutSuccessHandler.class.getName());
	
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String refererUrl = request.getHeader("Referer");
		logger.info("Cerrando sesion del usuario");
		super.onLogoutSuccess(request, response, authentication);
	}
}
