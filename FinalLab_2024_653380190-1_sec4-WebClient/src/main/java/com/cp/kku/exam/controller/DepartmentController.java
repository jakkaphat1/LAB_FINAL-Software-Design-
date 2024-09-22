package com.cp.kku.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.cp.kku.exam.model.Department;
import com.cp.kku.exam.service.WebClientDepartmentService;

import reactor.core.publisher.Mono;

@Controller
public class DepartmentController {
	@Autowired
	WebClientDepartmentService departmentService;
	
	public DepartmentController(WebClientDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	
	//Correct
	@GetMapping("/web/departments")
	public String getAllListDepartment(Model model) {
		model.addAttribute("departments", departmentService.getAllDepartment().collectList().block());
		return "departmentListCRUD";
	}
	
	//Get Department by Id //Correct
	@GetMapping("/web/departments/{id}")
	public String getDepartmentById(@PathVariable Long id, Model model) {
		Mono<Department> department = departmentService.getDepartmentById(id);
		model.addAttribute("department", department.block());
		return "departmentDetail";
	}
	
	//Get view of add Department  //Correct
	@GetMapping("/web/departments/form")
	public String getAddDepartmentForm(Model model) {
		model.addAttribute("departments", new Department());
		return "addDepartmentForm";
	}

	//Add Department //Correct
	@PostMapping("/web/departments/form")
	public String addDepartment(@ModelAttribute Department department, Model model) {
		Mono<Department> createDepartment = departmentService.createDepartment(department);
		model.addAttribute("departments", createDepartment.block());
		return "redirect:/web/departments";
	}
	
	//Get view of edit Department //Correct
	@GetMapping("/web/departments/edit/{id}")
	public String editDepartment(@PathVariable Long id, Model model) {
	    Department department = departmentService.getDepartmentById(id).block();
	    model.addAttribute("departments", department);
	    return "editDepartmentForm";
	}

	//Edit Department //Correct
	@PutMapping("/web/departments/edit/{id}")
	public String editDepartment(@PathVariable Long id, @ModelAttribute("departments") Department department, Model model) {
	    Mono<Department> editDepartment = departmentService.editDepartment(id, department);
	    model.addAttribute("departments", editDepartment.block());
	    return "redirect:/web/departments";
	}
	
	//Delete Department //Correct
	@GetMapping("/web/departments/delete/{id}")
	public String deleteDepartment(@PathVariable("id") Long id) {
		departmentService.deleteDepartmentById(id).block();
		return "redirect:/web/departments";
	}
}
