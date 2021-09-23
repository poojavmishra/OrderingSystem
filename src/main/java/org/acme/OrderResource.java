package org.acme;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.criteria.Order;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Path("/orders")
public class OrderResource {
	
	String host = "127.0.0.1";
	String usrname = "Administrator";
	String pswd = "password";
	String bucketName = "cart";
	
	Cluster cluster = Cluster.connect(host, usrname,pswd);
	Bucket bucket = cluster.bucket(bucketName);
	
	Collection collection = bucket.defaultCollection();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Orders> getAllOrders() {
		QueryResult res = cluster.query("select id,name,price from cart");
		System.out.println("Output: " + res.rowsAsObject());
		return res.rowsAs(Orders.class);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getById(@PathParam("id") String id) {
		GetResult result = collection.get(id);
		Orders orderbyid = result.contentAs(Orders.class);
		return Response.ok(orderbyid).build();
	}
		
	@POST
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addOrder")
	public Response addOrder(Orders order) {
		collection.insert(order.getId(), order);
		return Response.ok(order).build();
	}
	
	@PUT
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateOrder(@PathParam("id") String id, Orders updatedOrder) {
		if(updatedOrder.name == null) {
			throw new WebApplicationException("Order Name was not set on request.", 422);
		}
		GetResult result = collection.get(id);
		if(result.equals(null)) {
			throw new WebApplicationException("Order with id "+id+" not exists", 404);
		}
		Orders order = result.contentAs(Orders.class);
		order.name = updatedOrder.name;
		order.price = updatedOrder.price;
		collection.replace(id, order);
		return Response.ok(order).build();
	}
	
	@DELETE
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteOrder(@PathParam("id") String id) {
		try {
			collection.remove(id);
		}
		catch(CouchbaseException e){
			throw new WebApplicationException("Order with id "+id+" does not exists.");
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
