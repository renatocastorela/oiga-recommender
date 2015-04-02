<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.homepage.title" />		
	</jsp:attribute>
	<jsp:body>
	<jsp:include page="/WEB-INF/pages/events/box.jsp"></jsp:include>
<div class="container">
<div class="row">
<div class="col-md-12 hidden-xs">		
<div class="well well-sm top-well" >
<c:forEach var="c" items="${all_categories }">
	<a href="${pageContext.request.contextPath}/events/categories${c.path}" class="label label-info inline-block" >${c.name}</a>
</c:forEach>
</div>
</div>
</div>
<div class="row">		
	<div class="events-container">
	<c:if test="${ not empty parent_category}">
			<h3>
			<a href="${pageContext.request.contextPath}/events/categories${category.path}">${category.name }</a>
			</h3>
			<div
								class="event-box ${fn:replace(parent_category,' ', '-')}-category"
								category="${parent_category}">
			</div>
	</c:if>
	<c:if test="${fn:length(categories) > 0}">
		<c:forEach var="c" items="${categories }">
  				<h3><a href="${pageContext.request.contextPath}/events/categories${c.path}">${c.name }</a></h3>
			<div class="event-box ${c.hyphen}-category"
				id="${c.uuid}" category="${c.hyphen}">
			</div>
		</c:forEach>
	</c:if>  
</div>
</div>
</div>
<script type="text/javascript">
	var template = $("#event-box-template").html();

	function build_box(node, category) {
		var category_class = "." + category.replace(" ", "-") + "-category";
		append_event_to_row(node, category_class);
	}
	
	function run_cypher(query, params, success) {
		data = {
			query : query,
			params : params
		};
		data = JSON.stringify(data);
		$.ajax({
			type : "POST",
			url : "http://localhost:7474/db/data/cypher",
			data : data,
			success : success,
			accepts : "application/json",
			contentType : "application/json",
			dataType : "json"
		});
	}

	function find_by_category(category) {
		console.log(category);
		var query = "START n=node(*)	\
				MATCH n-[:CATEGORIZED]->(c)<-[r?:HAS*]-(d), n-[:PERFORMED]->v, n-[:HOSTED]->o \
				WHERE has(n.__type__) and n.__type__ = 'org.oiga.model.entities.Event' \
				and ( c.hyphen = {category} or d.hyphen = {category} ) \
				RETURN  n, v, collect(DISTINCT c.name), collect(DISTINCT c.path), o";
		run_cypher(query, {
			category : category
		}, function(response) {
			$.each(response.data, function(i, val) {
				build_box(val, category);
			});
			init_carraousel(category);
		});
	}

	function init_carraousel(category) {
		var css_class = "." + category.replace(" ", "-") + "-category";
		var settings = {
			infinite : false,
			slidesToShow : 3,
			slidesToScroll : 3,
			responsive : [ {
				breakpoint : 600,
				settings : {
					slidesToShow : 2,
					slidesToScroll : 2
				}
			}, {
				breakpoint : 480,
				settings : {
					slidesToShow : 1,
					slidesToScroll : 1
				}
			} ]
		};
		$(css_class).slick(settings);
	}
	$(".event-box").waypoint(function() {
		var category = $(this).attr("category");
		if (!$(this).find(".panel").length) {
			find_by_category(category);
		}
	}, {
		offset : '100%'
	});
</script>

</jsp:body>
</l:base>
