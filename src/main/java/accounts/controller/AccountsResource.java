package accounts.controller;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import accounts.dao.AccountDAO;
import accounts.model.Account;

public class AccountsResource implements Accounts {

	@Context
	ServletContext servletContext;

	@Context
	ServletConfig servletConfig;

	@Context
	UriInfo uriInfo;

	public Response getAccounts() {
		EntityManagerFactory emf = (EntityManagerFactory) servletContext
				.getAttribute("emf");
		AccountDAO accountDAO = new AccountDAO(emf);
		List<Account> accounts = accountDAO.getAccounts();
		return Response.status(Status.OK).entity(accounts).build();
	}

	public Response getAccount(Long id) {
		EntityManagerFactory emf = (EntityManagerFactory) servletContext
				.getAttribute("emf");
		AccountDAO accountDAO = new AccountDAO(emf);
		Account account = accountDAO.getAccount(id);
		return Response.status(Status.OK).entity(account).build();
	}

	public Response createAccount(Account account) {
		EntityManagerFactory emf = (EntityManagerFactory) servletContext
				.getAttribute("emf");
		AccountDAO accountDAO = new AccountDAO(emf);
		accountDAO.createAccount(account);
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		URI uri = uriBuilder.path(
				uriInfo.getAbsolutePath() + "/" + account.getId()).build();
		return Response.status(Status.CREATED)
				.header(HttpHeaders.LOCATION, uri.toASCIIString())
				.entity(account).build();
	}

	public Response deleteAccount(Long id) {
		EntityManagerFactory emf = (EntityManagerFactory) servletContext
				.getAttribute("emf");
		AccountDAO accountDAO = new AccountDAO(emf);
		accountDAO.deleteAccount(id);
		JsonObject jsonResponseObject = new JsonObject();
		jsonResponseObject.add("accountId", new JsonPrimitive(id));
		return Response.status(Status.OK).entity(jsonResponseObject).build();
	}

}
