package com.imaginnovate.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

	private Long id;

	private String employeeCode;

	@NotNull(message = "First Name Should not be null")
	@NotBlank(message = "First Name Should not be empty")
	private String firstName;

	@NotNull(message = "Last Name Should not be null")
	@NotBlank(message = "Last Name Should not be empty")
	private String lastName;

	@NotNull(message = "Email Should not be null")
	@NotBlank(message = "Email Should not be empty")
	@Email(message = "Please provide well formed email")
	private String email;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Date of Joining Should not be null")
	private LocalDate dateOfJoining;

	@NotNull(message = "Monthly Salary Should not be null")
	private Double monthlySalary;

	private Double yearlySalary;

	@NotNull(message = "Must provide atleat one Phone Number")
	@Size(min = 1, message = "At least one phone number must be provided.")
	private List<String> employeePhoneNumbersList;

	private List<EmployeePhoneNumberDto> employeePhoneNumberList;

	private Double taxAmount;

	private Double cessAmount;

}
