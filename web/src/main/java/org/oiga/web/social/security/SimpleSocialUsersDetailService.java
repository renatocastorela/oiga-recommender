package org.oiga.web.social.security;

import org.oiga.model.entities.User;
import org.oiga.repositories.UserRepository;
import org.oiga.web.dto.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class SimpleSocialUsersDetailService implements SocialUserDetailsService, UserDetailsService {
	
	static Logger logger = LoggerFactory.getLogger(SimpleSocialUsersDetailService.class); 
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		logger.debug("Cargando al usuario '"+userId+"' del repositorio local");
		User user = userRepository.findByEmail(userId);
		if(user == null){
			logger.error("Usuario No encontrado"+userId);
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		UserDetails userDetails = UserDetails.toUserDetails(user);
		return new SocialUser(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	}

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(
			String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user == null){
			logger.error("Usuario No encontrado"+username);
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		return UserDetails.toUserDetails(user);
	}

	
}
