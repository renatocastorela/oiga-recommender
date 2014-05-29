package org.oiga.web.controllers;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

@Controller
@RequestMapping("reconnect")
public class ReconnectController {
	private static final Logger logger = LoggerFactory
			.getLogger(ReconnectController.class.getName());
	
	private ConnectionFactoryLocator connectionFactoryLocator;
	private	UsersConnectionRepository usersConnectionRepository;
	private SignInAdapter signInAdapter;
	
	@Inject
	public ReconnectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository usersConnectionRepository,
			SignInAdapter signInAdapter) {
		super();
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.usersConnectionRepository = usersConnectionRepository;
		this.signInAdapter = signInAdapter;
	}

	@RequestMapping(value = "/facebook", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody Map<String,String> reconnect(@RequestParam String accessToken,
			@RequestParam String userID,
			@RequestParam Long expiresIn,
			HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		FacebookProfile profile = new FacebookTemplate(accessToken).userOperations().getUserProfile();
		ConnectionData data = createConnectionData(profile, accessToken, expiresIn);
		Connection<Facebook>  conn = connectionFactoryLocator.getConnectionFactory(Facebook.class).createConnection(data);
		Set<String> users = usersConnectionRepository.findUserIdsConnectedTo("facebook",Collections.singleton(userID));
		if(users.size() == 1){
			usersConnectionRepository.createConnectionRepository(userID).updateConnection(conn);
		}else if(users.size() == 0){
			usersConnectionRepository.createConnectionRepository(userID).addConnection(conn);
		}
		signInAdapter.signIn(userID, conn, new ServletWebRequest(request, response));
		result.put("response", "sucess");
		logger.info("Se reconecto el usuario "+conn.fetchUserProfile().getName());
		return result;
		
	}
	
	
	
	private ConnectionData createConnectionData(FacebookProfile profile, String accessToken, long expiresIn){
		return new ConnectionData("facebook", 
				profile.getId(), 
				profile.getUsername(), 
				"http://facebook.com/profile.php?id=" + profile.getId(),
				"http://graph.facebook.com/v1.0/" + profile.getId() + "/picture",
				accessToken, null, null, new Date().getTime() +  expiresIn);
	}
}
	