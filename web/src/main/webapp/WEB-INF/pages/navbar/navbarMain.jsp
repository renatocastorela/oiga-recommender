<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="facebook" uri="http://www.springframework.org/spring-social/facebook/tags" %>
<%@ taglib prefix="social" uri="http://www.springframework.org/spring-social/social/tags" %>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
	
	<!-- Navbar header -->
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-content">
			<span class="sr-only"><spring:message code="label.button.toggle"/></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="${pageContext.request.contextPath}" id="oiga_logo"> 
			<img src="${pageContext.request.contextPath}/resources/img/oiga_logo.png" alt="Oiga logo" />
		</a>
	</div>
	<!-- Navbar collapse -->
	<div class="collapse navbar-collapse" id="navbar-content">
		<form class="navbar-form navbar-left" role="search" action="${pageContext.request.contextPath}/events/explore" id="search-form">
			<div class="form-group">
				<input type="text" class="form-control navbar-input"
					placeholder="Eventos en mi ciudad" id="search-input" name="q" />
				<input type="text" id="place" class="form-control navbar-input" placeholder="Lugar" name="place"/>	
				<button type="button" class="btn btn-default" id="geolocation" >
  					<span class="glyphicon glyphicon-map-marker"></span>
				</button>
				<select name="when" id="when" class="form-control navbar-input">
					<option selected="selected" value="PROXIMO_MES" >Próximos eventos</option>
					<option value="PROXIMO_FIN_DE_SEMANA">Fin de semana</option>
					<option value="Monday" >Proximo lunes</option>
					<option value="Tuesday">Proximo martes</option>
					<option value="Wednesday">Proximo miercoles</option>
					<option value="Thursday">Proximo jueves</option>
					<option value="Friday">Proximo viernes</option>
					<option value="Saturday">Proximo sabado</option>
					<option value="Sunday">Proximo domingo</option>
				</select>
			</div>
			<input type="hidden" name="start_date" />
			<input type="hidden" name="end_date" />
			<input type="hidden" name="ln" />
			<input type="hidden" name="lt" />
			<button type="submit" class="btn btn-pistache "
				id="search-btn">Buscar</button>
		</form>
		<div id="user-functions">
			<jsp:include page="/WEB-INF/pages/navbar/userFunctions.jsp"></jsp:include>
		</div>
	</div>
	</div>
</nav>
<!-- Signin modal -->
<div class="modal" id="signin-modal" tabindex="-1" role="dialog"
	aria-labelledby="signinLabel" aria-hidden="true">
</div>

<!-- Signup modal -->
<div class="modal" id="signup-modal" tabindex="-1" role="dialog"  
	aria-labelledby="signupLabel" aria-hidden="true">
	
</div>

<!-- Location util functions -->
<script type="text/javascript">
	var oiga_location = "oiga.location";
	var submit_flag = false;
	var cuernavaca_location = {
		name : "Cuernavaca",
		latitude : 19.428,
		longitude : -99.141,
	};
	var default_location = {
		name : "Ciudad de Mexico",
		latitude : 18.924,
		longitude : -99.221,
	};
	$.cookie.json = true;
	function is_location_sync(){
		if (!is_location_set()){
			return false;
		}
		if( $( "input[name=place]" ).val()  == $.cookie(oiga_location).name 
				&& $( "input[name=ln]" ).val()  == $.cookie(oiga_location).latitude
				&& $( "input[name=lt]" ).val()  == $.cookie(oiga_location).longitude){
			return true;	
		}
		return false;
	}
	function is_location_set(){
		if(typeof $.cookie(oiga_location) == 'undefined'){
			return false;	
		}
		return true;
	}
	
	function resolve_geoname_remote(latitude, longitude){
		$.get("http://api.geonames.org/findNearbyPlaceNameJSON", 
				{ lat : latitude, lng : longitude, username : "oiga",  lang: "es", cities : "cities15000"})
		.done(function(data){
			if(data.totalResultsCount == 0){
				resolve_geodata_local();
			}
			 set_location(latitude, longitude, data.geonames[0].name);
		}).fail(function(data){
			resolve_geodata_local();
		});
	}
	
	function resolve_geopoint_remote(){
		$.ajax({
			dataType : "json",
			url : "https://freegeoip.net/json/"
		}).done(function(data) {
			if(typeof data.latitude == 'undefined' && typeof data.longitude == 'undefined'){
				resolve_geodata_local();
			}
			resolve_geoname_remote(data.latitude, data.longitude);
		}).fail(function(data){
			resolve_geodata_local();
		});
	}
	
	function reverse_geocoding(name, callback){
		$.get("http://api.geonames.org/searchJSON",
				{name_equals : name, lang : "es", country : "MX", username : "oiga", maxRows : 1 }, 
				callback);		
	}
	
	function set_geoname(data){
		if(data.totalResultsCount != 0){
			var lt = data.geonames[0].lat;
			var ln = data.geonames[0].lng;
			var name = data.geonames[0].name;
			set_location(lt, ln, name);
		}
	}
	
	function resolve_geodata_local(){
		set_location(default_location.latitude, default_location.longitude, default_location.name);	
	}
	
	function resolve_geodata(){
		//TODO: Validar datos del URL para carga
		if (!is_location_sync()){
			sync_location();
		}
	}
		
	function set_location(latitude, longitude, name) {
		$.cookie(oiga_location, {latitude:latitude, longitude:longitude, name:name},
					{path: "/", expires : 7});
		$( "input[name=place]" ).val(name); 
		$( "input[name=ln]" ).val(longitude);
		$( "input[name=lt]" ).val(latitude);
		console.debug("name : "+name+", lt: "+latitude+", ln : "+longitude);
	}
	
	
	function sync_location() {
		var place = $( "input[name=place]" ).val();
		if( is_location_set() ){
			$( "input[name=place]" ).val($.cookie(oiga_location).name);
			$( "input[name=ln]" ).val($.cookie(oiga_location).longitude);
			$( "input[name=lt]" ).val($.cookie(oiga_location).latitude);
		} else if(place.trim() != "" ){
			reverse_geocoding(place, set_geoname);	
		} else{
			resolve_geopoint_remote();
		}
	}
	
	function get_current_location(){
		if (navigator.geolocation) {
			  	navigator.geolocation.getCurrentPosition(function(position){
			  	resolve_geoname_remote(position.coords.latitude, position.coords.longitude);		  
			  }, function(error){
				  console.error('Error: '+error.message);  
			  });
		}else{
			  console.error('not supported');
		}

	}
	
	$( "#search-form" ).submit(function(event){
		if( is_location_sync() || submit_flag == true){
			return true;
		}
		event.preventDefault();
		var place =  $( "input[name=place]" ).val();
		reverse_geocoding(place, function(data){ 
			submit_flag = true;
			set_geoname(data);
			set_date_range();
			$( "#search-form" ).submit();
		});
	});
	/*Registro */
	$("#geolocation").click(get_current_location);
	resolve_geodata();
</script>
<!-- Time Script -->
<script type="text/javascript">
	//@Deprecated
	function get_date_range_by_option(option){
		if(option == "PROXIMO_MES"){
			return {start_date : moment().startOf('day').format(), 
				end_date : moment().add(30, 'd' ).endOf('day').format() };
		}else if(option == "PROXIMO_FIN_DE_SEMANA"){
			return {start_date : moment().day("Saturday").startOf('day').format(), 
				end_date : moment().day("Sunday").endOf('day').format() };
		}else {
			return {start_date : moment().day(option).startOf('day').format(), 
				end_date : moment().day(option).endOf('day').format() };
		}
	}
	function get_moment_range_by_option(option){
		if(option == "PROXIMO_MES"){
			return {start_date : moment().startOf('day'), 
				end_date : moment().add(30, 'd' ).endOf('day') };
		}else if(option == "PROXIMO_FIN_DE_SEMANA"){
			return {start_date : moment().day("Saturday").startOf('day'), 
				end_date : moment().day("Sunday").endOf('day') };
		}else {
			return {start_date : moment().day(option).startOf('day'), 
				end_date : moment().day(option).endOf('day') };
		}
	}
	function set_date_range(){
		var range = get_date_range_by_option ( $( "select[name=when]" ).val() );
		$( "input[name=start_date]" ).val(range.start_date);
		$( "input[name=end_date]" ).val(range.end_date);
	}
</script>
<script type="text/javascript">
var QueryString = function () {
	  // This function is anonymous, is executed immediately and 
	  // the return value is assigned to QueryString!
	  var query_string = {};
	  var query = window.location.search.substring(1);
	  var vars = query.split("&");
	  for (var i=0;i<vars.length;i++) {
	    var pair = vars[i].split("=");
	    	// If first entry with this name
	    if (typeof query_string[pair[0]] === "undefined") {
	      query_string[pair[0]] = pair[1];
	    	// If second entry with this name
	    } else if (typeof query_string[pair[0]] === "string") {
	      var arr = [ query_string[pair[0]], pair[1] ];
	      query_string[pair[0]] = arr;
	    	// If third or later entry with this name
	    } else {
	      query_string[pair[0]].push(pair[1]);
	    }
	  } 
	    return query_string;
	} ();
</script>
<script type="text/javascript">
	/*Autocomplete*/
 	console.debug("config autocomplete");	
	
	$("#place").autocomplete({
		source : function(request, response){
			$.get("http://api.geonames.org/searchJSON", 
					{name_startsWith : request.term, lang : "es", country : "MX", username : "oiga", cities : "cities15000" }, 
					function(data){
						var suggestions = $.map(data.geonames, function(item){ return {label : item.name, value: item.name}; });
					response(suggestions);
			});		
		}
	});
	$("#search-input").autocomplete({
		source : function(request, response) {
			client.search({
				index : 'events',
				body : {
					query : {
						bool : {
							should : [{	
								match_phrase_prefix : { 
									name : { query :request.term,
									_name:"phrase",
										slop : 6 }
									}},
								{match : {	
									name : { query : request.term, fuzziness: 0.7, cutoff_frequency : 0.001, analyzer : "spanish",
										_name:"phrase"}
								}}]
						}
					},
					size : 5,
					highlight :{ pre_tags : ["<b>"],
						post_tags : ["</b>"],
						fields : {name:{}}},
					_source : ["name", "uuid", "start_date", "end_date", "hyphen"],
					suggest: {
						text : request.term,
						category_suggest : {
							completion : {
								field : "category_suggest",
								fuzzy : true
							}
						},
						organizer_suggest : {
							completion : {
								field : "organizer_suggest",
								fuzzy : true
							}
						},
						venue_name_suggest : {
							completion : {
								field : "venue_name_suggest",
								fuzzy : true
							}
						}, 
						phrase_suggest : 
						{
							phrase : { field : "_all", confidence : 0.0, highlight: {
						          pre_tag: "<b>",
						          post_tag: "</b>"
						     }}
							
						
						}
					}
				}
			}, function(error, data){
					var name_suggest = $.map(data.hits.hits, function(item) {
						var start_date = moment(item._source.start_date).format("DDMMYYYY");
						var end_date = moment(item._source.end_date).format("DDMMYYYY");
						
						return {
							label : item.highlight.name ,
							value : item._source.name,
							type : "name_suggest", 
							style : "background:#CCFFFF",
							url : ctx + "/events/"+start_date+"/"+end_date+"/"+item._source.hyphen+"/"+item._source.uuid
						};
					});
					var phrase_suggest = $.map(data.suggest.phrase_suggest[0].options, function(item) {
						return {
							label : item.text,
							value : item.text
						};
					});
					var category_suggest = $.map(data.suggest.category_suggest[0].options, function(item) {
						return {
							label : item.text,
							value : item.text,
							style : "background:#FFFF99",
							url : ctx + "/events/categories/"
						};
					});
					var organizer_suggest = $.map(data.suggest.organizer_suggest[0].options, function(item) {
						return {
							label : item.text,
							value : item.text,
							style : "background:#CCFF99",
						};
					});
					var venue_name_suggest = $.map(data.suggest.venue_name_suggest[0].options, function(item) {
						return {
							label : item.text,
							value : item.text,
							style : "background:#99FF99",
						};
					});	
					var suggestions = phrase_suggest.concat( name_suggest);
					suggestions = suggestions.concat(category_suggest);
					suggestions = suggestions.concat(organizer_suggest);
					suggestions = suggestions.concat(venue_name_suggest);
					response(suggestions);
			});
		}
	}).data("ui-autocomplete")._renderItem = function( ul, item ) {
		var url = typeof item.url == 'undefined' ? "":"href="+item.url;				
		return $( "<li>" )
		.append( "<a style="+item.style+" "+url+" >"+item.label+"</a>" )
		.appendTo( ul );
	};
</script>
