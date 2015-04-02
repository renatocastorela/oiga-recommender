<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>

<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.homepage.title" />		
	</jsp:attribute>
	<jsp:body>
		<jsp:include page="box.jsp"></jsp:include>
		
        <div class="container">
        <div class="row">
			<div class="col-md-12 hidden-xs">		
				<div class="well well-sm top-well" >
				</div>
			</div>
		</div>	
		<div class="row events-container">
		
        </div>
        </div>
        <script type="text/javascript" charset="UTF-8">
        var template = $("#event-box-template").html();
        
        function build_dynamic_query(filters){
        	var query = "";
        	var params = {};
        	if(typeof filters.location != 'undefined'){
        		var ln = filters.location.ln;
        		var lt = filters.location.lt;
        		var radio = filters.location.radio;
				query += "START a=node:geo_location('withinDistance:["+ lt +" "+ ln+", "+radio+" ]') \
				 MATCH n-[:PERFORMED]->(v)-[:IS_LOCATED]->a, n-[:CATEGORIZED]-c, n-[:HOSTED]->o ";								    			
			}else{
				query += "START n=node(*) MATCH n-[:PERFORMED]->v, n-[:CATEGORIZED]-c, n-[:HOSTED]->o ";
			}
        	if(typeof filters.tags != 'undefined'){
        		query += ", n-[:TAGGED]->t ";
        	}
        	query += "WHERE has(n.__type__) and n.__type__ = 'org.oiga.model.entities.Event' and has(c.path) ";
        	if(typeof filters.category != 'undefined'){
        		params.category = "(?i).*" + filters.category + ".*";
        		query += "AND c.name =~ {category} ";	
        	}
        	if(typeof filters.range != 'undefined'){
        		params.startDate = filters.range.start_date.valueOf();
        		params.endDate = filters.range.end_date.valueOf();
        		query += "AND n.startDate >= {startDate} and n.endDate <= {endDate} "
        	}
        	if(typeof filters.venue != 'undefined'){
        		params.venue = "(?i).*" +filters.venue+ ".*";
        		query += "and v.name =~ {venue} ";
        	}
        	if(typeof filters.organizer != 'undefined'){
        		params.organizer = "(?i).*" +filters.organizer+ ".*";
        		query += "and o.name =~ {organizer} ";
        	}
        	if(typeof filters.tags != 'undefined'){
        		params.tags = filters.tags;
        		query += "AND t.keyword in {tags} ";
        	}
        	query += "return n,v,collect(c.name), collect(c.path), o ";
        	console.log(query);
        	console.log(params);
        	return {query : query, params: params };
        }
        
        function load_filtered_events(filters, container){
			var build = build_dynamic_query(filters);        	
        	run_cypher(
        			build.query,
        			build.params,
    				function(response){
    					$.each(response.data, function(i, val) {
    						append_event_to_row(val, container);
    					});
    				}
    		);
        }
        
        function get_url_filters(){
        	var filters = {};
        	if(getParameterByName("lt") != '' && getParameterByName("ln") != ''){
        		filters.location = {
        				lt :  getParameterByName("lt"),
        				ln : getParameterByName("ln")
        		};
        		if(getParameterByName("r") == ''){
        			filters.location.radio = "10.00"; 	
        		}else{
        			filters.location.radio = getParameterByName("r");
        		}
        	}
        	if(getParameterByName("category")!=''){
        		filters.category = decodeURIComponent(getParameterByName("category"));
        	}
        	if(getParameterByName("when") != ''){
        		filters.range = get_moment_range_by_option(getParameterByName("when"));
        	}
        	if(getParameterByName("venue") != ''){
        		filters.venue = decodeURIComponent(getParameterByName("venue"));
        	}
        	if(getParameterByName("organizer") != ''){
        		filters.organizer = decodeURIComponent(getParameterByName("organizer"));
        	}
        	if(getParameterByName("tags") != ''){
        		filters.tags = decodeURIComponent(getParameterByName("tags")).split(",");
        	}
        	console.log(filters);
        	return filters;
        }
        function resolve_geoname_remote(location, container){
    		$.get("http://api.geonames.org/findNearbyPlaceNameJSON", 
    				{ lat : location.lt, lng : location.ln, username : "oiga",  lang: "es", cities : "cities15000"})
    		.done(function(data){
    			console.log(data);
    			if(data.totalResultsCount == 0){
    				return;
    			}
    			append_tag("label label-salmon inline-block",
    					data.geonames[0].name,
    					container);
    		});
    	}
        function append_tag(classes, val, container ){
        	$("<span />")
				.addClass(classes)
				.css({display: 'none'})
				.html(val)
				.appendTo(container).fadeIn(getRandomInt(500, 2000));
        }
        
        function load_filters(filters, container){
        	if(typeof filters.location){
        		resolve_geoname_remote(filters.location, container);
        	}
        	if(typeof filters.category != 'undefined'){
        		append_tag("label label-salmon inline-block", filters.category, container);		
        	}
        	if(typeof filters.range != 'undefined'){
        		append_tag("label label-sandy inline-block",
        				filters.range.start_date.locale("es").format("MMMM DD"),
        				container);
        		append_tag("label label-thistle inline-block",
        				filters.range.end_date.locale("es").format("MMMM DD"),
        				container);
        	}
        	if(typeof filters.venue != 'undefined'){
        		append_tag("label label-violet inline-block", filters.venue, container);
        	}
        	if(typeof filters.organizer != 'undefined'){
        		append_tag("label label-coral inline-block",
        				filters.organizer,
        				container);
        	}
        	if(typeof filters.tags != 'undefined'){
        		$.each(filters.tags, function(i, val){
        			append_tag("label label-brown inline-block",
            				val,
            				container);	
        		});
        	}
        	
        }
        
        $(function(){
        	var filters = get_url_filters();
        	load_filtered_events(filters, ".events-container" );
        	load_filters(filters, ".top-well");
    	});
        </script>
	</jsp:body>
</l:base>