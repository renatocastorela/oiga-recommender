package org.oiga.estructura.cultura.unam.persisters;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.oiga.estructura.model.beans.Documento;
import org.oiga.estructura.model.beans.Extraccion;
import org.oiga.model.entities.Event;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class EventPersister {
	private ObjectMapper mapper = new ObjectMapper();
	/*El nombre del archivo debe resolverse en la clase que instancia el persister */
	private String htmlFileName;
	private String eventFileName;
	public abstract List<Event> parseEvents(Extraccion extraccion);
	
	public void persist(){
		String eventFileName = getEventFileName();
		String htmlFileName = getHtmlFileName();
		if(htmlFileName == null){
			throw new RuntimeException("Falta especificar el nombre del archivo a cargar 'html_[domain]_[date].json'");
		}
		if(eventFileName == null){
			throw new RuntimeException("Falta especificar el nombre del archivo a guardar 'event_[domain]_[date].json'");
		}
		
		Extraccion extraccion;
		try {
			extraccion = readHtml(htmlFileName);
		}catch (IOException e) {
			 throw new RuntimeException("No se pudo leer el archivo con el html "+htmlFileName+", Error:"+e.getMessage());
		}
		List<Event> events = parseEvents(extraccion);
		try {
			mapper.writeValue(new File(eventFileName), events);
		} catch ( IOException e) {
			throw new RuntimeException("No se pudo escribir el archivo "+eventFileName+", Error:"+e.getMessage());
		}
		
	}
	private Extraccion readHtml(String htmlFileName) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(new File(htmlFileName), Extraccion.class);
	}
	protected ObjectMapper getMapper() {
		return mapper;
	}
	public String getHtmlFileName() {
		return htmlFileName;
	}
	public void setHtmlFileName(String htmlFileName) {
		this.htmlFileName = htmlFileName;
	}
	public String getEventFileName() {
		return eventFileName;
	}
	public void setEventFileName(String eventFileName) {
		this.eventFileName = eventFileName;
	}
}
