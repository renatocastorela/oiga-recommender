<%@tag description="Base Pages template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Oiga Recommender - somethin </title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
  	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
  	<!-- Oiga 	-->
  	<link href="${pageContext.request.contextPath}/resources/css/oiga.css" rel="stylesheet" media="screen">
  	<link href="${pageContext.request.contextPath}/resources/css/map.css" rel="stylesheet" media="screen">
  	<!-- Mapbox -->
  	<link href='//api.tiles.mapbox.com/mapbox.js/v1.3.1/mapbox.css' rel='stylesheet' />
  	<!-- JQuery UI -->
  	<link href='http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css' rel='stylesheet' />
	<!--[if lte IE 8]>
    <link href='//api.tiles.mapbox.com/mapbox.js/v1.3.1/mapbox.ie.css' rel='stylesheet'>
  	<![endif]--> 
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
    <script src='//api.tiles.mapbox.com/mapbox.js/v1.3.1/mapbox.js'></script>
  </head>
  <body>
    <jsp:include page="/WEB-INF/layouts/navbar.jsp" />
    <jsp:doBody />
    <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script src='//api.tiles.mapbox.com/mapbox.js/v1.3.1/mapbox.js'></script>
	<script src="${pageContext.request.contextPath}/resources/js/geoPosition.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/geoPositionSimulator.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script type='text/javascript'>
			var map;
	     
	        var latitude;
            var longitude;
            var markerLayer;
	        var locations=new Array({ coords: {
	        	latitude:   19.54,
                longitude: -99.20
                } 
            });
			if(geoPosition.init()){  // Geolocation Initialisation
				geoPosition.getCurrentPosition(success_callback,error_callback,{enableHighAccuracy:true});
			}else{
				// You cannot use Geolocation in this device
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
			
		function load_events_on_map(data) {
			var features = [];
			var items = [];
			$
					.each(
							data,
							function(key, val) {
								if (val.venue.adress == null) {
									console.warn("Adress is null");
									return true;
								}
								var p = parse_wkt(val.venue.adress.wkt);
								features.push({
									type : 'Feature',
									geometry : {
										type : 'Point',
										coordinates : [ p.coords.longitude,
												p.coords.latitude ]
									},
									properties : {
										'marker-color' : '#000',
										'marker-symbol' : 'star-stroked',
										'title' : val.name,
										'id' : val.nodeId

									}
								});

								markerLayer = L.mapbox.markerLayer().addTo(map);
								markerLayer.setGeoJSON({
									type : 'FeatureCollection',
									features : features
								});

								/*Registrando eventos en la capa de marker layer*/
								markerLayer.on('click', function(e) {
									/*Centrando el mapa a la posicion del marker*/
									map.panTo(e.layer.getLatLng());
									var nodeId = e.layer.feature.properties.id;
									/*Scrolling to element*/
									$('html, body').animate(
											{
												scrollTop : $(
														"div[nodeid='" + nodeId
																+ "']")
														.offset().top - 60
											}, 200);
									$(
											"#events-result > div[nodeid!='"
													+ nodeId + "']")
											.removeClass("panel-primary");
									$("div[nodeid='" + nodeId + "']")
											.toggleClass("panel-primary");
								});
								/**/
								items
										.push("<div class='panel panel-default display-none' nodeid='"+val.nodeId+"'>"
												+ "<div class='panel-heading'  >"
												+ "<h3 class='panel-title'>"
												+ val.name
												+ "</h3>"
												+ "</div>"
												+ "<div class='panel-body'> "
												+ "<p>"
												+ val.name
												+ "</p>"
												+ "</div>" + "</div>"

										);
							});
			$("#events-result > div").slideUp();
			$("#events-result").empty();
			$("#events-result").append(items.join(" "));
			$("#events-result > div").slideDown();
			$("#events-result > div").click(function(e) {
				$(".panel-primary").removeClass("panel-primary");
				$(this).addClass("panel-primary");
				var nodeId = $(this).attr("nodeid");
				$('html, body').animate({
					scrollTop : $(this).offset().top - 60
				}, 200);
				markerLayer.eachLayer(function(e) {
					if (e.feature.properties.id == nodeId) {
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

			$.getJSON("events/find/" + latitude + "/" + longitude + "/",
					load_events_on_map);

		}

		// p : geolocation object
		function success_callback(p) {
			latitude = parseFloat(p.coords.latitude).toFixed(2);
			longitude = parseFloat(p.coords.longitude).toFixed(2);
			map = L.mapbox.map('map', 'renato-castorela.map-hgnx2ffm');

			map.fitBounds([ [ latitude, longitude ] ], {
				paddingTopRight : [ 400, 0 ]
			});
			map.setZoom(12);
			load_events();
		}

		function error_callback(p) {
			// p.message : error message
		}
		/*Configuracion de la busqueda */
		$("#search-input").autocomplete({
			source : function(request, response) {
				$.getJSON("events/search/like/" + request.term, function(data) {
					response($.map(data, function(item) {
						return {
							label : item.name,
							value : item.name
						}
					}))
				})
			},
			select : function(event, ui) {
				console.debug(ui.item.value);
				$.getJSON("events/search/exact/" + ui.item.value, load_events_on_map);
			}
		});
		$("#search-btn").click(function(e){
			var term = $("#search-input").val();
			$.getJSON("events/search/like/"+term+"/", load_events_on_map);
			return false;
		});
	</script>
  </body>
</html>