package org.opportunity.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Billing extends PanacheMongoEntity{
	
	@BsonProperty("billing_address")
	public String billingAddress;
	
	@BsonProperty("billing_city")
	public String billingCity;
	
	@BsonProperty("billing_state")
	public String billingState;
	
	@BsonProperty("billing_zipcode")
	public int billingZipCode;
	
	protected Billing() {}

	public Billing(String billingAddress, String billingCity, String billingState, int billingZipCode) {
		this.billingAddress = billingAddress;
		this.billingCity = billingCity;
		this.billingState = billingState;
		this.billingZipCode = billingZipCode;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public int getBillingZipCode() {
		return billingZipCode;
	}

	public void setBillingZipCode(int billingZipCode) {
		this.billingZipCode = billingZipCode;
	}

	@Override
	public String toString() {
		return "Billing [billingAddress=" + billingAddress + ", billingCity=" + billingCity + ", billingState="
				+ billingState + ", billingZipCode=" + billingZipCode + "]";
	}
	
	
}
