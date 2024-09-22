package com.cp.kku.exam.service;

import org.springframework.data.repository.CrudRepository;

import com.cp.kku.exam.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{
	
}
