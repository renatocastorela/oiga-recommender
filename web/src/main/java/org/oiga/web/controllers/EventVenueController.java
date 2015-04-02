package org.oiga.web.controllers;

import org.oiga.model.entities.SimpleVenue;
import org.oiga.services.SimpleVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("events/venues")
public class EventVenueController {
	@Autowired
	private SimpleVenueService simpleVenueService;
	
	@RequestMapping("{name}/{uuid}")
	public String showCatalog(@PathVariable String name,
			@PathVariable String uuid,
			Model model){
		//TODO: Por la Estructura del api de foursquare sera necesario hacer la carga de los 
		//datos de foursquare del lado del servidor.
		SimpleVenue venue = simpleVenueService.findByUuid(uuid);				
		model.addAttribute("name", name);
		model.addAttribute("foursquareId", venue.getFoursquareId());
		return "venues/catalog";
		
	}

}
