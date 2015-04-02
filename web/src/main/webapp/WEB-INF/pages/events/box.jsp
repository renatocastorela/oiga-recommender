<div class="display-none" id="event-box-template">
	<div class="panel panel-platano">
		<div class="panel-heading "><p class="text-overflow"  data-toggle="tooltip" 
		title="{{name}}">{{name}} <p></div>
		<div class="panel-body">
			<dl>
				<dt>Organiza</dt>
				<dd>
					<a href="${pageContext.request.contextPath}/events/organizers/{{organizer.hyphen}}/{{organizer.uuid}}">{{host}}</a>
				<dd>
				<dt>Recinto</dt>
				<dd>
					<a href="${pageContext.request.contextPath}/events/venues/{{venue.hyphen}}/{{venue.uuid}}">{{location}}</a>
				<dd>
				<dt>Fecha</dt>
				<dd>Del {{startDate}} al {{endDate}}
				<dd>
				<dt>Categoria </dt>
				<dd>
					{{#categories}}
						<a href="${pageContext.request.contextPath}/events/categories{{path}}" class="label label-default">{{name}}</a>  
					{{/categories}}
				 </dd>
			</dl>
			<a class="btn btn-default" 
			href="${pageContext.request.contextPath}/events/{{startDateParam}}/{{endDateParam}}/{{hyphen}}/{{uuid}}"
				role="button">Ver detalles &raquo;</a>
		</div>
	</div>
</div>