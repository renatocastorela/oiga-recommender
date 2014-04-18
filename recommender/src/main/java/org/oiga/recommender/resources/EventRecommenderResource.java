package org.oiga.recommender.resources;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.oiga.recommender.services.EventRecommenderService;

@Path("/")
public class EventRecommenderResource {
	private final class RecommendedItemImplementation implements
			RecommendedItem {
		@Override
		public float getValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getItemID() {
			return 0;
		}
	}

	@Inject
	private EventRecommenderService eventRecommenderService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("bylikes")
	public  List<RecommendedItem>  recommendByLikes(@QueryParam("userId") int userId){
		List<RecommendedItem> recommendedItems;
		try {
			recommendedItems = eventRecommenderService.recommendByLikes(userId);
		}catch( NoSuchUserException e){
			RecommendedItem item = new RecommendedItem() {
				@Override
				public float getValue() {
					return 0;
				}
				@Override
				public long getItemID() {
					return 0;
				}
			};
			recommendedItems =  Collections.singletonList(item);
		} 
		catch (TasteException e) {
			throw new RuntimeException(e);
		}
		
		return recommendedItems;
	}
}
