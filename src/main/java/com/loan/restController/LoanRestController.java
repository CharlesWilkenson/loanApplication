package com.loan.restController;

import java.security.Principal;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loan.dao.RandomPassword;
import com.loan.dao.Services;
import com.loan.entities.AppUser;
import com.loan.entities.Loan;
import com.loan.entities.Personal;

@RestController
public class LoanRestController {

	@Autowired
	private Services dao;
	                         
	
    //Used for a new customer to register
@PostMapping("/register")
public ResponseEntity<Object> register(
		@RequestParam(name = "email")String email,
		@RequestParam(name = "password")String password){

try {
dao.register(new AppUser(email,password), "OPERATOR");
}catch (Exception e) {

return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
}


return new ResponseEntity<Object>(HttpStatus.CREATED);
}

	
	
//This method is used by the ADMIN add a Operator in the system
	@PostMapping("/addOperator")
	public ResponseEntity<Object> addOperator(
			@RequestParam(name = "email")String email,
			@RequestParam(name = "password")String password){

		try {
			dao.addOperator(new AppUser(email,password), "OPERATOR");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	
	//This method is used to generate a password when the ADMIN add a operator in the system
	// This password should not be seen to the ADMIN it should be sent to the Operator's email. I did not follow  these rules.

	@GetMapping("/generatePass")
	public ResponseEntity<Object> generatePass(){
		String password=RandomPassword.randomAlphaNumeric(8);
	
		
		return new ResponseEntity<Object>(password,HttpStatus.CREATED);
	}
	
	
	//This method add the first loan for an existing customer he/she must provide all the infos
	@PostMapping("/addLoanForNewClient")
	public ResponseEntity<Object> addLoanForNewClient(
			@RequestParam(name = "lastname")String lastname,
			@RequestParam(name = "firstname")String firstname,
			@RequestParam(name = "day")int day,
			@RequestParam(name = "month")int month,
			@RequestParam(name = "year")int year,
			@RequestParam(name = "employer")String employer,
			@RequestParam(name = "salary")double salary,
			@RequestParam(name = "monthly_Liability")double monthly_Liability,
			@RequestParam(name = "requested_Amount")double requested_Amount,
			@RequestParam(name = "request_term")int request_term ,Principal principal){
		
		
		try {
			String dateBirth=day+"/"+month+"/"+year;
			
			Personal personal=new Personal(principal.getName(), firstname, lastname, dateBirth, employer, salary);
			Loan loan=new Loan(monthly_Liability, requested_Amount, request_term, personal);

			String str = "abcdefghijklmnopqrstuvwxyz"; 
			int sumOfLetters=0;
		    for (char ch : str.toCharArray()) {
		       
		        	sumOfLetters += 1 + ch - 'a';      
		    }
		  
		    
			int a=(14 -month) / 12;
			int y= year +4800 - a;
			int m = month +12*a -3;
			
			
			int JD= day + (153*m +2)/5 + 365*y + y/4 -y/100 + y/400 -32045; 
		    
			
			
		   // Score = (Sum of first name letter positions in the alphabet (a = 1, z = 26)) + Salary * 1.5 - MonthlyLiability * 3 + (year of birth) - (month of birth) - (Julian day of the year of birth (1st Feb = 32, etc.))`
		    double score=(sumOfLetters) + salary * 1.5 - monthly_Liability * 3 + (year) - (month) -JD;
		 
		    
		    if(score <2500) {
		    	loan.setMark("Must be Rejected");
		    }else if(score > 3500) {
		    	loan.setMark("Must be Approved");
		    }else {
		    	loan.setMark("Must be Manual");
		    }
		    
	
			dao.createLoan(personal, loan);
			
		}catch (Exception e) {
			// TODO: handle exception
		}

		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	
	
	//This method add a loan for an existing customer he/she does not need to add somme info like dateBirth, lastname, Firstname
	@PostMapping("/addLoanForExistingClient")
	public ResponseEntity<Object> addLoanForExistingClient(
			@RequestParam(name = "employer")String employer,
			@RequestParam(name = "salary")double salary,
			@RequestParam(name = "monthly_Liability")double monthly_Liability,
			@RequestParam(name = "requested_Amount")double requested_Amount,
			@RequestParam(name = "request_term")int request_term, Principal principal){
		
		
		try {
	
			
			Personal personal=dao.findByEmail(principal.getName());
	System.out.println("DATE "+personal.getDateBirth());
			   
			int day=Integer.parseInt(personal.getDateBirth().substring(0, 2));
			int month=Integer.parseInt(personal.getDateBirth().substring(3, 5));
			int year=Integer.parseInt(personal.getDateBirth().substring(6, 10));

			int a=(14 -month) / 12;
			int y= year +4800 - a;
			int m = month +12*a -3;
			
			
			int JD= day + (153*m +2)/5 + 365*y + y/4 -y/100 + y/400 -32045; 
			
	        personal.setEmployer(employer);
	        personal.setSalary(salary);
			
			Loan loan=new Loan(monthly_Liability, requested_Amount, request_term, personal);
			
			
			
			String str = "abcdefghijklmnopqrstuvwxyz"; 
			int sumOfLetters=0;
		    for (char ch : str.toCharArray()) {
		       
		        	sumOfLetters += 1 + ch - 'a';      
		    }
		    System.out.print("JULIAN DAY "+JD); 
			
			
		   // Score = (Sum of first name letter positions in the alphabet (a = 1, z = 26)) + Salary * 1.5 - MonthlyLiability * 3 + (year of birth) - (month of birth) - (Julian day of the year of birth (1st Feb = 32, etc.))`
		    double score=(sumOfLetters) + salary * 1.5 - monthly_Liability * 3 + (year) - (month) -JD;
		 
		    
		    if(score <2500) {
		    	loan.setMark("Must be Rejected");
		    }else if(score > 3500) {
		    	loan.setMark("Must be Approved");
		    }else {
		    	loan.setMark("Must be Manual");
		    }
			
			
			dao.createLoan(personal, loan);
			
		}catch (Exception e) {
			// TODO: handle exception
		}

		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	



	
}
