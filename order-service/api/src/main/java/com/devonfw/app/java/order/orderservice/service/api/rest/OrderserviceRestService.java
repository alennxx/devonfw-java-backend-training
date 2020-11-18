package com.devonfw.app.java.order.orderservice.service.api.rest;

import java.time.LocalDate;
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

import org.springframework.data.domain.Page;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.module.rest.common.api.RestService;

/**
 * @author NGASIORO
 *
 */
@Path("/orderservice/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface OrderserviceRestService extends RestService {

  // ITEMS:

  @GET
  @Path("/items/{name}")
  public ItemEto findItemByName(@PathParam("name") String name);

  @GET
  @Path("/items/find/{name}")
  public Page<ItemEto> findItemsWithNameLikeOrdered(@PathParam("name") String name);

  @PUT
  @Path("/items/save")
  public ItemEto saveItem(ItemEto item);

  @POST
  @Path("/items/increasePrice/{name}")
  public void increaseItemPriceByName(@PathParam("name") String name, Double amount);

  @DELETE
  @Path("/items/{id}")
  public void deleteItem(@PathParam("id") long id);

  // CUSTOMERS:

  @PUT
  @Path("/customers/save")
  public CustomerEto saveCustomer(CustomerEto customer);

  @DELETE
  @Path("/customers/{id}")
  public void deleteCustomer(@PathParam("id") long id);

  // ORDERS:

  @GET
  @Path("/orders/find/{creationDate}/{status}")
  public List<OrderEto> findOrdersByCreationDateAndStatus(@PathParam("id") LocalDate creationDate,
      @PathParam("status") OrderStatus status);

  @PUT
  @Path("/orders/save")
  public OrderCto saveOrder(OrderCto order);

  @DELETE
  @Path("/orders/{id}")
  public void deleteOrder(@PathParam("id") long id);

}
