package accounts.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import accounts.exception.IllegalArgumentExceptionMapper;
import accounts.exception.JsonParseExceptionMapper;
import accounts.exception.NoResultExceptionMapper;
import accounts.exception.NumberFormatExceptionMapper;
import accounts.exception.ParseExceptionMapper;
import accounts.exception.RollbackExceptionMapper;
import accounts.gson.GsonJsonProvider;
import accounts.model.Account;

public class AccountsResourceTest {

	private static final String ADDRESS = "http://localhost:8080/user-accounts/";

	private static Server server;

	@BeforeClass
	public static void setUp() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();

		// Add resource class
		sf.setResourceClasses(AccountsResource.class);

		sf.setResourceProvider(AccountsResource.class,
				new SingletonResourceProvider(new AccountsResource()));

		// Add all providers defined in DD
		List<Object> providers = new ArrayList<Object>();
		providers.add(new GsonJsonProvider<Account>());
		providers.add(new NoResultExceptionMapper());
		providers.add(new NumberFormatExceptionMapper());
		providers.add(new JsonParseExceptionMapper());
		providers.add(new IllegalArgumentExceptionMapper());
		providers.add(new ParseExceptionMapper());
		providers.add(new RollbackExceptionMapper());
		sf.setProviders(providers);

		sf.setAddress(ADDRESS);

		server = sf.create();
	}

	@Test
	public void testCreateAccountWithMissingFirstName() {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new GsonJsonProvider<Account>());

		WebClient webClient = WebClient.create(ADDRESS, providers);
		webClient.path("accounts/").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);

		// Message body - Account without firstName
		Account account = new Account();
		account.setLastName("Dow");
		account.setEmail("john.doe@sun-fish.com");
		account.setDateOfBirth(new Date());

		// Make a POST request to create an account
		Response response = webClient.post(account);

		// 400 Bad Request must be returned
		assertTrue(response.getStatus() == 400);
	}

	@Test
	public void testCreateAccountWithoutLastName() {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new GsonJsonProvider<Account>());

		WebClient webClient = WebClient.create(ADDRESS, providers);
		webClient.path("accounts/").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);

		// Message body - Account without firstName
		Account account = new Account();
		account.setFirstName("John");
		account.setEmail("john.doe@sun-fish.com");
		account.setDateOfBirth(new Date());

		// Make a POST request to create an account
		Response response = webClient.post(account);

		// 400 Bad Request must be returned
		assertTrue(response.getStatus() == 400);
	}

	@Test
	public void testCreateAccountWithoutEmail() throws Exception {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new GsonJsonProvider<Account>());

		WebClient webClient = WebClient.create(ADDRESS, providers);
		webClient.path("accounts/").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);

		// Message body - Account without firstName
		Account account = new Account();
		account.setFirstName("John");
		account.setLastName("Dow");
		account.setDateOfBirth(new Date());

		// Make a POST request to create an account
		Response response = webClient.post(account);

		// 400 Bad Request must be returned
		assertTrue(response.getStatus() == 400);
	}

	@Test
	public void testCreateAccountWithInvalidEmail() throws Exception {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new GsonJsonProvider<Account>());

		WebClient webClient = WebClient.create(ADDRESS, providers);
		webClient.path("accounts/").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);

		// Message body - Account without firstName
		Account account = new Account();
		account.setFirstName("John");
		account.setLastName("Dow");
		account.setEmail("john.doe@.com");
		account.setDateOfBirth(new Date());

		// Make a POST request to create an account
		Response response = webClient.post(account);

		// 400 Bad Request must be returned
		assertTrue(response.getStatus() == 400);
	}

	@Test
	public void testCreateAccountWithoutDateOfBirth() throws Exception {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new GsonJsonProvider<Account>());

		WebClient webClient = WebClient.create(ADDRESS, providers);
		webClient.path("accounts/").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);

		// Message body - Account without firstName
		Account account = new Account();
		account.setFirstName("John");
		account.setLastName("Dow");
		account.setEmail("john.doe@sun-fish.com");

		// Make a POST request to create an account
		Response response = webClient.post(account);

		// 400 Bad Request must be returned
		assertTrue(response.getStatus() == 400);
	}

	@AfterClass
	public static void tearDown() {
		server.stop();
		server.destroy();
	}
}
