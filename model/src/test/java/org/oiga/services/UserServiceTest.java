package org.oiga.services;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oiga.exceptions.DuplicateUserException;
import org.oiga.exceptions.NullEMailException;
import org.oiga.model.entities.Role;
import org.oiga.model.entities.User;
import org.oiga.repositories.UserRepository;
import org.oiga.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:db-context.xml")
public class UserServiceTest {
	@Autowired private Neo4jTemplate template;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testFindByUsername() {
		User user = userService.findByProviderId("647395325", "facebook");
		template.fetch(user.getRoles());
		System.out.println( "Usuario encontrado "+user.getFacebookUsername()
				+" ,"+user.getNodeId()+
				" Size : "+user.getRoles().size());
		for(Role rol:user.getRoles()){
			System.out.println( rol.getName() );
		}
	}
	
	@Test
	@Transactional
	public void testRegisterUser(){
		User user = new User();
		HashSet<Role> roles = new HashSet<Role>(); 
		roles.add(new Role("ROLE_OIGA_USER"));
		roles.add(new Role("ROLE_FACEBOOK_USER"));
		user.setRoles(roles);
		user.setUsername("borrame.username");
		user.setEmail("anonimo2@gmail.com");
		try {
			System.out.println("Registrando Usuario");
			userService.registerNewUser(user);
		} catch (DuplicateUserException | NullEMailException e) {
			new RuntimeException(e);
		}
	}

}
