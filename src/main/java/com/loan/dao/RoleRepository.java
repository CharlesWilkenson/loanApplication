package com.loan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.entities.AppRole;

public interface RoleRepository extends JpaRepository<AppRole, Long>{
public AppRole findByName(String name);
}
