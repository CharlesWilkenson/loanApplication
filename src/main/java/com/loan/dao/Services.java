package com.loan.dao;

import java.util.List;

import com.loan.entities.AppRole;
import com.loan.entities.AppUser;
import com.loan.entities.Loan;
import com.loan.entities.Personal;


public interface Services {
public void createLoan (Personal personal,Loan loan);
public List<Loan> allLoans();

public List<Personal> allPersonals();

public List<Loan> findByPersonal(Long id);
public void deleteLoan(int id);
public void addMarkToLoan(int loan_id, String mark);
	

public void register(AppUser appUser,String role);
public void saveRole(AppRole appRole);

public Personal findByEmail(String email);
public void addOperator(AppUser appUser,String roleName);
public AppUser findUserByEmail(String email);
}
