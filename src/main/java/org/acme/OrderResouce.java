package org.acme;

import java.net.URI;
import java.util.ArrayList;
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

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Path("/orders")
public class OrderResouce {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrders() {
		List<Orders> orders = Orders.listAll();
		return Response.ok(orders).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getById(@PathParam("id") Long id) {
		return Orders.findByIdOptional(id)
				.map(orders -> Response.ok(orders).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).build());
	}
		
	@POST
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrders(Orders order) {
		Orders.persist(order);
		if(order.isPersistent()) {
			return Response.created(URI.create("/orders"+order.id)).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@PUT
	@Transactional
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrder(@PathParam("id") Long id, Orders order) {
		if(order.name == null) {
			throw new WebApplicationException("Order Name was not set on request.", 422);
		}
		Orders entity = Orders.findById(id);
		if(entity == null) {
			throw new WebApplicationException("Order with id "+id+" not exists", 404);
		}
		entity.name = order.name;
		
		return Response.ok(entity).build();
		
	}
	
	@DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteBydId(@PathParam("id") Long id) {
        boolean deleted = Orders.deleteById(id);
        if(deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
