package com.loan.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loan.entities.AppRole;
import com.loan.entities.AppUser;

@Service
public class UserDetailsServicesImpl  implements UserDetailsService {

	@Autowired
	private UserRepository userRepository; 
	
	  @Override public UserDetails loadUserByUsername(String email) throws
	  UsernameNotFoundException {
	  
	  AppUser user=userRepository.findByEmail(email); if(user==null) throw new
	  UsernameNotFoundException("User registered with email "+email
	  +" does not exits");
	  
	  List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (AppRole role : user.getAppRoles()) {
			System.out.println("ROLES **"+role.getName());
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
	  
	 // Collection<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
	  
		
		/*
		 * authorities.forEach(r ->{ authorities.add(new
		 * SimpleGrantedAuthority(r.getAuthority()));
		 * 
		 * });
		 */
		 
	  
	  
	  return new User(user.getEmail(),user.getPassword(),authorities); }
	 

}
