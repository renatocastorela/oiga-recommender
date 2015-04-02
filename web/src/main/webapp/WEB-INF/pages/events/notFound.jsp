<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.oiga.title" text="No se encontro el evento" />		
	</jsp:attribute>
	<jsp:body>
	    		
      <div class="container">
	    <div class="row">
			<div> No se encontro el evento :'( </div>			
	    </div>
     </div>
      	</jsp:body>
</l:base>
