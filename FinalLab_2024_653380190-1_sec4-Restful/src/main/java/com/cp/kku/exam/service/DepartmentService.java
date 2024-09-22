package com.cp.kku.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.kku.exam.model.Department;

@Service
public class DepartmentService {
	@Autowired
	DepartmentRepository departmentRepository;
	
	public List<Department> getDepartment(){
		List<Department> departments = (List<Department>) departmentRepository.findAll();
		return departments;
	}

	
	
	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id).orElseThrow(()->
		new DepartmentNotFoundException(id));
	}
	
	public void save(Department c) {
		departmentRepository.save(c);
	}
	
	public Department addDepartment(Department c) {
		departmentRepository.save(c);
		return c;
	}
	
	public void deleteById(Long id) {
		Department department = departmentRepository.findById(id).orElseThrow(()->
		new DepartmentNotFoundException(id));
		departmentRepository.delete(department);
	}
	
	public Department updateDepartment(Long id,Department c) {
		Department existingDepartment = departmentRepository.findById(id).get();
		existingDepartment.setId(c.getId());
		existingDepartment.setName(c.getName());
	    return departmentRepository.save(existingDepartment);
	}
}
