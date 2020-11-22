package com.loan.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	  @Autowired private UserDetailsService userDetailsService;
	  
	  @Autowired private BCryptPasswordEncoder passwordEncoder;
	 
		@Autowired
	    CustomSuccessHandler customSuccessHandler;
	
		@Autowired
		private LogoutSuccessHandler logoutSuccessHandler;
		
	  @Override protected void configure(AuthenticationManagerBuilder auth) throws
	  Exception {
	  auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	  }
	  
	  
	  
	  @Override protected void configure(HttpSecurity http) throws Exception {

		  http
		  .csrf()
		  .disable()
                  .formLogin()
		  .loginPage("/login")
		 .successHandler(customSuccessHandler);
		  
		
		   http.logout()
		  .logoutSuccessUrl("/login")
		  .logoutUrl("/logout")
		  .permitAll();
		  


		    http.authorizeRequests() .antMatchers("/login",  "/registerForm", "/register", "/logout").permitAll();
		    
		
		    
		      http.authorizeRequests().antMatchers(HttpMethod.GET,"/generatePass").hasAuthority("ADMIN");
			  http.authorizeRequests().antMatchers("/applyFormLoanForm").hasAnyAuthority("ADMIN" ,"CLIENT","OPERATOR");
			  http.authorizeRequests().antMatchers(HttpMethod.GET,"/getLoansForm").hasAnyAuthority("ADMIN" ,"OPERATOR");
			  http.authorizeRequests().antMatchers(HttpMethod.GET,"/getPersonalsForm").hasAuthority("ADMIN");
			  http.authorizeRequests().antMatchers(HttpMethod.GET,"/addOperatorForm").hasAuthority("ADMIN");
			  http.authorizeRequests().antMatchers(HttpMethod.GET,"/getMyLoansForm").hasAuthority("CLIENT" );
			  http.authorizeRequests().antMatchers(HttpMethod.GET,"/markLoan").hasAnyAuthority("ADMIN" ,"OPERATOR");
			  
			  http.authorizeRequests().antMatchers(HttpMethod.GET,"/getLoansByClient").hasAuthority("CLIENT" );
			  http.authorizeRequests().antMatchers(HttpMethod.GET,"/deleteLoan").hasAuthority("OPERATOR");
			  http.authorizeRequests().antMatchers(HttpMethod.GET,"/getLoans").hasAuthority("OPERATOR");
			  
			  
			  http.authorizeRequests().antMatchers(HttpMethod.POST,"/addOperator").hasAuthority("ADMIN");
			  http.authorizeRequests().antMatchers(HttpMethod.POST,"/addLoanForExistingClient").hasAnyAuthority("ADMIN" ,"CLIENT","OPERATOR");
			  
			  http.authorizeRequests().antMatchers(HttpMethod.POST,"/addLoanForNewClient").hasAnyAuthority("ADMIN" ,"CLIENT","OPERATOR");
			 
	      


		  http.authorizeRequests().anyRequest().authenticated();
	       // Logout Config
	      http.authorizeRequests().and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
	  }
	  
	  
	  @Override public void configure(WebSecurity web) throws Exception { 

			web
			.ignoring()
			.antMatchers("/assets/**")
			.antMatchers("/assets/css/**")
			.antMatchers("/assets/css/**")
			.antMatchers("/assets/js/**")
			.antMatchers("/css/**")
					.antMatchers("/js/**")
					.antMatchers("/lib/**")
					.antMatchers("/lib/js/**")
					.antMatchers("/image/**");
	  }
	 
}
