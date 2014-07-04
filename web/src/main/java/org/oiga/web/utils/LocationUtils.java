package org.oiga.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LocationUtils {
	private static final Logger LOG = LoggerFactory
			.getLogger(LocationUtils.class.getName());
	
	public static String getLocation(String ip){
		Client client = ClientBuilder.newClient();
		String result;
		try {
		result =
				client.target("http://freegeoip.net/json").path(ip)
				.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		} catch (Exception e) {
			LOG.error("No se pudo obtener la ip desde freegeoip, probablemente el servidor este abajo",e);
			return "{}";
		}
		return result;
	}
	
	public static String getUserLocation(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarded-For") == null? request.getRemoteAddr(): request.getHeader("X-Forwarded-For");
		return getLocation(ip);
		
	}
}
