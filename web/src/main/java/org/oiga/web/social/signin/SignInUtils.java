package org.oiga.web.social.signin;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Deprecated
public class SignInUtils {
	
	   public static void signin(String userId) {
           SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));        
   }

}
