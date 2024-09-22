package com.cp.kku.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp.kku.exam.model.Department;
import com.cp.kku.exam.model.Employee;
import com.cp.kku.exam.service.DepartmentService;
import com.cp.kku.exam.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	DepartmentService departmentService;
	
	//Correct
	@GetMapping("employees")
	public ResponseEntity<List<Employee>> getEmployee(){
		List<Employee> employees = employeeService.getEmployee();
		return new ResponseEntity<>(employees,HttpStatus.OK);
	} 
	
	//Correct
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id")
	Long Id){
		Employee employee = employeeService.getEmployeeById(Id);
		return new ResponseEntity<>(employee,HttpStatus.OK);
	}
	
//	@PostMapping("/employees") // add new Employee
//	  public Employee addNewEmployee(@RequestBody Employee employee) {
//		  System.out.println(employee);
//		  Employee savedEmployee = employeeService.addEmployee(employee);
//		  return savedEmployee;
//	  }
	//Correct
	@PostMapping("/employees/addnew/{id}")
	public ResponseEntity<Employee> addEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
		Department department = departmentService.getDepartmentById(id);
		employee.setDepartment(department);
		Employee savedEmployee = employeeService.addEmployee(employee);
		return ResponseEntity.ok(savedEmployee);
	}
	
	//Correct
	@PutMapping("/employees/edit/{id}") //update
	  ResponseEntity<Employee> updateEmployee(@RequestBody Employee newEmployee, 
			  @PathVariable Long id) {
		Employee updateEmployee = employeeService.updateEmployee(id, newEmployee);
	     return ResponseEntity.ok(updateEmployee);
	  }
	
	//Correct
	@DeleteMapping("/employees/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
