package org.oiga.services;

import java.util.Collections;

import org.oiga.exceptions.DuplicateUserException;
import org.oiga.exceptions.NullEMailException;
import org.oiga.model.entities.Role;
import org.oiga.model.entities.User;
import org.oiga.repositories.RoleRepository;
import org.oiga.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired private Neo4jTemplate template;
	@Autowired private RoleRepository roleRepository;
	@Autowired private UserRepository userRepository;
		
	@Transactional
	public User registerNewUser(User user) throws DuplicateUserException, NullEMailException{
		if(user.getEmail() == null){
			throw new NullEMailException("El email es nulo");
		}
		logger.debug("Registrando nueva cuenta "+user.getEmail());
		User dbUser = userRepository.findByEmail(user.getEmail());
		if(dbUser != null){
			  logger.debug("El correo {} ya se encuentra registrado", user.getEmail());
			  throw new DuplicateUserException("The email address: " + user.getEmail() + " is already in use.");
		}
		for(Role rol:user.getRoles()){
			Role roleFound  = roleRepository.findByName(rol.getName());
			if(roleFound != null){
				rol.setNodeId(roleFound.getNodeId());
			}else{
				logger.debug("No se encontro el rol "+rol.getName());
				roleRepository.save(rol);
			}
		}
		template.save(user);
		return user;
	}
	
	public User registerAnonymous(String sessionId) {
		User user = new User();
		user.setSessionId(sessionId);
		user.setFirstName("Anonymous");
		user.setRoles(Collections.singleton(new Role("ROLE_ANONYMOUS")));
		user.setUsername("anonymous");
		template.save(user);
		return user;
	}
	
	@Deprecated
	public User findByProviderId( String userId, String providerId){
		User user;
		if( providerId.equals("facebook")){
			user = userRepository.findByFacebookUid(userId);
		}else{
			user = userRepository.findByEmail(userId);
		}
		return user;
	}
	
	public User findByUsername(String facebookUsername){
		User user = userRepository.findByfacebookUsername(facebookUsername);
		return user;
	}
	
	public User findByFacebookUid(String facebookUid){
		return userRepository.findByFacebookUid(facebookUid);
	}
	
	public User findByfacebookUsername(String facebookUsername){
		User user = userRepository.findByfacebookUsername(facebookUsername);
		return user;
	}
	
	public User findBySessionId(String sessionId){
		return userRepository.findBySessionId(sessionId);
	}
	
	public UserRepository getUserRepository() {
		return userRepository;
	}
	
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
}
