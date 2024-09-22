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

import com.cp.kku.exam.service.DepartmentService;
import com.cp.kku.exam.model.Department;
@RestController
@RequestMapping("/api")
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
	
	//correct
	@GetMapping("/departments")
	public ResponseEntity<List<Department>> getDepartment(){
		List<Department> departments = departmentService.getDepartment();
		return new ResponseEntity<>(departments,HttpStatus.OK);
	}
	
	//correct
	@GetMapping("/departments/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable("id")
	Long Id){
		Department department = departmentService.getDepartmentById(Id);
		return new ResponseEntity<>(department,HttpStatus.OK);
	}
	
	//correct
	@PostMapping("/departments") // add new departmentป
	  public Department addNewDepartment(@RequestBody Department department) {
		  System.out.println(department);
		  Department savedDepartment = departmentService.addDepartment(department);
		  return savedDepartment;
	  }
	
	//correct
	@PutMapping("/departments/edit/{id}") //update
	  ResponseEntity<Department> updateDepartment(@RequestBody Department newDepartment, 
			  @PathVariable Long id) {
		  
	     Department updateDepartment = departmentService.updateDepartment(id, newDepartment);
	     return ResponseEntity.ok(updateDepartment);
	  }
	
	//correct
	@DeleteMapping("/departments/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteById(id);
        return ResponseEntity.ok("Department deleted successfully");
    }
	
}
