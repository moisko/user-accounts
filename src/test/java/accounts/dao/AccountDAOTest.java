package accounts.dao;

import static org.mockito.Mockito.when;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import accounts.model.Account;

@RunWith(MockitoJUnitRunner.class)
public class AccountDAOTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	EntityManagerFactory emf;

	@Mock
	EntityManager em;

	@Mock
	Query query;

	private AccountDAO accountDAO = new AccountDAO(null);

	@Test
	public void testDeleteUnexistingAccount() {
		when(query.getSingleResult()).thenReturn(null);
		when(em.createNamedQuery("getAccountById")).thenReturn(query);
		when(emf.createEntityManager()).thenReturn(em);
		accountDAO = new AccountDAO(emf);
		expectedException.expect(NoResultException.class);
		expectedException.expectMessage("Account with id [1] not found");
		accountDAO.deleteAccount(1L);
	}

	@Test
	public void testAddAccountWithMissingFirstName() throws Exception {
		Account account = createAccountWithMissingFirstName();
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Missing property [firstName]");
		accountDAO.createAccount(account);
	}

	@Test
	public void testAddAccountWithMissingLastName() throws Exception {
		Account account = createAccountWithMissingLastName();
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Missing property [lastName]");
		accountDAO.createAccount(account);
	}

	@Test
	public void testAddAccountWithMissingEmail() throws Exception {
		Account account = createAccountWithMissingEmail();
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Missing property [email]");
		accountDAO.createAccount(account);
	}

	@Test
	public void testCreateAccountWithMissingDateOfBirth() throws Exception {
		Account account = createAccountWithMissingDateOfBirth();
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Missing property [dateOfBirth]");
		accountDAO.createAccount(account);
	}

	@Test
	public void testCreateAccountWithInvalidEmail() throws Exception {
		Account account = createAccountWithInvalidEmail();
		expectedException.expect(IllegalArgumentException.class);
		expectedException
				.expectMessage("Email [john.doe@sun-fish] is not valid");
		accountDAO.createAccount(account);
	}

	private Account createAccountWithMissingFirstName() {
		Account account = new Account();
		account.setLastName("Doe");
		account.setEmail("john.doe@sun-fish.com");
		account.setDateOfBirth(new Date());
		return account;
	}

	private Account createAccountWithMissingLastName() {
		Account account = new Account();
		account.setFirstName("John");
		account.setEmail("john.doe@sun-fish.com");
		account.setDateOfBirth(new Date());
		return account;
	}

	private Account createAccountWithMissingEmail() {
		Account account = new Account();
		account.setFirstName("John");
		account.setLastName("Doe");
		account.setDateOfBirth(new Date());
		return account;
	}

	private Account createAccountWithMissingDateOfBirth() {
		Account account = new Account();
		account.setFirstName("John");
		account.setLastName("Doe");
		account.setEmail("john.doe@sun-fish.com");
		return account;
	}

	private Account createAccountWithInvalidEmail() {
		Account account = new Account();
		account.setFirstName("John");
		account.setLastName("Doe");
		account.setEmail("john.doe@sun-fish");
		account.setDateOfBirth(new Date());
		return account;
	}

}
