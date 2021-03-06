<%@tag description="Base Pages template" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="pageTitle" fragment="true"%>
<%@attribute name="openGraphMeta" fragment="true" required="false"%>
<%@attribute name="header" fragment="true"%>
<%@attribute name="footer" fragment="true"%>

<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/img/cokie_60.png" />
<title><jsp:invoke fragment="pageTitle" /></title>
<jsp:invoke  fragment="openGraphMeta"  />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<!-- Mapbox -->
<link href='//api.tiles.mapbox.com/mapbox.js/v1.6.0/mapbox.css'
	rel='stylesheet' />
<!-- Slick -->
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/jquery.slick/1.3.15/slick.css"/>
<!-- JQuery UI --> 

<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/themes/smoothness/jquery-ui.css" />
<!--[if lte IE 8]>
    <link href='//api.tiles.mapbox.com/mapbox.js/v1.3.1/mapbox.ie.css' rel='stylesheet'>
  	<![endif]-->
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
<!-- Oiga 	-->
<link href="${pageContext.request.contextPath}/resources/css/oiga.css"
	rel="stylesheet" media="screen">
<!-- Mapbox -->
<link href="${pageContext.request.contextPath}/resources/css/map.css"
	rel="stylesheet" media="screen">

<script src='https://api.tiles.mapbox.com/mapbox.js/v2.1.4/mapbox.js'></script>
<!-- jQuery -->
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
<!-- jQuery plugins -->
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.cookie.js"></script>
<script 
	src="${pageContext.request.contextPath}/resources/js/jquery.raty.js"></script>
<script type="text/javascript" src="//cdn.jsdelivr.net/jquery.slick/1.3.15/slick.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/geoPosition.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/geoPositionSimulator.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/mustache.js"></script>
<!-- Elastic search -->
<script
	src="${pageContext.request.contextPath}/resources/js/elasticsearch.js"></script>
<!-- Moment js -->
<script
	src="${pageContext.request.contextPath}/resources/js/moment-with-locales.min.js"></script>
<!-- waypoints -->
<script
	src="${pageContext.request.contextPath}/resources/js/waypoints.min.js"></script>
	
</head>
<!-- Variables globales -->
<script type="text/javascript">
	var EVENT_ID_REGEX = /.*events\/([0-9]*)/;
	var client = elasticsearch.Client({
		  host: 'localhost:9200',
		  log: 'trace'
		});
	var ctx = "${pageContext.request.contextPath}";
	var oiga = new Object();
	if (typeof $.cookie("oiga.rememberMe") == 'undefined') {
		oiga.rememberMe = "true";
	}else{
		oiga.rememberMe = $.cookie("oiga.rememberMe");
	}
	$.fn.raty.defaults.path = ctx + '/resources/img';
</script>

<body>
	<!-- Facebook SDK -->
	<div id="fb-root"></div>
   	<script>
	$(document).ready(function() {
		  $.ajaxSetup({ cache: true });
		  $.getScript('//connect.facebook.net/es_LA/all.js', function(){
			FB.init({
		      appId: '546423088770268',
		      cookie	: true,
		      status	: true,
	          xfbml		: true
		    });
			console.log("Se inicializa facebook");
			console.log("Estus remmeberMe : "+oiga.rememberMe);
			//Autenticando en la aplicacion 
			FB.getLoginStatus(function(response) {
				console.log("Reconectando con la aplicacacion..");
				if (response.status === 'connected' ){
					var userID = response.authResponse.userID;
					console.log("Usuario conectado : "+userID);
					$.cookie.json = false;
					if(typeof $.cookie('oiga.facebookUid') == 'undefined'){
						$.cookie('oiga.facebookUid', userID, { path : '/'});
					}else if($.cookie('oiga.facebookUid') != userID){
						$.cookie('oiga.facebookUid', userID, { path : '/'});
					}
				}else{
					console.log("No se esta conectado con facebook");
					$.removeCookie('oiga.facebookUid', { path: '/' });
				}
				if (response.status === 'connected' && oiga.rememberMe === 'true') {
					if( $("#user-functions ul").is(".anonymous") ){
						response.authResponse['${_csrf.parameterName}'] = '${_csrf.token}';
						$.post(ctx+"/reconnect/facebook", response.authResponse,
							"json")
							.done(function(data){
								console.info("Conexion exitosa");
								$(" #user-functions ").fadeOut("slow", function(){
									$(this).load(ctx+"/navbar/userFunctions").fadeIn("slow");
							});
								})
							.fail(function(jqXHR, textStatus, errorThrown){
								console.error(jqXHR+","+textStatus+" , "+errorThrown);
								});
					  } 
					}
			});
			//Log out from facebook
			$("#switch-user-button").click(function(){
				FB.logout(function(response) {
					  console.info("El usuario ha sido desconecado te facebook");
					  window.location.href = ctx + "/signout";
					});
			});
			// Me gosta
			var page_like_callback = function(url, html_element) {
			  	console.log("page_like_callback");
				var id = url.match(EVENT_ID_REGEX)[1];
				console.log("Me gusta el event id : "+id);
				$.post(ctx+"/events/liked/"+id,{
					'${_csrf.parameterName}' : '${_csrf.token}' 
				});
			}
			//Ya no me gosta
			var page_unlike_callback = function(url, html_element) {
			  	console.log("page_unlike_callback");
			  	//console.log(url);
			  	//console.log(html_element);
			}
			FB.Event.subscribe('edge.create', page_like_callback);			
			FB.Event.subscribe('edge.remove', page_unlike_callback);
		  });
		
		});
 </script>
	<!-- Oiga Scripts -->
	<script type="text/javascript">
  		
		
		function getParameterByName(name) {
			name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
			var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
					.exec(location.search);
			return results == null ? "" : decodeURIComponent(results[1]
					.replace(/\+/g, " "));
		}
		
		function get_event_box(template, node){
    		event = node[0].data;
    		event.startDateParam = moment(event.startDate).format("DDMMYYYY"); 
            event.endDateParam = moment(event.endDate).format("DDMMYYYY");
    		event.startDate = moment(event.startDate).calendar();
            event.endDate = moment(event.endDate).calendar();
            event.venue = node[1].data;
            event.organizer = node[4].data;
            event.categories = [];
            
            $.each(node[2], function(i, val){
            	console.log(val);
            	category = {
            			name : node[2][i],
            			path : node[3][i],
            	};
            	event.categories.push(category);
            }); 
    		return Mustache.render(template, event);
            
    	}
		
		function run_cypher(query, params, success) {
    		data = {
    			query : query,
    			params : params
    		};
    		data = JSON.stringify(data);
    		$.ajax({
    			type : "POST",
    			url : "http://localhost:7474/db/data/cypher",
    			data : data,
    			success : success,
    			accepts : "application/json",
    			contentType : "application/json",
    			dataType : "json"
    		});
    	}
		
		function append_event_to_row(node, row) {
    		$(document.createElement('div'))
    			.addClass("col-lg-4")
    			.append(get_event_box(template, node))
    			.appendTo(row);
    	}
		

		/**
		 * Returns a random integer between min (inclusive) and max (inclusive)
		 * Using Math.round() will give you a non-uniform distribution!
		 */
		function getRandomInt(min, max) {
		    return Math.floor(Math.random() * (max - min + 1)) + min;
		}
	</script>
	
	<jsp:include page="/WEB-INF/pages/navbar/navbarMain.jsp" />
	<jsp:doBody />
	<jsp:include page="/WEB-INF/layouts/footer.jsp" />
</body>
</html>