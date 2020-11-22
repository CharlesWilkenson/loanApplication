package com.loan.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table( uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Personal implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long personal_id;
	
	@Column(nullable = false,unique = true)
	private String email;

	@Column(nullable = false)
	private String firstname;
	@Column(nullable = false)
	private String lastname;
	@Column(nullable = false)
	private String dateBirth;
	@Column(nullable = false)
	private String employer;
	@Column(nullable = false)
	private double salary;
	@OneToMany(mappedBy = "personal")
	private Collection<Loan> loans=new ArrayList<Loan>();
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Personal(String email, String firstname, String lastname, String dateBirth, String employer, double salary) {
		super();
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateBirth = dateBirth;
		this.employer = employer;
		this.salary = salary;
	}
	public Long getPersonal_id() {
		return personal_id;
	}
	public Personal() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setPersonal_id(Long personal_id) {
		this.personal_id = personal_id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDateBirth() {
		return dateBirth;
	}
	public void setDateBirth(String dateBirth) {
		this.dateBirth = dateBirth;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Collection<Loan> getLoans() {
		return loans;
	}
	public void setLoans(Collection<Loan> loans) {
		this.loans = loans;
	}


}
