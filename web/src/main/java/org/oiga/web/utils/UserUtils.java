package org.oiga.web.utils;

import org.oiga.model.entities.Role;
import org.oiga.model.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;

public class UserUtils {
	private static Logger logger = LoggerFactory.getLogger(UserUtils.class);
	
	 public static User prefillUser(Connection<?> connection){
	    	User user = null;
	    	
	    	if(connection != null ){
	    		UserProfile up = connection.fetchUserProfile();
	    		logger.debug("Fetching user profile {} ", up.getEmail());
	    		user = new User.Builder()
	    						.email(up.getEmail())
	    						.firstName(up.getFirstName())
	    						.lastName(up.getLastName())
	    						.build();
	    		Role role = new Role();
	    		role.setName("ROLE_OIGA_USER");
	    		user.setRole(role);
	    		try{
	    			Facebook f =  (Facebook) connection.getApi();
	    			FacebookProfile p = f.userOperations().getUserProfile();
	    			user.setFacebookThirdPartyId(p.getThirdPartyId());
	    			user.setFacebookUid(p.getId());
	    			user.setFacebookUsername(p.getUsername());
	    		}catch(Exception e){
	    			logger.error("There is a problem trying to fill facebook's user data: "+e.getMessage());
	    			throw e;
	    		}
	    	}
	    	return user;
	    }
}
