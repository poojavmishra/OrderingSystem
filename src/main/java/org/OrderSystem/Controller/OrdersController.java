package org.opportunity.Controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.opportunity.model.Orders;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheQuery;

@Path("/orders")

public class OrdersController {

	@GET
	public List<Orders> getAllOrders() {
		return Orders.listAll();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Orders> getOrderById(@PathParam("id") String id){
		return Orders.findById(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrder(Orders order){
		order.persist();		
		return Response.ok(order).build();		
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateOrder(@PathParam("id") String id,Orders order) {
		order.update();
		return Response.ok(order).build();
	}
	
	@DELETE
	@Path("/{id}")
	
	public  void deleteOrderById(@PathParam("id") String id) {
		Orders entity = Orders.findById(new ObjectId(id));
		entity.delete();		
	}
}
