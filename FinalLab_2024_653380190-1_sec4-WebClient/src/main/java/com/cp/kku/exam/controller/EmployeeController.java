package com.cp.kku.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cp.kku.exam.model.Department;
import com.cp.kku.exam.model.Employee;
import com.cp.kku.exam.service.WebClientDepartmentService;
import com.cp.kku.exam.service.WebClientEmployeeService;

import reactor.core.publisher.Mono;

@Controller
public class EmployeeController {

	@Autowired
	WebClientEmployeeService employeeService;

	@Autowired
	WebClientDepartmentService departmentService;

	public EmployeeController(WebClientEmployeeService employeeService, WebClientDepartmentService departmentService) {
		this.employeeService = employeeService;
		this.departmentService = departmentService;
	}

	// Get All Employee ->correct✅
	@GetMapping("/web/employees")
	public String getListAllEmployee(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployee().collectList().block());
		return "employeeListCRUD";
	}

	// Get Employee by Id ->correct✅
	@GetMapping("/web/employees/view/{id}")
	public String getEmployeeById(@PathVariable Long id, Model model) {
		Mono<Employee> employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee.block());
		return "employeeDetail";
	}

	// Get view add Employee form ->correct✅
	@GetMapping("/web/employees/form")
	public String getAddEmployeeForm(Model model) {
		Mono<List<Department>> departments = departmentService.getAllDepartment().collectList();
		model.addAttribute("employee", new Employee());
		model.addAttribute("departments", departments.block());
		return "addEmployeeForm";
	}

	// Create Employee ->correct✅
	@PostMapping("/web/employees/form")
	public String createEmployee(@ModelAttribute("employee") Employee employee, Model model) {
		Employee newEmployee = new Employee();
		newEmployee.setName(employee.getName());
		newEmployee.setSalary(employee.getSalary());
		Long departmentId = employee.getDepartment().getId();
		model.addAttribute("employee", employeeService.createEmployee(departmentId, newEmployee).block());
		return "redirect:/web/employees";
	}

	// Get view of edit employee ->correct✅
	@GetMapping("/web/employees/edit/{id}")
	public String getEditEmployeeForm(@PathVariable("id") Long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id).block();
		List<Department> departments = departmentService.getAllDepartment().collectList().block();
		model.addAttribute("employee", employee);
		model.addAttribute("departments", departments);
		return "editEmployeeForm";
	}
	
	//update employee ->correct✅
	@PostMapping("/web/employees/edit/{id}")
	public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute("employee") Employee employee,
			Model model) {
		Employee editEmployee = new Employee();
		editEmployee.setName(employee.getName());
		editEmployee.setSalary(employee.getSalary());
		editEmployee.setDepartment(departmentService.getDepartmentById(employee.getDepartment().getId()).block());
		model.addAttribute("employee", employeeService.updateEmployee(id, editEmployee).block());
		return "redirect:/web/employees";
	}

	// Delete 
	@GetMapping("/web/employees/delete/{id}")
	public String deleteEmployee(@PathVariable("id") Long id) {
	    employeeService.deleteEmployeeById(id).block();
	    return "redirect:/web/employees";
	}
	
}
