<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib
	uri="http://www.springframework.org/spring-social/facebook/tags"
	prefix="facebook"%>
<%@ page session="false"%>
        <div class="col-xs-12 col-sm-9">
          <div class="jumbotron">
            <h1>Proximos eventos!!</h1>
            <p></p>
          </div>
           <jsp:include page="/WEB-INF/pages/events/row.jsp"/> 
        </div>
        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
          <span class="label label-default">Categorias</span>
          <div class="list-group">
          <a href="${pageContext.request.contextPath}/" class="list-group-item <c:if test="${ empty active }"> active </c:if> ">Todos</a>
           
          <c:forEach var="c" items="${categories }">
          	<a href="${pageContext.request.contextPath}/filter/${c.name}" class="list-group-item <c:if test="${ active == c.name }"> active </c:if>" >${ c.name} <span class="badge">${c.count}</span></a>
          </c:forEach>
          
          </div>
        </div>
      
      