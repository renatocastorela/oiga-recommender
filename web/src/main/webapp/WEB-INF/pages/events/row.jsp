<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
	<c:if test="${ not empty events }">

		<c:forEach var="ev" items="${events}">
			<div class="col-6 col-sm-6 col-lg-4">
				<div class="panel panel-danger">

					<div class="panel-heading">${ev.name}</div>
					<div class="panel-body">
						<dl>
							<dt>Organiza</dt>
							<dd>${ev.host}
							<dd>
							<dt>Recinto</dt>
							<dd>${ev.location}
							<dd>
							<dt>Fecha</dt>
							<dd>
								Del
								<fmt:formatDate value="${ev.startDate}" pattern="yyyy-MM-dd " />
								al
								<fmt:formatDate value="${ev.endDate}" pattern="yyyy-MM-dd " />
							<dd>
								<!-- 
							<dt>Descripcion</dt>
  							<dd>${ev.description}</dd>
  							 -->
							<dt>Tipo</dt>
							<dd>${ev.category.name}</dd>
						</dl>
						<a class="btn btn-default"
							href="${pageContext.request.contextPath}/events/${ev.nodeId}"
							role="button">Ver detalles &raquo;</a>
					</div>
				</div>
			</div>
			<!--/span-->
		</c:forEach>
	</c:if>
</div>