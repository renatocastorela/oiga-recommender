package org.oiga.recommender.resources;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.oiga.recommender.services.EventRecommenderService;

@ManagedBean
@Path("/events/recommendation")
public class EventRecommenderResource {
	@Inject
	private EventRecommenderService eventRecommenderService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/bylikes")
	public  Response recommendByLikes(@QueryParam("userId") int userId){
		String recomendation = eventRecommenderService.recommendByLikes(userId);
		return Response.ok(recomendation).build();
	}
}
