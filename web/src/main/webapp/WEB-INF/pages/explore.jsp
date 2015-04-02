<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.homepage.title" />		
	</jsp:attribute>
	<jsp:body>
	
	<div id="events-result">
	</div>
	<div id="map-wrapper">
		<div id="map" class="map"><!-- Load MapBox Map--></div>
    </div>
	<div hidden="true" id="event-template">
		<div class='panel panel-danger display-none' uuid='{{uuid}}'>
			<div class='panel-heading'  >
					<h3 class='panel-title'> {{name}}</h3>
					</div>
				<div class='panel-body'>
	    		<dl>
	    			<dt>Fecha</dt>
	    			<dd>{{start_date}} - {{end_date}}<dd>
					<dt>Organiza</dt>
  					<dd> {{host}}<dd>
  					<dt>Recinto</dt>
  					<dd>{{location}}<dd>
				</dl>		
				<a href='${pageContext.request.contextPath}/events/details/{{uuid}}' target="_blank" class="btn btn-primary btn-xs">Detalles</a>
			</div>
		</div>
	</div>
	<div hidden="true" id="event-not-found">
		<div class='panel panel-danger display-none' uuid='{{uuid}}'>
				<div class='panel-body'>
	    		<p> No se encontraron eventos </p>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var map;
    var markerLayer;
    var template = $("#event-template").html();
	function push_state(form){
		if (typeof window.history.pushState == 'function') {
			console.debug("state");
			history.pushState({}, null, ctx+"/events/explore?"
					+"q="+encodeURIComponent(form.q.value)
					+"&lt="+encodeURIComponent(form.lt.value)
					+"&ln="+encodeURIComponent(form.ln.value)
					+"&place="+encodeURIComponent(form.place.value)
					+"&when="+encodeURIComponent(form.when.value)
					+"&start_date="+encodeURIComponent(form.start_date.value) 
					+"&end_date="+encodeURIComponent(form.end_date.value) );
			
		}
	}
	
	$("#search-form").submit(function(event){
		var form = this;
		console.debug("submiting");
		event.preventDefault();
		place =  $( "input[name=place]" ).val();
		reverse_geocoding(place, function(data){
				set_geoname(data);
				set_date_range();
				push_state(form);
				load_events();
		});
		
		
	});
	</script>
	<script type="text/javascript">
    function update_params_from_uri(){
    	$("input[name=q]").val(getParameterByName("q"));
    	$("select[name=when]").val(getParameterByName("when"));
    	$("input[name=place]").val(getParameterByName("place"));
    	$("input[name=lt]").val(getParameterByName("lt"));
    	$("input[name=ln]").val(getParameterByName("ln"));
    }

	function load_map(){
		load_events();
	}
	
	function parse_wkt(text)
	{
		var result={};
		var match = text.match(/\([^\(\)]+\)/g);
	   	if (match !== null) {
	        for (var i = 0; i < match.length; i++) {
	            tmp = match[i].match(/-?\d+\.?\d*/g);
	            if (tmp !== null) {
	            	/*Nota: Solo soporta puntos*/
	                for (j = 0, tmpArr = []; j < tmp.length; j+=2) {
	                    lng = Number(tmp[j]);
	                    lat = Number(tmp[j + 1]);
	                    result  = { coords: {
	        	        	latitude:   lat,
	                        longitude: lng
	                        } 
	                    };
	                }
	            }
	        }
	    }
		return result; 
	}
	
	function load_events_on_map(error, data) {
		var features = [];
		var items = [];
		var bounds = [];
		$.each(data.hits.hits, function(key, val) {
			var v = val._source;
			var coord = [ v.wkt[1], v.wkt[0] ]; 
			bounds.push(coord);
			/*Cargando features*/
			features.push({
				type : 'Feature',
				geometry : {
					type : 'Point',
					coordinates : v.wkt
				},
				properties : {
					'marker-color' : '#000',
					'marker-symbol' : 'star-stroked',
					'title' : v.name,
					'id' : v.uuid
	
				}
			});
			/*Cargando Items*/
			v.start_date = moment(v.start_date).calendar();
			v.end_date = moment(v.end_date).calendar();
			var output = Mustache.render(template, v);					
			items.push(output);
		});
		if(items.length == 0) { items.push( $("#event-not-found").html()); }
		if( bounds.length == 0){ bounds.push( [  19.428, -99.141 ]); }
		if(typeof map == 'undefined' ){
			L.mapbox.accessToken = 'pk.eyJ1IjoicmVuYXRvLWNhc3RvcmVsYSIsImEiOiJORm9oR0dBIn0.-Fq4UmqK-5x1e8ZyRyZneQ';
			map = L.mapbox.map('map', 'renato-castorela.map-hgnx2ffm')
				.fitBounds( bounds , {
					paddingTopRight : [ 400, 0 ]
			
				});
		}else{
			map.fitBounds(bounds);
		}
		if(typeof markerLayer != 'undefined'){
				markerLayer.clearLayers();
			}
		markerLayer = L.geoJson({
			type : 'FeatureCollection',
			features : features },
			{onEachFeature: function (feature, layer) {
			    	if (feature.properties) {
			        	layer.bindPopup(feature.properties.title);
			   		}
				}
			}).addTo(map); 
	
		 markerLayer.on('click', function(e) {
			map.panTo(e.layer.getLatLng());
			var uuid = e.layer.feature.properties.id;
			$('html, body').animate(
					{
						scrollTop : $(
								"div[uuid='" + uuid
										+ "']")
								.offset().top - 60
					}, 200);
			$("#events-result > div[uuid!='"
							+ uuid + "']")
					.removeClass("panel-primary");
			$("div[uuid='" + uuid + "']")
					.toggleClass("panel-primary");
		}); 
		 
		$("#events-result > div").slideUp();
		$("#events-result").empty();
		$("#events-result").append(items.join(" "));
		$("#events-result > div").slideDown();
		$("#events-result > div").click(function(e) {
			$(".panel-primary").removeClass("panel-primary");
			$(this).addClass("panel-primary");
			var uuid = $(this).attr("uuid");
			$('html, body').animate({
				scrollTop : $(this).offset().top - 60
			}, 200);
			markerLayer.eachLayer(function(e) {
				if (e.feature.properties.id == uuid) {
					console.debug(e.feature.properties.id);
					map.panTo(e.getLatLng());
					e.openPopup();
					return false;
				}
			});
		});
		$("#map").click(function(e) {
			$("#events-result > div").removeClass("panel-primary");
		});
}

function load_events() {
	var query =  	$("input[name=q]").val();
	var start_date =  $("input[name=start_date]").val();
	var end_date =  $("input[name=end_date]").val();
	var ln = $( "input[name=ln]" ).val();
	var lt = $( "input[name=lt]" ).val();
	
	client.search({
		index : 'events',
		body : {
			query : {
				filtered :{
					query : {
						bool : {
							should : [
						    	{ match_phrase : { name :{ query : query, slop : 6, boost : 2, analyzer : "spanish" }}},
						    	{ match : { name : { query : query, boost : 1.5 }}},
					            { match : { description : { query : query }}}, 
					            { match : { location : { query : query }}},
					            { match : { venue_name : { query : query }}}, 
					            { match : { host : { query : query, boost : 1.5 }}},
					            { match : { "categories.category" : { query : query }}}
							]
						}
					},
					filter : {
			            and : [
			                //{ range : { start_date : { from : start_date, to : end_date }}}, 
			                { geo_distance : { distance : "120km",
			                                   "event.wkt" : { lat : lt, lon : ln }}}]
					}
				}
			}	
		}
	}, load_events_on_map);
	
	/* 
	$.getJSON(ctx+"/events/search/like/",
			{
				q : $("input[name=q]").val(),
				lt : $("input[name=lt]").val(),
				ln : $("input[name=ln]").val(),
			},
			load_events_on_map); */
}

window.onpopstate = function (event) {
	  update_params_from_uri();
	  load_events();
	}
window.onload = function(event){
	update_params_from_uri();
	load_events();
}
</script>
</jsp:body>
</l:base>
