<%@tag description="Base Pages template" pageEncoding="UTF-8"%>
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
/* 	
		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id))
				return;
			js = d.createElement(s);
			js.id = id;
			js.src = "//connect.facebook.net/es_LA/all.js#xfbml=1&appId=546423088770268";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
 */	
 </script>
	
	<!-- Oiga Scripts -->
	<script type="text/javascript">
		var ctx = "${pageContext.request.contextPath}";
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
			console.debug("Location : " + $.cookie("location").region_name);
		}
		function getParameterByName(name) {
			name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
			var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
					.exec(location.search);
			return results == null ? "" : decodeURIComponent(results[1]
					.replace(/\+/g, " "));
		}
	</script>
	<jsp:include page="/WEB-INF/layouts/navbar.jsp" />
	<jsp:doBody />
</body>
</html>