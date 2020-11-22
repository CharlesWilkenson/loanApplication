package com.loan.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.management.relation.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table( uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class AppUser implements Serializable{


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false,unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<AppRole> appRoles=new ArrayList<AppRole>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public AppUser(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public Collection<AppRole> getAppRoles() {
		return appRoles;
	}
	public void setAppRoles(Collection<AppRole> appRoles) {
		this.appRoles = appRoles;
	}
	public AppUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
