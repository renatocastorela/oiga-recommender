<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<p class="text-muted">Inicie sesión con su red social</p>
<form action="${pageContext.request.contextPath}/signin/facebook"
	method="POST" id="loginByFacebook">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" /> 
		<input type="hidden" name="scope" value="email" />
	<button type="submit" class="btn btn-facebook btn-connect">
		Facebook</button>
</form>
<hr />
<p class="text-muted">O mediante su correo electronico</p>
<form:form action="${pageContext.request.contextPath}/users/signin"
	method="POST" modelAttribute="signinForm">
	<s:bind path="*">
		<c:choose>
			<c:when test="${status.error}">
				<div class="alert alert-danger">
					<spring:message code="error.users.signin"></spring:message>
				</div>
			</c:when>
		</c:choose>
	</s:bind>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	<div class="form-group">
		<label for="email">Correo Electronico</label> <input name="email"
			value="${signinForm.email }" type="text" class="form-control"
			id="email" placeholder="Correo Electronico" />
		<form:errors path="email" cssClass="error-label" />
	</div>
	<div class="form-group">
		<label for="lastName">Password</label> <input name="password"
			type="password" class="form-control" id="password"
			placeholder="Contraseña" />
		<form:errors path="password" cssClass="error-label" />
	</div>
	<button type="submit" class="btn btn-primary">Iniciar sesion</button>
</form:form>