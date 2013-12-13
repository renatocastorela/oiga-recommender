package org.oiga.web.social.security;

import org.oiga.model.entities.User;
import org.oiga.model.exceptions.DuplicateUserException;
import org.oiga.model.services.UserService;
import org.oiga.web.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

public class UserConnectionSignUp implements ConnectionSignUp {
	private static Logger logger = LoggerFactory.getLogger(UserConnectionSignUp.class);
	@Autowired
	private UserService userService;

	@Override
	public String execute(Connection<?> connection) {
		String userName = null;
		try{
			UserProfile profile = connection.fetchUserProfile();
			userName = profile.getUsername();
			User user = UserUtils.prefillUser(connection);
			logger.debug("Signin up user '{}' ", user.getFacebookUsername() );
        	userService.registerNewUser(user);
        }catch(DuplicateUserException e){
        	  logger.error("User already registered : "+e.getMessage());
        }
		return userName;
	}

}
