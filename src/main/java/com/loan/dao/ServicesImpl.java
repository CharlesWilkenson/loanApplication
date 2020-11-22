package com.loan.dao;

import java.util.List;
import java.util.Optional;

import javax.management.relation.Role;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.loan.entities.AppRole;
import com.loan.entities.AppUser;
import com.loan.entities.Loan;
import com.loan.entities.Personal;

@Service
@Transactional
public class ServicesImpl implements Services{
@Autowired
private LoanRepository loanRepository; 
@Autowired
private PersonalRepository personalRepository;
@Autowired
private UserRepository userRepository; 
@Autowired
private RoleRepository roleRepository;

@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void createLoan(Personal personal, Loan loan) {
    personalRepository.save(personal);
    loan.setPersonal(personal);
	loanRepository.save(loan);
	}

	@Override
	public List<Loan> allLoans() {

		return loanRepository.findAll();
	}

	@Override
	public List<Loan> findByPersonal(Long id) {

		return loanRepository.findByPersonal(id);
	}

	
	@Override
	public void deleteLoan(int id) {
	loanRepository.deleteById(id);
		
	}

	@Override
	public void addMarkToLoan(int id, String mark) {
    Optional<Loan> optional=loanRepository.findById(id);
    if(optional.isPresent())optional.get().setMark(mark);
	
		
	}



	@Override
	public void register(AppUser appUser,String appRole) {		
		String hashPassword=passwordEncoder.encode(appUser.getPassword());
	     AppUser user= userRepository.findByEmail(appUser.getEmail());
	     if(user !=null) throw new RuntimeException("User with email "+ user.getEmail()+" already exists");
	     
		
		appUser.setPassword(hashPassword);
		userRepository.save(appUser);

		AppRole role=roleRepository.findByName( "CLIENT");
		appUser.getAppRoles().add(role);
		
	}
	
	@Override
	public void saveRole(AppRole appRole) {
	
	roleRepository.save(appRole);
	}

	@Override
	public void addOperator(AppUser appUser,String appRole) {
		String hashPassword=passwordEncoder.encode(appUser.getPassword());
		appUser.setPassword(hashPassword);
		AppUser  user= userRepository.save(appUser);
		AppRole role=roleRepository.findByName( "OPERATOR");
		user.getAppRoles().add(role);
	}

	@Override
	public AppUser findUserByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	public Personal findByEmail(String email) {
		Personal personal=personalRepository.findByEmail(email);
		if(personal==null) return null;
		
		return personal;
	}

	@Override
	public List<Personal> allPersonals() {
		return personalRepository.findAll();
	}





	
	
	
	
	
	
	
	
	
	
	
	
}
