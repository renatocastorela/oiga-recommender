<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib
	uri="http://www.springframework.org/spring-social/facebook/tags"
	prefix="facebook"%>
<%@ page session="false"%>
 
 				<div>
 					<form action="${pageContext.request.contextPath}/signin/facebook" method="POST">
  					 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  					 <input type="hidden" name="scope" value="email" />
  					<button type="submit" class="btn-facebook btn-connect">
  						Facebook
  					</button>
				</form>
				</div>
 