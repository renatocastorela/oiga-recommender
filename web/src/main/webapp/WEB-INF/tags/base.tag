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
<!-- JQuery UI -->
<link
	href='http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css'
	rel='stylesheet' />

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
<script src='//api.tiles.mapbox.com/mapbox.js/v1.6.0/mapbox.js'></script>
<!-- jQuery -->
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- jQuery plugins -->
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.cookie.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/geoPosition.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/geoPositionSimulator.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/mustache.js"></script>
</head>
<!-- Variables globales -->
<script type="text/javascript">
	var ctx = "${pageContext.request.contextPath}";
	var oiga = new Object();
	if (typeof $.cookie("oiga.rememberMe") == 'undefined') {
		oiga.rememberMe = "true";
	}else{
		oiga.rememberMe = $.cookie("oiga.rememberMe");
	}
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
		      status     : true,
	          xfbml      : true
		    });
			
			
			console.log("Se inicializa facebook");
			console.log("Estus remmeberMe : "+oiga.rememberMe);
			//Autenticando en la aplicacion 
			FB.getLoginStatus(function(response) {
				if (response.status === 'connected' && oiga.rememberMe === 'true') {
					console.log("Reconectando con la aplicacacion..");
					if( $("#user-functions ul").is(".anonymous") ){
						response.authResponse['${_csrf.parameterName}'] = '${_csrf.token}';
						$.post(ctx+"/reconnect/facebook", response.authResponse,
							"json")
							.done(function(data){
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
			
			// Me gosta
			var page_like_callback = function(url, html_element) {
			  console.log("page_like_callback");
			  console.log(url);
			  console.log(html_element);
			}
			//Ya no me gosta
			var page_unlike_callback = function(url, html_element) {
			  console.log("page_unlike_callback");
			  console.log(url);
			  console.log(html_element);
			}
			FB.Event.subscribe('edge.create', page_like_callback);			
			FB.Event.subscribe('edge.remove', page_unlike_callback);
			
		  });
			
		});
 </script>
	<!-- Oiga Scripts -->
	<script type="text/javascript">
		$.cookie.json = true;
		if (typeof $.cookie("location") == 'undefined') {
			$.ajax({
				dataType : "json",
				url : "https://freegeoip.net/json/",
				async : false
			}).done(
					function(data) {
						$.cookie("location", data, {
							expires : 1
						});
						console.debug("Location : "
								+ $.cookie("location").region_name);
					});
		} else {
			console.debug(">Location : " + $.cookie("location").region_name);
		}
		
		function getParameterByName(name) {
			name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
			var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
					.exec(location.search);
			return results == null ? "" : decodeURIComponent(results[1]
					.replace(/\+/g, " "));
		}
	</script>
	<jsp:include page="/WEB-INF/pages/navbar/navbarMain.jsp" />
	<jsp:doBody />
</body>
</html>