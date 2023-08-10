package com.imaginnovate.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.imaginnovate.constant.Constant;
import com.imaginnovate.domain.Employee;
import com.imaginnovate.domain.EmployeePhoneNumber;
import com.imaginnovate.dto.EmployeeDto;
import com.imaginnovate.dto.EmployeePhoneNumberDto;
import com.imaginnovate.dto.EmployeeTaxInfo;
import com.imaginnovate.repository.EmployeeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepo employeeRepo;

	@PostConstruct
	public void insert() {
		List<Employee> el = new ArrayList<>();
		for (int index = 1; index <= 3; index++) {
			Employee employee = Employee.builder().employeeCode("EMP" + index).firstName("employee" + index)
					.lastName("last").email("email" + index + "@yahoo.com")
					.dateOfJoining(LocalDate.of(LocalDate.now().getYear(), 5, 16))
					.monthlySalary(Double.parseDouble(2800000 + index + "")).isActive(Boolean.TRUE).createdBy("1")
					.createdDate(LocalDateTime.now()).build();
			List<EmployeePhoneNumber> phl = new ArrayList<>();
			for (int i = 1; i <= 2; i++) {
				phl.add(EmployeePhoneNumber.builder().phoneNumber("989898797" + i).employee(employee).build());
			}
			employee.setEmployeePhoneNumberList(phl);
			el.add(employee);
		}
		employeeRepo.saveAll(el);
	}

	/**
	 * STORE EMPLOYEE DETAILS
	 * 
	 */
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		Employee employee = Employee.builder().build();
		BeanUtils.copyProperties(employeeDto, employee);
		employee.setEmployeePhoneNumberList(
				phoneDtoListToDomainList(employeeDto.getEmployeePhoneNumbersList(), employee));
		BeanUtils.copyProperties(employeeRepo.save(employee), employeeDto);
		employeeDto.setEmployeePhoneNumberList(phoneDomainToDto(employee));
		return employeeDto;
	}

	private List<EmployeePhoneNumberDto> phoneDomainToDto(Employee employee) {
		return employee.getEmployeePhoneNumberList().stream().map(i -> {
			EmployeePhoneNumberDto dto = new EmployeePhoneNumberDto();
			BeanUtils.copyProperties(i, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	private List<EmployeePhoneNumber> phoneDtoListToDomainList(List<String> phoneNumbers, Employee employee) {
		return Optional.ofNullable(phoneNumbers)
				.map(phn -> phn.stream().map(phone -> phoneDtoToDomain(employee, phone)).collect(Collectors.toList()))
				.orElseGet(ArrayList::new);
	}

	private EmployeePhoneNumber phoneDtoToDomain(Employee employee, String phone) {
		return EmployeePhoneNumber.builder().phoneNumber(phone).employee(employee).build();
	}

	/**
	 * EMPLOYEES' TAX DEDUCTION
	 * 
	 */
	@Override
	public List<EmployeeTaxInfo> getEmployeeTaxInfo() {
		List<Employee> employees = employeeRepo.findAll();

		LocalDate finYearStart = LocalDate.of(LocalDate.now().getYear(), 4, 1);
		LocalDate finYearEnd = LocalDate.of(LocalDate.now().getYear() + 1, 3, 31);
		return employees.stream().map(employee -> {

			LocalDate doj = employee.getDateOfJoining();
			LocalDate payableFromDate = doj.isBefore(finYearStart) ? finYearStart : doj;
			LocalDate payableToDate = finYearEnd.isBefore(LocalDate.now()) ? finYearEnd : LocalDate.now();
			int noOfPayableMonths = Math.max(0, payableFromDate.until(payableToDate).getMonths() + 1);

			double totalSalary = employee.getMonthlySalary() * noOfPayableMonths;
			double taxableAmount = Math.max(0, totalSalary - Constant.TAX_SLAB1);
			double tax = 0;

			if (taxableAmount > 0) {
				if (taxableAmount <= Constant.TAX_SLAB1) {
					tax = 0;
				} else {
					if (taxableAmount <= Constant.TAX_SLAB2) {
						tax = taxableAmount * Constant.TAX_SLAB1_RATE;
					} else {
						if (taxableAmount <= Constant.TAX_SLAB3) {
							tax = taxableAmount * Constant.TAX_SLAB2_RATE;
						} else {
							tax = taxableAmount * Constant.TAX_SLAB3_RATE;
						}
					}
				}
			}

			if (totalSalary > Constant.CESS_APPLICABLE_AMOUNT) {
				double cess = (totalSalary - Constant.CESS_APPLICABLE_AMOUNT) * Constant.CESS_RATE;
				tax += cess;
			}

			Double cessAmount = decimalsFormat(totalSalary > Constant.CESS_APPLICABLE_AMOUNT
					? (totalSalary - Constant.CESS_APPLICABLE_AMOUNT) * Constant.CESS_RATE
					: 0);

			return EmployeeTaxInfo.builder().employeeCode(employee.getEmployeeCode()).firstName(employee.getFirstName())
					.lastName(employee.getLastName()).yearlySalary(decimalsFormat(totalSalary))
					.taxAmount(decimalsFormat(tax)).cessAmount(cessAmount).build();

		}).collect(Collectors.toList());
	}

	private Double decimalsFormat(double value) {
		DecimalFormat decfor = new DecimalFormat("0.00");
		return Optional.ofNullable(value).map(decfor::format).map(Double::valueOf)
				.orElseGet(() -> Double.valueOf(decfor.format(0d)));
	}
}
