package com.cp.kku.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cp.kku.exam.model.Department;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientDepartmentService {
	@Autowired
	private final WebClient departmentWebClient;
	
	@Autowired
	public WebClientDepartmentService(WebClient departmentWebClient) {
		this.departmentWebClient = departmentWebClient;
	} 
	
	public Flux<Department> getAllDepartment() {
		return departmentWebClient.get().uri("/departments").retrieve() // ดึงข้อมูลขึ้นมา
				.bodyToFlux(Department.class);
	}
	
	public Mono<Department> getDepartmentById(Long id) {
		return departmentWebClient.get().uri("/departments/{id}", id).retrieve().bodyToMono(Department.class); // Convert
																								// ข้อมูลที่ได้เป็นเป็น
																								// Mono จาก Class Author
	}
	
	public Mono<Department> createDepartment(Department department) {
		return departmentWebClient.post().uri("/departments").bodyValue(department).retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						clientResponse -> Mono.error(new RuntimeException("Client error during createDepartment")))
				.bodyToMono(Department.class).doOnNext(response -> System.out.println("Response received: " + response))
				.doOnError(error -> System.err.println("Error occured: " + error.getMessage()));
	}
	
	
	public Mono<Department> editDepartment(Long id, Department department) {
        return departmentWebClient.put()
                .uri("/departments/{id}", id)
                .bodyValue(department)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Client error: " + body)));
                })
                .onStatus(status -> status.is5xxServerError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Server error: " + body)));
                })
                .bodyToMono(Department.class)
                .doOnNext(updatedDepartment -> System.out.println("Department updated: " + updatedDepartment))
                .doOnError(error -> System.err.println("Error updating department: " + error.getMessage()));
    }
	
	public Mono<Void> deleteDepartmentById(Long id) {
		return departmentWebClient.delete().uri("/departments/{id}", id).retrieve().bodyToMono(Void.class);
	}
}
