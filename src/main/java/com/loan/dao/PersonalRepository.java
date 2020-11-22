package com.loan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.entities.Personal;

public interface PersonalRepository extends JpaRepository<Personal, Long>{

	public Personal findByEmail(String email);
}
