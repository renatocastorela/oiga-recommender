package org.oiga.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.EventCategory;
import org.oiga.model.repositories.EventCategoryRepository;
import org.oiga.model.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
	static private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private EventCategoryRepository eventCategoryRepository; 
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, ModelMap model){
		//TODO:Pasar esta carga inicial a ajax
		List<Event> events;
		List<Map<String, Object>> categories = new ArrayList<Map<String, Object>>();
		Pageable page = new PageRequest(0, 25);
		Page<Event> result = eventRepository.findAll(page);
		events = result.getContent();

		Iterable<Map<String, Object>> categoriesIterator = eventCategoryRepository.getCategoriesCount();
		CollectionUtils.addAll(categories, categoriesIterator.iterator());
		
		model.put("events", events);
		model.put("categories", categories);
		return "home";
	}
	
	@RequestMapping(value = "/filter/{category}", method = RequestMethod.GET)
	public String filterByCategory(@PathVariable String category, HttpServletRequest request, ModelMap model){
		//TODO:Pasar esta carga inicial a ajax
		List<Event> events = new ArrayList<Event>();
		List<Map<String, Object>> categories = new ArrayList<Map<String, Object>>();
		
		Iterable<Event> result = eventRepository.findByCategory(category);
		CollectionUtils.addAll(events, result.iterator());

		Iterable<Map<String, Object>> categoriesIterator = eventCategoryRepository.getCategoriesCount();
		CollectionUtils.addAll(categories, categoriesIterator.iterator());
		
		model.put("events", events);
		model.put("categories", categories);
		model.put("active", category);
		
		return "home";
	}

	@RequestMapping(value = "/details/{nodeId}", method = RequestMethod.GET)
	public String eventDetails(@PathVariable Long nodeId, HttpServletRequest request, ModelMap model){
		Event event = eventRepository.findOne(nodeId);
		model.put("event", event);
		return "detalles";
	}
}
