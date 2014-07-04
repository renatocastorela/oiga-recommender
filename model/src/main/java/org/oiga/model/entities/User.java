package org.oiga.model.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class User {
	@GraphId
	private Long nodeId;
	private String facebookUsername;
	private String facebookUid;
	private String facebookThirdPartyId;
	@Indexed(indexName = "user_email")
	private String email;
	private String sessionId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	//TODO: Agregar genero
	//TODO: Agregar direccion
	//TODO: Agregar fecha de nacimiento 
	@Transient
	private String signInProvider;
	private String imageUrl;
	@RelatedTo(type = "HAS_ROLES",  direction = Direction.BOTH)
	@Fetch
	private Set<Role> roles;
	//TODO: Validar si en realidad se necesita hacer un fetch de esta informacion 
	@RelatedToVia(type = "VIEWED", direction=Direction.BOTH)
	private Set<ViewInteraction> viewInteractions = new HashSet<ViewInteraction>();
	@RelatedToVia(type = "LIKED", direction=Direction.BOTH)
	private Set<LikeInteraction> likeInteractions = new HashSet<LikeInteraction>();
	@RelatedToVia(type = "RATED", direction=Direction.BOTH)
	private Set<RateInteraction> rateInteractions = new HashSet<RateInteraction>();
	
	
	public User() {
	}

	public String getFacebookUsername() {
		return facebookUsername;
	}

	public void setFacebookUsername(String facebookUsername) {
		this.facebookUsername = facebookUsername;
	}

	public String getFacebookUid() {
		return facebookUid;
	}

	public void setFacebookUid(String facebookUid) {
		this.facebookUid = facebookUid;
	}

	public String getFacebookThirdPartyId() {
		return facebookThirdPartyId;
	}

	public void setFacebookThirdPartyId(String facebookThirdPartyId) {
		this.facebookThirdPartyId = facebookThirdPartyId;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignInProvider() {
		return signInProvider;
	}

	public void setSignInProvider(String signInProvider) {
		this.signInProvider = signInProvider;
	}

	public String getUsername() {
		return username;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Set<ViewInteraction> getViewInteractions() {
		return viewInteractions;
	}

	public void setViewInteractions(Set<ViewInteraction> viewInteractions) {
		this.viewInteractions = viewInteractions;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Set<LikeInteraction> getLikeInteractions() {
		return likeInteractions;
	}

	public void setLikeInteractions(Set<LikeInteraction> likeInteractions) {
		this.likeInteractions = likeInteractions;
	}

	public Set<RateInteraction> getRateInteractions() {
		return rateInteractions;
	}

	public void setRateInteractions(Set<RateInteraction> rateInteractions) {
		this.rateInteractions = rateInteractions;
	}
	
}
