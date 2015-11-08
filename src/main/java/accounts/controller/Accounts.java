package accounts.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import accounts.model.Account;

@Produces(MediaType.APPLICATION_JSON)
public interface Accounts {

	@GET
	public Response getAccounts();

	@GET
	@Path("{id}")
	public Response getAccount(@PathParam("id") Long id);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAccount(Account account);

	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public Response updateAccount(@FormParam("id") String id,
			@FormParam("value") String value);

	@DELETE
	@Path("{id}")
	public Response deleteAccount(@PathParam("id") Long id);
}
