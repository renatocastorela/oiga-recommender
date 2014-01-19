package org.oiga.estructura.cultura.unam.extractors;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.oiga.estructura.model.beans.Documento;
import org.oiga.estructura.model.beans.Extraccion;
import org.oiga.estructura.utils.JsoupUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CulturaUnamRepositoryExtractor {
	public static Logger logger = LoggerFactory
			.getLogger(CulturaUnamRepositoryExtractor.class);
	private String pagesUrl = "http://www.cultura.unam.mx/Administracion/files/GestionBusqueda.html.php";
	private String eventUrl = "http://www.cultura.unam.mx/agenda/MostrarEvento.html.php?eve_id=";
	private String repoName = "Cultura UNAM";
	private String repoUrl = "http://www.cultura.unam.mx";
	private String fileName;
	private Map<String, String> params = new HashMap<String, String>();
	private final String FILE_NAME_EXP = "html_cultura_unam_%s.json";
	private static final SimpleDateFormat FILE_DATE_PREFIX_FORMAT = new SimpleDateFormat(
			"yyyy_MM");
	private static final Pattern PAGE_FUNCTION_PATTERN = Pattern
			.compile("\\((.*?)\\)");
	private static final Pattern EVENT_FUNCTION_PATTERN = Pattern
			.compile("mostrarEvento\\((.*?),.*?");
	
	public void setUp() {
		params.put("date", "");
		params.put("date2", "");
		params.put("fec", "mes");
		params.put("gen", "");
		params.put("operacion", "busqueda");
		params.put("publico", "");
		/*
		 * TODO: Nombre del archivo dependiente de los parametros de entrada
		 */
		fileName = String.format(FILE_NAME_EXP,
				FILE_DATE_PREFIX_FORMAT.format(new Date()));
		logger.info("Se extraera del repositorio \""+repoUrl+"\" y se guardara el archivo con el nombre: " + fileName);
	}
	
	public void extract() {
		setUp();

		/* Extraer Identificadores */
		List<String> ids = getIds();
		/* Por cada identificador generar un documento */
		List<Documento> docs = getDocuments(ids);
		/* Generar extraccion a persistir */
		Extraccion extraccion = new Extraccion();
		extraccion.setDocumentos(docs);
		extraccion.setFechaExtraccion(new Date());
		extraccion.setNombreRepositorio(repoName);
		extraccion.setUrlRepositorio(repoUrl);
		extraccion.setNombreArchivo(fileName);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(fileName), extraccion);
		} catch ( IOException e) {
			throw new RuntimeException("No se pudo escribir el archivo "+fileName+", Error:"+e.getMessage());
		}
	}

	public List<Documento> getDocuments(List<String> ids) {
		ArrayList<Documento> docs = new ArrayList<Documento>();
		for (String id : ids) {
			String url = eventUrl + id;
			try {
				Document doc = JsoupUtils.get(url);
				Element e = doc.select("fieldset[class=eventos-field]").first();
				Documento d = new Documento();
				d.setContenido(e.html());
				d.setFuente(url);
				docs.add(d);
				logger.info("Se extrajo el documento : '"+e.select("div[class=eventos-titulo]").text()+"'");
			} catch (IOException e) {
				throw new RuntimeException("Hubo un error al obtener la " + url
						+ ", error: " + e.getMessage());
			}
		}
		return docs;
	}

	public List<String> getIds() {
		ArrayList<String> ids = new ArrayList<String>();
		int pagesSize = getLastPageNumber();
		for (int i = 1; i <= pagesSize; i++) {
			try {
				Document doc = extractPage(i);
				Elements tags = doc.select("a[onclick~=mostrarEvento.*]");
				for (Element e : tags) {
					String jsFunc = e.attr("onclick");
					Matcher m = EVENT_FUNCTION_PATTERN.matcher(jsFunc);
					if (m.find()) {
						String id = m.group(1);
						if(!ids.contains(id)){
							ids.add(id);
						}
					} else {
						throw new RuntimeException(
								"No se pudo encontrar el id en la cadena : "
										+ jsFunc);
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(
						"Hubo un error al extraer la pagina " + i + ", error: "
								+ e.getMessage());
			}
		}
		logger.debug("Identificadores : "+ids);
		return ids;
	}

	private int getLastPageNumber() {
		int pageNumber = 0;
		try {
			Document d = extractPage(1);
			Elements els = d.select("a[onclick~=busqueda.enviarDatos(.*);]");
			Element el = els.select("a:matchesOwn(>>)").first();
			String input = el.attr("onclick");
			logger.debug("Cadena con el valor \"{}\" ", input);
			Matcher matcher = PAGE_FUNCTION_PATTERN.matcher(input);
			if (matcher.find()) {
				pageNumber = Integer.valueOf(matcher.group(1));
			} else {
				logger.error("No se encontro el valor esperado al extraer de la funcion : "
						+ input);
			}
			logger.debug("El valor de la extraccion es \"{}\" ", pageNumber);
		} catch (IOException e) {
			logger.error("No se pudo extraer el numero de paginas del repositorio : "
					+ e.getMessage());
		}
		return pageNumber;
	}

	private Document extractPage(int page) throws IOException {
		params.put("inicio", Integer.toString(page));
		return JsoupUtils.post(pagesUrl, params);
	}
}
