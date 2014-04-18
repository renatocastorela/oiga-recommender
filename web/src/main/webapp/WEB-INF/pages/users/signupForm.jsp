<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${not empty message}">
	<div class="${message.type.cssClass}">${message.text}</div>
</c:if>
<form:form role="form" method="post"
	action="${pageContext.request.contextPath}/users"
	modelAttribute="signupForm">
	<div class="formInfo">
		<s:bind path="*">
			<c:choose>
				<c:when test="${status.error}">
					<div class="error">Unable to sign up. Please fix the errors
						below and resubmit.</div>
				</c:when>
			</c:choose>
		</s:bind>
	</div>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	<div class="form-group">
		<label for="firstName">Nombre</label> <input name="firstName"
			type="text" class="form-control" id="firstName" placeholder="Nombre" />
		<form:errors path="firstName" />
	</div>
	<div class="form-group">
		<label for="lastName">Apellido</label> <input name="lastName"
			type="text" class="form-control" id="lastName" placeholder="Apellido" />
		<form:errors path="lastName" />
	</div>
	<div class="form-group">
		<label for="emailInput">Correo electronico</label> <input name="email"
			type="email" class="form-control" id="emailInput"
			placeholder="Correo" />
		<form:errors path="email" />
	</div>
	<div class="form-group">
		<label for="passwordInput">Contraseña</label> <input name="password"
			type="password" class="form-control" id="passwordInput"
			placeholder="Contraseña" />
		<form:errors path="password" />
	</div>
	<button type="submit" class="btn btn-primary">Registrarse</button>
</form:form>