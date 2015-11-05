package accounts.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import accounts.exception.IllegalArgumentExceptionMapper;
import accounts.exception.JsonParseExceptionMapper;
import accounts.exception.NoResultExceptionMapper;
import accounts.exception.NumberFormatExceptionMapper;
import accounts.exception.ParseExceptionMapper;
import accounts.exception.RollbackExceptionMapper;
import accounts.gson.GsonJsonProvider;
import accounts.model.Account;

@RunWith(MockitoJUnitRunner.class)
public class AccountsResourceTest {

	@Mock
	ServletContext servletContext;

	@Mock
	ServletConfig servletConfig;

	@Mock
	EntityManagerFactory emf;

	@Mock
	EntityManager em;

	@Mock
	EntityTransaction et;

	@Mock
	Query queryAllAccounts;

	@Mock
	Query querySingleAccount;

	private static final String ADDRESS = "http://localhost:8080/user-accounts/";

	private static Server server;

	@BeforeClass
	public static void beforeClass() {
		// Bean to help easily create Server endpoints for JAX-RS
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

	@Before
	public void setUp() {
		when(em.getTransaction()).thenReturn(et);
		when(emf.createEntityManager()).thenReturn(em);
		when(servletContext.getAttribute("emf")).thenReturn(emf);
		when(servletConfig.getInitParameter("max.results")).thenReturn("10");

		// Return predefined account from getAllAccounts operation
		Account account = createSingleAccount();
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(account);
		when(queryAllAccounts.getResultList()).thenReturn(accounts);
		when(querySingleAccount.getSingleResult()).thenReturn(account);
		when(em.createNamedQuery("getAllAccounts"))
				.thenReturn(queryAllAccounts);
		when(em.createNamedQuery("getAccountById")).thenReturn(
				querySingleAccount);

		// Set ServletContext and ServletConfig as properties to Message
		server.getEndpoint().getInInterceptors()
				.add(new PreInvokeInInterceptor(servletContext, servletConfig));
	}

	@Test
	public void testGetAccounts() throws Exception {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new GsonJsonProvider<Account>());

		WebClient webClient = WebClient.create(ADDRESS, providers);
		webClient.path("accounts/").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);

		Response response = webClient.get();

		assertTrue(response.getStatus() == 200);

		String responseString = readResponse((InputStream) response.getEntity());
		assertTrue(responseString.contains("John"));
		assertTrue(responseString.contains("Dow"));
		assertTrue(responseString.contains("john.doe@sun-fish.com"));
	}

	@Test
	public void testGetAccount() throws Exception {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new GsonJsonProvider<Account>());

		WebClient webClient = WebClient.create(ADDRESS, providers);
		webClient.path("accounts/").path("1")
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);

		Response response = webClient.get();

		assertTrue(response.getStatus() == 200);
	}

	@Test
	public void testCreateAccount() {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new GsonJsonProvider<Account>());

		WebClient webClient = WebClient.create(ADDRESS, providers);
		webClient.path("accounts/").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);

		// Message body - Account without firstName
		Account account = createSingleAccount();

		// Make a POST request to create an account
		Response response = webClient.post(account);

		// 201 Created must be returned
		assertTrue(response.getStatus() == 201);
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
	public void testCreateAccountWithoutDateOfBirth() {
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
	public static void afterClass() {
		server.stop();
		server.destroy();
	}

	private Account createSingleAccount() {
		Account account = new Account();
		account.setId(1L);
		account.setFirstName("John");
		account.setLastName("Dow");
		account.setEmail("john.doe@sun-fish.com");
		account.setDateOfBirth(new Date());
		return account;
	}

	private String readResponse(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr)) {
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		}
		return sb.toString();
	}
}
