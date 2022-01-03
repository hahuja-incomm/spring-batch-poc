package com.example.demo;

public class Person {

	private Long ccNumber;
	private String lastName;
	private String firstName;
	private Double balance;
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String dueDate;
	private String status;

	public Person() {
	}

	public Person(Long ccNumber,String firstName, String lastName, Double balance, String dueDate, String status) {
		this.ccNumber=ccNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance= balance;
		this.dueDate = dueDate;
		this.status = status;
	}

	public void setCcNumber(Long ccNumber) {
		this.ccNumber = ccNumber;
	}

	public Long getCcNumber() {
		return ccNumber;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CC Number: "+  ccNumber +"firstName: " + firstName + ", lastName: " + lastName +", balance: "+balance+", DueDate: "+dueDate+", Status: "+status;
	}

}