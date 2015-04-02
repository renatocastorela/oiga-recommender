package org.oiga.repositories;

import org.oiga.model.entities.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface UserRepository extends GraphRepository<User>{
	User findByEmail(String email);
	User findByfacebookUsername(String facebookUsername);
	@Query("start n=node(*) "
			+ "MATCH n WHERE has(n.__type__) "
			+ "AND n.__type__ = 'org.oiga.model.entities.User' "
			+ "AND n.facebookUid! = {0} return n ")
	User findByFacebookUid(String facebookUid);
	User findBySessionId(String sessionId);
}
