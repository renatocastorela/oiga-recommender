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
		<form class="navbar-form navbar-left" role="search">
			<div class="form-group">
				<input type="text" class="form-control"
					placeholder="Eventos en mi Ciudad" id="search-input">
			</div>
			<button type="submit" class="btn btn-pistache btn-lg "
				id="search-btn">Buscar</button>

		</form>
		<ul class="nav navbar-nav navbar-right">
			<!-- Anonymous -->
			<sec:authorize access="isAnonymous()">
			<!-- 
				<li>
				<div >
					<button type="button" class="btn btn-primary navbar-btn btn-lg" data-toggle="dropdown">
						<spring:message code="label.signup" />
					</button>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
    					<li><a href="#">Another action</a></li>
  					</ul>
				</div>
					</li>
					-->
					<li>
				
					<button type="button" class="btn-pink navbar-btn btn-lg" data-toggle="modal" data-target="#myModal">
						<spring:message code="label.login" />
					</button>
					
				</li>
			</sec:authorize>
			<!-- Authenticated -->
			<sec:authorize access="isAuthenticated()">
				<li>
					<sec:authentication property="principal"/>
					
				</li>
				<li>
					<a href="<c:url value="/signout" />">Sign Out</a>

					</li>
			</sec:authorize>
		</ul>
	</div>
	</div>
</nav>
<!-- Login modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Iniciar sesion</h4>
			</div>
			<div class="modal-body">
				Inicia sesión con tu red social
				<div>
					<a class="btn btn-facebook btn-connect" href="#">Facebook</a>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
