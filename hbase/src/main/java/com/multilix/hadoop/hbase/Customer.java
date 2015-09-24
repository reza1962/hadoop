package com.multilix.hadoop.hbase;

public class Customer {
	private String email;
	private String password;
	
	public Customer(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "Customer [email=" + email + ", password=" + password + "]";
	}
	
	
}
