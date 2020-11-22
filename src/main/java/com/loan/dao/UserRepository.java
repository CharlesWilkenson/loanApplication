package com.loan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.entities.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long>{

	public AppUser findByEmail(String username);
	
	
	
	
}
