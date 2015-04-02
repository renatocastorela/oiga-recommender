<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.oiga.title" arguments="${event.name}" />		
	</jsp:attribute>
	<jsp:attribute name="openGraphMeta">
		<meta property="og:title" content="<spring:message code="label.oiga.title" arguments="${event.name}" />" />
		<meta property="og:site_name" content="Oiga" />
		<meta property="og:url" content="${pageContext.request.contextPath}/events/${event.nodeId}" />
		<meta property="og:description" content="${event.description}" />
		<c:choose>
			<c:when test="${event.picture }">
				<meta property="og:image" content="${event.picture}" />
			</c:when>
			<c:otherwise>
				<meta property="og:image" content="${pageContext.request.contextPath}/resources/img/oiga_logo.png" />
			</c:otherwise>
		</c:choose>
		<meta property="fb:app_id" content="546423088770268" />
		<meta property="og:locale" content="es_LA" />
	</jsp:attribute>
	<jsp:body>
	    		
      <div class="container">
	    <div class="row">
	    	<div class="col-md-8" id="event_details">
	    		<!--  Detalles ${event.name } ${event.venue.foursquareId}-->
	    		<h3 class="text-primary">
	    			<fmt:formatDate value="${event.startDate}" /> -
							<fmt:formatDate value="${event.endDate}" />
	    		</h3>
	    		<h1> ${event.name} </h1> 
	    		<div class="fb-like"
						data-href="${pageContext.request.contextPath}/events/${event.nodeId}"
						data-layout="standard" data-action="like" data-show-faces="false"
						data-share="true"></div>
				<div id="${event.nodeId}"  ></div>
				<hr>
	    		<dl>
					<dt>Fuente</dt>
  					<dd> <a href="${event.repository.url}">${event.repository.name}</a> </dd>
					<dt>Organiza</dt>
  					<dd> ${event.host}
						<dd>
  					
						<dt>Recinto</dt>
  					<dd>${event.location}
						<dd>
  					
						<dt>Descripcion</dt>
  					<dd>${event.description}</dd>
   					<dt>Tipo</dt>
  					<dd>${event.category.name}</dd>
  					<dt>Publico</dt>
  					<dd>${event.audience}</dd>
  					<dt>Precio</dt>
  					<dd>${event.ticketPrices}</dd>
  					<dt>Otros detalles</dt>
  					<dd>
  						<ul>
						<c:forEach items="${event.otherDetails}" var="o">
							<li>${o}</li>  
						</c:forEach>
						</ul> 
  					</dd>
				</dl>
	    		<input type="hidden" value="${ event.venue.foursquareId}"
						id="foursquareId" />
					
	    	</div>
			<div class="col-md-4" id="venue_details">
				<div id="map_container"></div>
				<address id="adress">
								
				</address>
  				<hr>
				<c:forEach items="${event.tags}" var="t">
					<span class="label label-default"> <c:out
								value="${t.keyword }"></c:out></span>  
				</c:forEach>
			</div>
	    </div>
     </div>
      <script type="text/javascript">
      	var foursquare_controller_url = ctx + "/venues/" + $("#foursquareId").val();

		console.debug("Venue request "+foursquare_controller_url);
      	$.getJSON(foursquare_controller_url)
      		.done(function( data ){
      			load_map(data);
      			$("#adress").html("<strong>"+data.location.address+"</strong><br>"
      					+data.location.crossStreet+"<br>"
      					+data.location.city+", "+data.location.state+" "+data.location.postalCode);
      	});
      	
      	function load_map(data)
      	{
      		var location = data.location;
          	L.mapbox.accessToken = 'pk.eyJ1IjoicmVuYXRvLWNhc3RvcmVsYSIsImEiOiJORm9oR0dBIn0.-Fq4UmqK-5x1e8ZyRyZneQ';
        	var map = L.mapbox.map('map_container', 'renato-castorela.map-hgnx2ffm').setView([location.lat, location.lng], 15);
      		L.mapbox.markerLayer({
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
      	$('#${event.nodeId}').raty({
      		  score : 3,
			  click: function(score, evt) {
					var id = $(this).attr("id");
					$.post(ctx+"/events/rated/"+id,{
						'score' : score, 
						'${_csrf.parameterName}' : '${_csrf.token}' 
					});
				  }
				});
		  
		
      </script>
	</jsp:body>
</l:base>
