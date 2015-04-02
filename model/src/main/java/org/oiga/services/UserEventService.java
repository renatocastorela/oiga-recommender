package org.oiga.services;

import org.oiga.model.entities.LikeInteraction;
import org.oiga.model.entities.RateInteraction;
import org.oiga.model.entities.ViewInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserEventService {
	private static final Logger LOG = LoggerFactory
			.getLogger(UserEventService.class.getName());
	
	@Autowired
	private Neo4jTemplate template;
	
	
	@Transactional
	public ViewInteraction viewed(ViewInteraction interaction){
		LOG.debug("Creando una nueva interaccion del tipo 'view' entre "+
				interaction.getUser().getNodeId() + " y "+
				interaction.getEvent().getNodeId());
		return template.save(interaction);
	}
	
	@Transactional
	public LikeInteraction liked(LikeInteraction interaction){
		LikeInteraction prev = template.getRelationshipBetween(interaction.getUser(), 
				interaction.getEvent(), LikeInteraction.class, "LIKED");
		if(prev != null){
			interaction.setNodeId(prev.getNodeId());
			LOG.debug("Actualizando la interaccion del tipo 'like' entre "+
					interaction.getUser().getNodeId() + " y "+
					interaction.getEvent().getNodeId());

		}else{
			LOG.debug("Creando una nueva interaccion del tipo 'like' entre "+
					interaction.getUser().getNodeId() + " y "+
					interaction.getEvent().getNodeId());
		}
		return template.save(interaction);
	}
	
	@Transactional
	public RateInteraction rated(RateInteraction interaction){
		RateInteraction prev = template.getRelationshipBetween(interaction.getUser(), 
				interaction.getEvent(), RateInteraction.class, "RATED");
		if(prev != null){
			interaction.setNodeId(prev.getNodeId());
			LOG.debug("Actualizando interaccion del tipo 'rate' entre "+
					interaction.getUser().getNodeId() + " y "+
					interaction.getEvent().getNodeId());
		}else{
			LOG.debug("Creando una nueva interaccion del tipo 'rate' entre "+
					interaction.getUser().getNodeId() + " y "+
					interaction.getEvent().getNodeId());
		}
		return template.save(interaction);
	}
	
}
