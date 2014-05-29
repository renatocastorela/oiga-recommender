<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h1 class="modal-title" id="signupLabel">¡Oiga!</h1>
				
			</div>
			<div class="modal-body">
				<p class="text-muted">
				Oiga es el sitio que puede visitar para enterarse de los mejores eventos culturales y compartir su experiencia 
				con amigos y familiares.
				Puede registrarse usando su red social:<p>
				<form action="${pageContext.request.contextPath}/signin/facebook" method="POST">
  					 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  					 <input type="hidden" name="scope" value="email" />
  					<button type="submit" class="btn btn-facebook btn-connect">
  						Facebook
  					</button>
				</form>
			</div>
			<div class="modal-footer">
				<p class="text-muted" align="left">
				También puede registrase con su <a href="${pageContext.request.contextPath}/users/signup"  >correo electronico</a>
				</p>
				<p class="text-muted" align="left">
				¿Ya tiene una cuenta? <a href="${pageContext.request.contextPath}/users/signin"  >Inicie sesion</a>
				</p>
			</div>
		</div>
	</div>