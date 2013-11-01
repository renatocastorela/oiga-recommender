package org.oiga.vertex.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.oiga.vertex.credentials.FoursquareApiCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursquareVenueService implements VenueService<CompactVenue> {
	Logger logger = LoggerFactory.getLogger(FoursquareVenueService.class);
	
	private FoursquareApiCredential credential;
	private FoursquareApi api;
	
	public FoursquareVenueService() {
		super();
		init();
	}
	
	private void init(){
		credential = new FoursquareApiCredential();
		api = new FoursquareApi(credential.getClientId(), credential.getClientSecret(), "");
	}
	

	public List<CompactVenue> findByName(String name, String near){
		List<CompactVenue> venues = new ArrayList<>();
		try {
			logger.debug("Looking for.. "+name);
			Result<VenuesSearchResult> result = api.venuesSearch(near, name, 1, null, null, null, null, null);
			if(result.getMeta().getCode() == 200){
				logger.debug("Where found "+result.getResult().getVenues().length+" venues.");
				venues.addAll(Arrays.asList(result.getResult().getVenues()));
			}else{
				String error = "Error ocurred: \n"
						+" code: "+result.getMeta().getCode()+"\n"
						+" type: "+result.getMeta().getErrorType()+"\n"
						+" detail: "+result.getMeta().getErrorDetail();
				logger.error(error);
				throw new RuntimeException(error);
			}
			result.getResult();
		}catch(ArrayIndexOutOfBoundsException e){
			throw new RuntimeException("Venue not found");
		}
		catch (FoursquareApiException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return venues;
	}
	
	public CompactVenue findOneByName(String name, String near)
	{
		return findByName(name, near).get(0);
	}
}
