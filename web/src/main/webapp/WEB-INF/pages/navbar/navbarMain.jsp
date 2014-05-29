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
				<input type="text" class="form-control"
					placeholder="Eventos en mi ciudad" id="search-input" name="q" >
			</div>
			<input type="hidden" name="ln" />
			<input type="hidden" name="lt" />
			<button type="submit" class="btn btn-pistache btn-lg "
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

<script type="text/javascript">
	/*Busqueda Global*/
 	console.debug("config autocomplete");
	$("input[name=lt]").val(  $.cookie("location").latitude );
	$("input[name=ln]").val(  $.cookie("location").longitude );
$("#search-input").autocomplete({
	source : function(request, response) {
		$.getJSON(ctx+"/events/search/like",
				{
					q : request.term,
					lt : $.cookie("location").latitude,
					ln : $.cookie("location").longitude
				},
				function(data) {
					response($.map(data, function(item) {
					return {
						label : item.name,
						value : item.name
					}
				}))
			})
		}
});
</script>
