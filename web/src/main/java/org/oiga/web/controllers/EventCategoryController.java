
package org.oiga.web.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.oiga.services.EventCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.repackaged.com.google.common.collect.Lists;

@Controller
@RequestMapping("events/categories")
public class EventCategoryController {
	private static final Logger LOG = LoggerFactory.getLogger(EventCategoryController.class);
	
	@Autowired
	private EventCategoryService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showCatalog(Model model){
		model.addAttribute("all_categories", service.findParentCategories());
		model.addAttribute("categories", service.findParentCategories());
		return "categories/catalog";
	}
	
	
	@RequestMapping("**/{subcategory}")
	public String showCatalogCategory(@PathVariable String subcategory,
			Model model, HttpServletRequest request){
		ArrayList<String> url = Lists.newArrayList(request.getRequestURL().toString().split("/"));
		String parentCategory = url.remove(url.size() -1 );
		String parentUrl = StringUtils.join(url, '/');
		model.addAttribute("all_categories", service.findParentCategories());
		model.addAttribute("category", service.findByHyphen(subcategory));
		model.addAttribute("categories", service.findSubcategories(subcategory));
		model.addAttribute("active", subcategory);
		model.addAttribute("parent_url", parentUrl);
		model.addAttribute("parent_category", parentCategory);
		return "categories/catalog";
	}
}
