package org.oiga.repositories;

import org.oiga.model.entities.Role;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface RoleRepository extends GraphRepository<Role>{
	Role findByName(String name);
}
