package com.loan.entities;

import java.io.Serializable;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Loan implements Serializable{
	@Id
	private int loan_id;
	@Column(nullable = false)
	private double monthly_Liability ;
	@Column(nullable = false)
	private double requested_Amount;
	@Column(nullable = false)
	private int request_term; //days or months
	
	private String mark;
	
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	@ManyToOne
	private Personal personal;
	
	
	public Loan(double monthly_Liability, double requested_Amount, int request_term, String mark, Personal personal) {
		super();
		this.monthly_Liability = monthly_Liability;
		this.requested_Amount = requested_Amount;
		this.request_term = request_term;
		this.mark = mark;
		this.personal = personal;
	}

	public Loan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getLoan_id() {
		return loan_id;
	}

	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}

	public double getMonthly_Liability() {
		return monthly_Liability;
	}
	public void setMonthly_Liability(double monthly_Liability) {
		this.monthly_Liability = monthly_Liability;
	}
	public double getRequested_Amount() {
		return requested_Amount;
	}
	public void setRequested_Amount(double requested_Amount) {
		this.requested_Amount = requested_Amount;
	}
	public int getRequest_term() {
		return request_term;
	}
	public void setRequest_term(int request_term) {
		this.request_term = request_term;
	}
	public Personal getPersonal() {
		return personal;
	}
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}
	private static final long serialVersionUID = 1L;


	
	public Loan(int loan_id) {
		super();
		this.loan_id = loan_id;
	}

	private int loan_id() {
	Random rd=new Random();
	
	  return rd.nextInt(999999999);
	}

	public Loan(double monthly_Liability, double requested_Amount, int request_term, Personal personal) {
		super();
		
		this.loan_id=loan_id();
		this.monthly_Liability = monthly_Liability;
		this.requested_Amount = requested_Amount;
		this.request_term = request_term;
		this.personal = personal;
	}
	
	
}
