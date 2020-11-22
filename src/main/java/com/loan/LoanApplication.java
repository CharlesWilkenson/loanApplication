package com.loan;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.loan.dao.RoleRepository;
import com.loan.dao.UserRepository;
import com.loan.entities.AppRole;
import com.loan.entities.AppUser;


@SpringBootApplication
public class LoanApplication {

	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
		// Below line gets current date/time
		Calendar rightNow = Calendar.getInstance();
		// put day of the year into an integer, which I think is the same
		// as Julian Day?
		int iDayOfYear = rightNow.get(Calendar.DAY_OF_YEAR);

		System.out.println("The Julian Day of the Year " +iDayOfYear);
	}

	
	
	
	
	  @Bean public BCryptPasswordEncoder bCryptPasswordEncoder() { return new
	  BCryptPasswordEncoder(); }
	 
	
	
	@Bean
	 CommandLineRunner init() {
		 
		Stream.of(new AppRole("CLIENT"),new AppRole("OPERATOR"),new AppRole("ADMIN"))
		.forEach(r ->{
		AppRole role=roleRepository.findByName(r.getName());	
			
		if(role==null) {
			roleRepository.save(r);
		}
	
		});
	

		register(new AppUser("admin@gmail.com","admin") );
		
		 
		 return null;
	 }
	
	public void register(AppUser appUser) {	
		
		try {
		String hashPassword=passwordEncoder.encode(appUser.getPassword());
		appUser.setPassword(hashPassword);
		 System.out.println("1");
		userRepository.save(appUser);

		AppRole role=roleRepository.findByName( "ADMIN");
		 System.out.println("ROLE FOR ADMIN "+role);
		 System.out.println("2");
		appUser.getAppRoles().add(role);
		 System.out.println("3");
		 
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	                  
}
