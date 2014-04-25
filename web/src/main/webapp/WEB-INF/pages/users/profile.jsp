<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>
<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.signuppage.title" />		
	</jsp:attribute>
	<jsp:body>
		
		<div class="container" >
			<div  class="center-block" >
				<h1>${user.firstName} ${user.lastName}</h1>
			</div>
		</div>
		
	</jsp:body>
</l:base>