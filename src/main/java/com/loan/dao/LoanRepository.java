package com.loan.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.loan.entities.Loan;
import com.loan.entities.Personal;

public interface LoanRepository extends JpaRepository<Loan, Integer>{

	@Query("SELECT l FROM Loan l WHERE l.personal.id=:x")
	public List<Loan> findByPersonal(@Param("x") Long personal_id);
	
}
