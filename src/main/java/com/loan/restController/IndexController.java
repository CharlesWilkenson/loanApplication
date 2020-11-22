package com.loan.restController;

import java.io.IOException;

import java.security.Principal;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.loan.dao.Services;
import com.loan.entities.Loan;
import com.loan.entities.Personal;

@Controller
public class IndexController {

	@Autowired
	private Services dao;
	
	
	//Those methods below are used to render views to the end users
	
	
	
	@GetMapping("/home")
	public String home() {		
		return "home";
	}
	
	@GetMapping("/registerForm")
	public String register() {		
		return "register";
	}
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "new_account", required = false) String new_account,
			@RequestParam(value = "errorCode", required = false) String errorCode)
			throws ServletException, IOException {
		ModelAndView model = new ModelAndView();


		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null || authentication instanceof AnonymousAuthenticationToken)
		{
			if (error != null) {
				model.addObject("css", "danger");
				model.addObject("msg", error);
			} else if (logout != null) {
				model.addObject("css", "success");
				model.addObject("logout", "You have been loged out successfully");


			} 
		
			model.setViewName("login");

			return model;
		}
		
	
              else return new ModelAndView("redirect:/");

	}
	
	
	
	
	
	
	
	
	
	@GetMapping("/applyFormLoanForm")
	public String applyFormLoan(Principal principal) {	
		String view="";
		

		try {
	   Personal personal =dao.findByEmail(principal.getName());
	   System.out.println("PERSOMNAL  "+personal);
	   if(personal==null) {
		   return "newClientloan";
	   }else {
		   return "existClientloan";
	   }

		}catch (Exception e) {
		}
 
	
		return view;
		
		//return view;
	}
	
	
	@GetMapping("/getLoansForm")
	public String getLoans(Model model ) {	
		
		List<Loan> loans=dao.allLoans();
		model.addAttribute("loans", loans);
		return "listloans";
	}
	
	@GetMapping("/getPersonalsForm")
	public String getPersonalsForm(Model model , Principal principal) {	
		
		List<Personal> personals=dao.allPersonals();
		model.addAttribute("personals", personals);
		return "listPersonals";
	}
	
	
	@GetMapping("/getMyLoansForm")
	public String getMyLoans(Model model,Principal principal ) {	
		   Personal personal =dao.findByEmail(principal.getName());
		   
		List<Loan> loans=dao.findByPersonal(personal.getPersonal_id());
		model.addAttribute("loans", loans);
		return "listloansForSinglePersonal";
	}
	
	
	
	@GetMapping("/addOperatorForm")
	public String addOperator() {		
		return "addOperator";
	}
	
	
	
	@GetMapping("/deleteLoan")
	public String deleteLoan(int id,Model model){
	
		try {
        dao.deleteLoan(id);
    	List<Loan> loans=dao.allLoans();
		model.addAttribute("loans", loans);
		}catch (Exception e) {
		
		}
		
		return "listloans";
	};
	
	
	//Used for an Operator to mark a loan
	@GetMapping("/markLoan")
	public String markLoan(int id,String mark,Model model){
	
		try {
        dao.addMarkToLoan(id, mark);
    	List<Loan> loans=dao.allLoans();
		model.addAttribute("loans", loans);
		}catch (Exception e) {
		
		}
		
		return "listloans";
	};
	
}
