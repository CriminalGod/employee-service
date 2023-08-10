package com.imaginnovate.exception.handler;


import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleException(final Exception exception, final HttpServletRequest request) {
		exception.printStackTrace();
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.callerURL(request.getRequestURI());
		return error;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionResponse handleMethodArgumentNotValid(final MethodArgumentNotValidException exception, final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		StringBuilder sb = new StringBuilder();
		exception.getBindingResult().getFieldErrors().forEach(x -> sb.append(x.getDefaultMessage() + "--"));
		System.out.println(sb.toString());
		error.setErrorMessage(sb.toString());
		error.callerURL(request.getRequestURI());
		return error;

	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionResponse handleConstraintViolationException(final ConstraintViolationException exception, final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		StringBuilder sb = new StringBuilder();
		exception.getConstraintViolations().forEach(x -> sb.append(x.getMessage() + "--"));
		System.out.println(sb.toString());
		error.setErrorMessage(sb.toString());
		error.callerURL(request.getRequestURI());
		return error;

	}

}