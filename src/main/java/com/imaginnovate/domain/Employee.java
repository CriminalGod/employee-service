package com.imaginnovate.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_employee")
public class Employee extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull(message = "Employee Code Should not be null")
	@NotBlank(message = "Employee Code Should not be empty")
	@Column(name = "employee_code")
	private String employeeCode;

	@NotNull(message = "First Name Should not be null")
	@NotBlank(message = "First Name Should not be empty")
	@Column(name = "first_name")
	private String firstName;

	@NotNull(message = "Last Name Should not be null")
	@NotBlank(message = "Last Name Should not be empty")
	@Column(name = "last_name")
	private String lastName;

	@NotNull(message = "Email Should not be null")
	@NotBlank(message = "Email Should not be empty")
	@Email(message = "Please provide well formed email")
	@Column(name = "email")
	private String email;

	@NotNull(message = "Date of Joining Should not be null")
	@Column(name = "date_of_joining")
	private LocalDate dateOfJoining;

	@NotNull(message = "Monthly Salary Should not be null")
	@Column(name = "monthly_salary")
	private Double monthlySalary;

	@Size(min = 1, message = "At least one phone number must be provided.")
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EmployeePhoneNumber> employeePhoneNumberList;

}
