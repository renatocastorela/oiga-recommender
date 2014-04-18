<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>
<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.signuppage.title" />		
	</jsp:attribute>
	<jsp:body> 
		<div class="container">
			<section id="signup">
				<h2>Conviertete en miembro</h2>
				<jsp:include page="/WEB-INF/pages/users/signupForm.jsp" />
			</section>	
		</div>
	</jsp:body>
</l:base>