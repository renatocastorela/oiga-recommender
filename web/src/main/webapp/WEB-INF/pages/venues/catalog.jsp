<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>

<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.homepage.title" />		
	</jsp:attribute>
	<jsp:body>
		<jsp:include page="/WEB-INF/pages/events/box.jsp"></jsp:include>
		<div class="display-none" id="venue-box-template" >
			<h1><a href="{{canonicalUrl}}?ref=M3XC102K5CRRC4OZLGQTXWICDAQUCB2MWQY0Q1WADK2STNMU"
				class="fs-navy">{{name}}</a>
			</h1>
			<dl class="dl-horizontal">
				<dt>Telefono</dt>
				<dd>{{contact.formattedPhone}}</dd>
				<dt>Dirección</dt>
				<dd>{{location.address}}</dd>
				<dt>Categorias</dt>
				<dd>
				{{#categories}}
					<span class="label label-default">{{shortName}}</span>  
				{{/categories}}
				</dd>
				<dt>Pagina Web</dt>
				<dd><a href="{{url}}">{{url}}</a></dd>
			</dl>
		</div>
			<div class="jumbotron">
				<div class="container">
					<div class="row">
					  	<div class="col-xs-8  venue-details" >
						</div>
						<div class="col-xs-4 ">
							<div id="map-canvas" class="map-canvas"></div>
						</div>
					</div>
				</div>	
          	</div>
        <div class="container">	
			<div class="row events-container">
        	</div>
        </div>
        <input type="hidden" value="${foursquareId}" name="foursquareId">
        <script type="text/javascript">
        var template = $("#event-box-template").html();
    	
    	function load_venue_events(venueId){
    		var query = "START n=node(*) \
    			MATCH n-[:PERFORMED]->v, n-[:CATEGORIZED]-c, n-[:HOSTED]->o \
    			WHERE has(n.__type__) and n.__type__ = 'org.oiga.model.entities.Event' \
    			and ( v.foursquareId={venueId} ) \
    			return n,v,collect(c.name), collect(c.path), o" ;
    		run_cypher(
    				query,
    				{venueId : venueId},
    				function(response){
    					$.each(response.data, function(i, val) {
    						append_event_to_row(val, ".events-container");
    					});
    				}
    		);
    	}
    	function load_venue_details(venueId){
    		var template = $('#venue-box-template').html();
    		$.get('https://api.foursquare.com/v2/venues/'+venueId,
    				{
    					client_id : "M3XC102K5CRRC4OZLGQTXWICDAQUCB2MWQY0Q1WADK2STNMU",
    					client_secret : "EEVCZRRIOTLRJM5Z1JUXQVJ2L3RWLZ53D3YSOH1ZAI221ZQL",
    					v : "20140806"
    				},
    				function(data){
    					$('.venue-details')
    						.append(Mustache.render(template, data.response.venue));
    					load_map(data.response.venue);
    			});
    	}
    	function load_map(data)
      	{
      		var location = data.location;
      		L.mapbox.accessToken = 'pk.eyJ1IjoicmVuYXRvLWNhc3RvcmVsYSIsImEiOiJORm9oR0dBIn0.-Fq4UmqK-5x1e8ZyRyZneQ';
      		var map = L.mapbox.map('map-canvas', 'renato-castorela.map-hgnx2ffm').setView([location.lat, location.lng], 15);
      		L.mapbox.featureLayer({
      		    type: 'Feature',
      		    geometry: {
      		        type: 'Point',
      		        coordinates: [location.lng, location.lat ]
      		    },
      		    properties: {
      		        title: data.name
      		    }
      		}).addTo(map);
      	}
    	
    	$(function(){
    		var venueId = $("input[name='foursquareId']").val();
    		load_venue_details(venueId);
    		load_venue_events(venueId);
    		
    	});
        </script>
	</jsp:body>
</l:base>