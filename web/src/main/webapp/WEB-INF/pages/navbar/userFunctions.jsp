<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="facebook"
	uri="http://www.springframework.org/spring-social/facebook/tags"%>
<%@ taglib prefix="social"
	uri="http://www.springframework.org/spring-social/social/tags"%>
	<!-- Anonymous -->
	<sec:authorize access="isAnonymous()">
	<ul class="anonymous nav navbar-nav navbar-right">
		<li>
			<button type="button" class="btn btn-primary navbar-btn btn-lg"
				data-toggle="modal" data-target="#signup-modal"
				data-remote="${pageContext.request.contextPath}/navbar/signupModal">
				<spring:message code="label.signup" />
			</button>
		</li>
		<li>
			<button type="button" class="btn-pink navbar-btn btn-lg"
				data-toggle="modal" data-target="#signin-modal"
				data-remote="${pageContext.request.contextPath}/navbar/signinModal">
				<spring:message code="label.login" />

			</button>
		</li>
	</ul>
	</sec:authorize>

	<!-- Authenticated -->
	<sec:authorize access="isAuthenticated()">
		<ul class="authenticated nav navbar-nav navbar-right">
		<li><img alt="User profile"
			src="<sec:authentication property="principal.user.imageUrl"/>"
			class="img-rounded" style="height: 35px;" /></li>
		<li>
			<div class="dropdown">
				<a data-toggle="dropdown" href="#" role="button"> <sec:authentication
						property="principal.username" /> <span class="caret"></span>
				</a>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
					<li><a
						href="<c:url value="/users/"/><sec:authentication property="principal.user.nodeId"/>">Mi
							perfil</a></li>
					<li><a id="logout-button" href="#">Cerrar sesion</a> 
					<script>
					$("#logout-button").click(function(){
						$.cookie("oiga.rememberMe", "false", {
							path    : '/',   
							expires : 365*10
						});
						window.location.href = ctx + "/signout";
					});
					</script>
				</li>
				<li><a id="switch-user-button" href="#">Cambiar de usuario</a></li>
				</ul>
			</div>
		</li>
		</ul>
	</sec:authorize>
</ul>
