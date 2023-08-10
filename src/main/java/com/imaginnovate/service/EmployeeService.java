package com.imaginnovate.service;

import java.util.List;

import com.imaginnovate.dto.EmployeeDto;
import com.imaginnovate.dto.EmployeeTaxInfo;

public interface EmployeeService {

	EmployeeDto createEmployee(EmployeeDto employeeDto);

	List<EmployeeTaxInfo> getEmployeeTaxInfo();

}
