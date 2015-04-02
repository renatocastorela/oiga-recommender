package org.oiga.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("events/organizers")
public class EventOrganizerController {
	
	
	
	@RequestMapping("{organizer}/{uuid}")
	public String showCatalog(@PathVariable String organizer,
			@PathVariable String uuid, 
			Model model){
		model.addAttribute(organizer);
		model.addAttribute(uuid);
		return "organizers/catalog";
		
	}
}
