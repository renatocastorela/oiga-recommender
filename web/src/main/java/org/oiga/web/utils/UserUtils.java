package org.oiga.web.utils;

import java.util.Collections;
import java.util.HashSet;

import org.oiga.model.entities.Role;
import org.oiga.model.entities.User;
import org.oiga.web.dto.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
			user.setRoles(Collections.singleton(new Role("ROLE_OIGA_USER")));
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

	public static void signIn(User user) {
		logger.debug("Iniciando sesion con el usuario : {}", user.getFacebookUsername());
		HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for(Role rol:user.getRoles()){
			authorities.add(new SimpleGrantedAuthority( rol.getName()) );
		}
		UserDetails userDetails = new UserDetails(user.getFacebookUsername(), user.getPassword(), authorities);
		userDetails.setUser(user);
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), authorities));
	}
}
