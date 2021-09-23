package org.opportunity.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection="cart")
public class Orders extends PanacheMongoEntity{
	
	@BsonProperty("status")
	public String status;
	
	@BsonProperty("subtotal")
	public int subtotal;
	
	@BsonProperty("total")
	public int total;
	
	@BsonProperty("order_date")
	public LocalDateTime created;
	
	@BsonProperty("shipping_charge")
	public int shippingCharge;
	
	@BsonProperty("customer_id")
	public int customerId;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	public List<OrderItem> orderItem;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", referencedColumnName = "bill_id")
	private Billing billing;

	public Orders() {}

	public Orders(String status, int subtotal, int total, LocalDateTime created, int shippingCharge, int customerId,
			List<OrderItem> orderItem, Billing billing) {
		super();
		this.status = status;
		this.subtotal = subtotal;
		this.total = total;
		this.created = created;
		this.shippingCharge = shippingCharge;
		this.customerId = customerId;
		this.orderItem = orderItem;
		this.billing = billing;
	}
	
	@PrePersist
	void onCreate() {
		this.setCreated(LocalDateTime.now());
	}

	private LocalDateTime getCreated(LocalDateTime created) {
		return created;	
	}
	
	private void setCreated(LocalDateTime created) {
		this.created = created;		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(int shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public LocalDateTime getCreated() {
		return created;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", status=" + status + ", subtotal=" + subtotal + ", total=" + total
				+ ", created=" + created + ", shippingCharge=" + shippingCharge + ", customerId=" + customerId
				+ ", orderItem=" + orderItem + "]";
	}	
	
}
