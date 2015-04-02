package org.oiga.services;

import java.util.ArrayList;
import java.util.List;

import org.oiga.model.entities.EventCategory;
import org.oiga.repositories.EventCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

@Service
public class EventCategoryService {
	@Autowired
	private EventCategoryRepository repository;

	public List<EventCategory> findParentCategories() {
		return toList(repository.findParentCategories());
	}
	
	public List<EventCategory> findSubcategories(String subcategory) {
		return toList(repository.findSubcategories(subcategory));
	}
	
	private List<EventCategory> toList(EndResult<EventCategory> result) {
		List<EventCategory> categories = new ArrayList<EventCategory>();
		for (EventCategory c : result) {
			categories.add(c);
		}
		return categories;
	}
	
	public EventCategory findByPath(String path){
		return repository.findByPath(path);
	}
	
	public EventCategory findByHyphen(String hyphen){
		return repository.findByHyphen(hyphen);
	}
	
	public List<EventCategory> findAll(){
		return toList(repository.findAll());
	}
}
