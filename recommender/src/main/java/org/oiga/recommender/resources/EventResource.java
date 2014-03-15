package org.oiga.recommender.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/events")
public class EventResource {
	ObjectMapper mapper = new ObjectMapper();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/recommendation")
	public  Response getRecommendation(@QueryParam("userId") int userId){
		List<String> res = new ArrayList<String>();
		res.add("1");
		res.add("2");
		try {
			//No es necesario hacer esto, bastaria con enviar solo el puro texto y armar
			//La cadena manualmente
			String r = mapper.writeValueAsString(res);
			return Response.ok(r).build();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
