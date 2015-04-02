package org.oiga.repositories;

import org.oiga.model.entities.SimpleVenue;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface SimpleVenueRepository extends GraphRepository<SimpleVenue>{
	SimpleVenue findByUuid(String uuid);
}
