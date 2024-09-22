package com.cp.kku.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.kku.exam.model.Department;
import com.cp.kku.exam.model.Employee;


@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	public List<Employee> getEmployee(){
		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		return employees;
	}
	
	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id).orElseThrow(()->
		new RuntimeException("Could not found employee"+id));
	}
	
	public void save(Employee c) {
		employeeRepository.save(c);
	}
	
	public Employee addEmployee(Employee c) {
		employeeRepository.save(c);
		return c;
	}
	
	public void deleteById(Long id) {
		Employee employees = employeeRepository.findById(id).orElseThrow(()->
		new RuntimeException("Could not found employee"+id));
		employeeRepository.delete(employees);
	}
	
	public Employee updateEmployee(Long id,Employee c) {
		Employee existingEmployee = employeeRepository.findById(id).get();
		existingEmployee.setName(c.getName());
	    existingEmployee.setSalary(c.getSalary());
	    Department department = departmentRepository.findById(c.getDepartment().getId()).get();
	    existingEmployee.setDepartment(department);
	    return employeeRepository.save(existingEmployee);
	}
	
//	public Employee updateEmployee(Long id, Employee employee) {
//	    Employee existEmployee = employeeRepository.findById(id)
//	            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
//	    existEmployee.setName(employee.getName());
//	    existEmployee.setSalary(employee.getSalary());
//	    Department department = departmentRepository.findById(employee.getDepartment().getId())
//	            .orElseThrow(() -> new RuntimeException("Department not found with id: " + employee.getDepartment().getId()));
//	    existEmployee.setDepartment(department);
//	    return employeeRepository.save(existEmployee);
//	}
	
}
