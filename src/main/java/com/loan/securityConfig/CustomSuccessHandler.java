package com.loan.securityConfig;




import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;


import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler  extends SimpleUrlAuthenticationSuccessHandler  {

private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

@Override
public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication authentication)
		throws IOException, ServletException {
	
	Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	authorities.forEach(authority -> {
		
		if(authority.getAuthority().equals("OPERATOR")) {
			try {
				redirectStrategy.sendRedirect(arg0, arg1, "/getLoansForm");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
		if(authority.getAuthority().equals("CLIENT")) {
			try {
				redirectStrategy.sendRedirect(arg0, arg1, "/applyFormLoanForm");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(authority.getAuthority().equals("ROLE_ADMIN")) {
			try {
				redirectStrategy.sendRedirect(arg0, arg1, "/addOperatorForm");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
           // throw new IllegalStateException();
        }
	});
	
}

}