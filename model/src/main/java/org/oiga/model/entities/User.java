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
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	@Transient
	private String signInProvider;
	private String imageUrl;
	@RelatedTo(type = "HAS_ROLES")
	@Fetch
	private Set<Role> roles;
	@RelatedToVia(type = "INTERACTS", direction=Direction.BOTH)
	@Fetch
	private Set<Interaction> interactions = new HashSet<Interaction>();
	@RelatedToVia(type = "HAS_VIEWED", direction=Direction.BOTH)
	@Fetch
	private Set<ViewInteraction> viewInteractions = new HashSet<ViewInteraction>();
	
	
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

	public Set<Interaction> getInteractions() {
		return interactions;
	}

	public void setInteractions(Set<Interaction> interactions) {
		this.interactions = interactions;
	}
}
