<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>

<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.homepage.title" />		
	</jsp:attribute>
	<jsp:body>
		<jsp:include page="/WEB-INF/pages/events/box.jsp"></jsp:include>
		<div class="display-none" id="organizer-box-template" >
						<h1>{{name}}</h1>
		</div>
		<div class="jumbotron">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 organizer-details" ></div>
				</div>	
			</div>	
        </div>
        <div class="container">	
			<div class="row events-container">
        	</div>
        </div>
        <script type="text/javascript">
        var template = $("#event-box-template").html();
    	function load_organizer_events(uuid){
    		var query = "START n=node(*) \
    			MATCH n-[:PERFORMED]->v, n-[:CATEGORIZED]-c, n-[:HOSTED]->o \
    			WHERE has(n.__type__) and n.__type__ = 'org.oiga.model.entities.Event' \
    			and o.uuid={uuid} \
    			return n,v,collect(c.name), collect(c.path), o";
    		run_cypher(
    				query,
    				{uuid : uuid},
    				function(response){
    					$.each(response.data, function(i, val) {
    						append_event_to_row(val, ".events-container");
    					});
    				}
    		);
    		
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
    	
    	function load_organizer_details(uuid){
    		var template = $('#organizer-box-template').html();
    		query = "START n=node(*) \
    			MATCH n-[:HOSTED]-o  \
    			WHERE has(n.__type__) and n.__type__ = 'org.oiga.model.entities.Event' \
    			and o.uuid={uuid} \
    			return  o LIMIT 1 "; 
    		run_cypher(query, {uuid:uuid}, function(response){
    			$('.organizer-details')
				.append(Mustache.render(template, response.data[0][0].data));
    		});
    	}
    	
    	$(function(){
    		var uuid = decodeURIComponent(window.location.pathname.split("/")[5]);
    		load_organizer_details(uuid);
    		load_organizer_events(uuid);
    	});
        </script>
	</jsp:body>
</l:base>