package org.oiga.web.dto;


import java.util.Collection;
import java.util.HashSet;

import org.oiga.model.entities.Role;
import org.oiga.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

public class UserDetails extends SocialUser {
	private static final long serialVersionUID = 1L;
	private User user;
	
	public UserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	public UserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static UserDetails toUserDetails(User user){
		HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for(Role rol:user.getRoles()){
			authorities.add(new SimpleGrantedAuthority( rol.getName()) );
		}
		UserDetails userDetails = new UserDetails(user.getEmail(), user.getPassword(), authorities);
		userDetails.setUser(user);
		return userDetails;
	}
}
