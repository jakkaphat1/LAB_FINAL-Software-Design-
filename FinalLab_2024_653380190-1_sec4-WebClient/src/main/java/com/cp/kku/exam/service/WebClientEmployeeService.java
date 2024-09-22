package com.cp.kku.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cp.kku.exam.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientEmployeeService {

	@Autowired
	private final WebClient employeeWebClient;

	@Autowired
	public WebClientEmployeeService(WebClient employeeWebClient) {
		this.employeeWebClient = employeeWebClient;
	}

	public Flux<Employee> getAllEmployee() {
		return employeeWebClient.get().uri("/employees").retrieve() // ดึงข้อมูลขึ้นมา
				.bodyToFlux(Employee.class);
	}

	public Mono<Employee> getEmployeeById(Long id) {
		return employeeWebClient.get().uri("/employees/{id}", id).retrieve().bodyToMono(Employee.class); // Convert
		// ข้อมูลที่ได้เป็นเป็น
		// Mono จาก Class
		// Author
	}

	public Mono<Employee> createEmployee(Long id,Employee employee) {
		return employeeWebClient.post().uri("/employees/addnew/{id}",id).bodyValue(employee).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						clientResponse -> Mono.error(new RuntimeException("Client error during createEmployee")))
				.bodyToMono(Employee.class).doOnNext(response -> System.out.println("Response received: " + response))
				.doOnError(error -> System.err.println("Error occured: " + error.getMessage()));
	}

//	public Mono<Employee> createEmployee(Long id, Employee employee) {
//		return WebClientEmployeeService.post().uri("/employees/addnew/{id}", id).bodyValue(employee).retrieve()
//				.onStatus(status -> status.is4xxClientError(), clientResponse -> {
//					return Mono.error(new RuntimeException("Client error"));
//				}).onStatus(status -> status.is5xxServerError(), clientResponse -> {
//					return Mono.error(new RuntimeException("Server error"));
//				}).bodyToMono(Employee.class);
//	}

	public Mono<Employee> updateEmployee(Long id, Employee employee) {
	    System.out.println("Edited Employee: " + employee.toString());
	    return employeeWebClient.put().uri("/employees/edit/{id}", id).bodyValue(employee).retrieve()
	            .bodyToMono(Employee.class);
	}

//	public Mono<Void> deleteEmployeeById(Long id) {
//		return employeeWebClient.delete().uri("/employees/{id}", id).retrieve().bodyToMono(Void.class);
//	}
	
	public Mono<Void> deleteEmployeeById(Long id) {
	    return employeeWebClient.delete().uri("/employees/delete/{id}", id).retrieve().bodyToMono(Void.class);
	}
}
