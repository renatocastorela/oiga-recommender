package org.oiga.web.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.oiga.model.entities.Event;
import org.oiga.model.repositories.EventCategoryRepository;
import org.oiga.model.repositories.EventRepository;
import org.oiga.web.utils.LocationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@Controller
public class HomeController {
	static private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private EventCategoryRepository eventCategoryRepository; 
	
	//TODO:Pasar esta carga inicial a ajax
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(
			@CookieValue(value = "location", required=false) String location, 
			HttpServletRequest request, 
			ModelMap model){
		try {
			logger.debug("Client IP "+request.getRemoteAddr()+",  "+request.getHeader("X-Forwarded-For")+", "+InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			logger.error("Host no conocido",e1);
		}
		//TODO:Pasar esta carga inicial a ajax
		if(location == null){
			location = LocationUtils.getUserLocation(request);
		}
		logger.debug("cookie location: "+location);
		HashMap<String, String> jsonLoc;
		//FIXME: Quitar esta horrible implementacion, pasar todo este codigo al cliente para resolver 
		//la ubicacion inicial
		double lt = 19.3200988;
		double ln = -99.1521845;
		try {
			jsonLoc = new ObjectMapper().readValue(location, new TypeReference<HashMap<String,String>>() {});
			lt = Double.valueOf( jsonLoc.get("latitude"));
			ln = Double.valueOf( jsonLoc.get("longitude"));
		}catch(Exception e){
			lt = 19.3200988;
			ln = -99.1521845;
			logger.error("Hubo un error al intentar cargar los mensajes: "+e.getMessage(), e);
		}
		
		List<Map<String, Object>> categories = new ArrayList<Map<String, Object>>();
//		Pageable page = new PageRequest(0, 25);
//		Page<Event> result = eventRepository.findAll(page);
		
		List<Event> events = new ArrayList<Event>();
		Iterable<Event> result = eventRepository.findByCategory("Danza");
		CollectionUtils.addAll(events, result.iterator());
		
		String function = String.format(EventRepository.WITHIN_DISTANCE, lt, ln, 25.0);
		logger.debug("Argumentos de entrada "+lt+", "+ln+" formato "+ function);
		//Iterable<Event> result = eventRepository.getLocation(function);
		
		//ArrayList<Event> events = Lists.newArrayList(result);
//		events = result.getContent();
		Iterable<Map<String, Object>> categoriesIterator = eventCategoryRepository.getCategoriesCount();
		categories = Lists.newArrayList(categoriesIterator);
		logger.debug("Se cargaron "+events.size()+" eventos");
		model.put("events", events);
		model.put("categories", categories);
		return "home";
	}
	
	@RequestMapping(value = "/filter/{category}", method = RequestMethod.GET)
	public String filterByCategory(@PathVariable String category, HttpServletRequest request, ModelMap model){
		//TODO:Pasar esta carga inicial a ajax
		List<Map<String, Object>> categories = new ArrayList<Map<String, Object>>();
		
		List<Event> events = new ArrayList<Event>();
		Iterable<Event> result = eventRepository.findByCategory(category);
		CollectionUtils.addAll(events, result.iterator());

		Iterable<Map<String, Object>> categoriesIterator = eventCategoryRepository.getCategoriesCount();
		CollectionUtils.addAll(categories, categoriesIterator.iterator());
		
		model.put("events", events);
		model.put("categories", categories);
		model.put("active", category);
		
		return "home";
	}

}
