package org.oiga.recommender.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/events")
public class EventResource {
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/recommendation")
	public  Response getRecommendation(@QueryParam("userId") int userId){
		List<String> res = new ArrayList<String>();
		res.add("1");
		res.add("2");
			
		return null;
	}
}
