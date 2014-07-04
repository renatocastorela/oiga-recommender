package org.oiga.searchengine;

import org.springframework.stereotype.Component;

@Component
public class LuceneQueryParser {
	
	public String parse(String freeTextQuery){
		//TODO: Detectar categoria 'Fields'
		//TODO: Detectar zonas 
		return "event_name=:' ' OR event_description:'' ";
	}
}
