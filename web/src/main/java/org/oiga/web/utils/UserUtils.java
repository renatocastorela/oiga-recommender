package org.oiga.web.utils;

import java.util.HashSet;

import org.oiga.model.entities.Role;
import org.oiga.model.entities.User;
import org.oiga.web.dto.UserDetails;
import org.oiga.web.exceptions.NullUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;

public class UserUtils {
	private static Logger logger = LoggerFactory.getLogger(UserUtils.class);

	public static User prefillUser(Connection<?> connection) {
		User user = new User();
		if (connection != null) {
			UserProfile up = connection.fetchUserProfile();
			logger.debug("Fetching user profile {} ", up.getUsername());
			//TODO: Marcar un error si no se puede acceder al correo al iniciar sesion con face
			user.setEmail(up.getEmail());
			user.setFirstName(up.getFirstName());
			user.setLastName(up.getLastName());
			user.setImageUrl(connection.getImageUrl());
			user.setUsername(up.getEmail());
			HashSet<Role> roles = new HashSet<Role>(); 
			roles.add(new Role("ROLE_OIGA_USER"));
			roles.add(new Role("ROLE_FACEBOOK_USER"));
			user.setRoles(roles);
			try {
				Facebook f = (Facebook) connection.getApi();
				FacebookProfile p = f.userOperations().getUserProfile();
				user.setFacebookThirdPartyId(p.getThirdPartyId());
				user.setFacebookUid(p.getId());
				user.setFacebookUsername(p.getUsername());
				user.setPassword(p.getId());
			} catch (Exception e) {
				logger.error("There is a problem trying to fill facebook's user data: "
						+ e.getMessage());
				throw new RuntimeException(e);
			}
		}
		return user;
	}

	public static void signIn(User user) throws NullUserException {
		if(user == null){
			throw new NullUserException("El usuario no puede ser nulo");
		}
		HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for(Role rol:user.getRoles()){
			authorities.add(new SimpleGrantedAuthority( rol.getName()) );
		}
		logger.debug("Authorities : "+authorities);
		UserDetails userDetails = new UserDetails(user.getFacebookUsername(), user.getPassword(), authorities);
		userDetails.setUser(user);
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), authorities));
	} 
	
	public static boolean hasRole(String role) {
	        SecurityContext context = SecurityContextHolder.getContext();
	        if (context == null)
	            return false;
	        Authentication authentication = context.getAuthentication();
	        if (authentication == null)
	            return false;

	        for (GrantedAuthority auth : authentication.getAuthorities()) {
	            if (role.equals(auth.getAuthority()))
	                return true;
	        }
	        return false;
	    }
}
