package org.acme;

import java.util.List;

import javax.persistence.criteria.Order;

public class Orders{
	public String id;
	public String name;
	public int price;
	
	public Orders() {

	}

	public Orders(String name, int price) {
		this.name = name;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}