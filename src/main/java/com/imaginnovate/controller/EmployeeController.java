package com.imaginnovate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.dto.EmployeeDto;
import com.imaginnovate.dto.EmployeeTaxInfo;
import com.imaginnovate.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;

	@PostMapping("/create")
	public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeDto));
	}

	@GetMapping("/employees/tax-details")
	public ResponseEntity<List<EmployeeTaxInfo>> getEmployeeTaxInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeTaxInfo());
	}

}
