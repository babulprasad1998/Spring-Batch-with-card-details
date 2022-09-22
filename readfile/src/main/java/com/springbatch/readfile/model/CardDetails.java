
package com.springbatch.readfile.model;

//import java.util.Date;

//import org.springframework.format.annotation.DateTimeFormat;

public class CardDetails {

	String customerName;
	String cardNumber;
	// @DateTimeFormat(pattern = "YYYY-MM-DD")
	String expiryDate;
	String mobileNumber;
	String email;

	// public setter and getter methods
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "CardDetails[customerName=" + customerName + ", cardNumber=" + cardNumber + ", expiryDate=" + expiryDate
				+ ", mobileNumber=" + mobileNumber + ", email=" + email + "]";
	}

}
