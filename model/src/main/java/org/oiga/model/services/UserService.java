package org.oiga.model.services;

import org.oiga.model.entities.User;
import org.oiga.model.exceptions.DuplicateUserException;
import org.oiga.model.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
		
	@Transactional
	public User registerNewUser(User user) throws DuplicateUserException{
		logger.debug("Registrando nueva cuenta "+user.getEmail());
		User dbUser = userRepository.findByEmail(user.getEmail());
		if(dbUser != null){
			  logger.debug("El correo {} ya se encuentra registrado", user.getEmail());
			  throw new DuplicateUserException("The email address: " + user.getEmail() + " is already in use.");
		}
		logger.debug("Updating new user with email: {} ", user.getEmail());
		return userRepository.save(user);
	}
	
	public User registerNewUser(String firstName,
    		String lastName,
    		String email,
    		String password) throws DuplicateUserException{
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
				return registerNewUser(user);
		
	}
	/**
	 * Metodo que registra un evento de usuario.
	 * 
	 * @param userId Identificador de usuario ó IP 
	 */
	public void onView(String userId){
		//TODO:Agregar Soporte para usuarios oiga y de otros provedores como twiter o google+
		User u = userRepository.findByfacebookUsername(userId);
		if(u!=null){
			//Register user event
		}else{
			//Register Anonymous user event 
		}
		
	}
	
	public User findByUsername(String facebookUsername){
		User user = userRepository.findByfacebookUsername(facebookUsername);
		return user;
	}
	public UserRepository getUserRepository() {
		return userRepository;
	}
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
