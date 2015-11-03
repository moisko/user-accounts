package accounts.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import accounts.model.Account;

public class AccountDAO {

	private static final String PATTERN = "dd/MM/yyyy HH:mm";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
			PATTERN);

	private final EntityManagerFactory emf;

	public AccountDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public List<Account> getAccounts() {
		EntityManager em = emf.createEntityManager();
		try {
			return getAccountsFromDb(em);
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Account> getAccountsFromDb(EntityManager em) {
		Query q = em.createNamedQuery("getAllAccounts");
		List<Account> allAccounts = (List<Account>) q.getResultList();
		return allAccounts;
	}

	public Account getAccount(Long id) {
		EntityManager em = emf.createEntityManager();
		try {
			return getAccountFromDb(em, id);
		} finally {
			em.close();
		}
	}

	private Account getAccountFromDb(EntityManager em, Long id) {
		Query q = em.createNamedQuery("getAccountById");
		q.setParameter("id", id);
		Account account = (Account) q.getSingleResult();
		return account;
	}

	public void createAccount(Account account) {
		EntityManager em = emf.createEntityManager();
		try {
			persistAccountInDb(em, account);
		} finally {
			em.close();
		}
	}

	private void persistAccountInDb(EntityManager em, Account account) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(account);
			et.commit();
		} finally {
			if (et.isActive()) {
				et.rollback();
			}
		}
	}

	public void updateAccount(Long id, String accountProperty,
			String accountValue) throws ParseException {
		EntityManager em = emf.createEntityManager();
		try {
			Account account = getAccountFromDb(em, id);
			if (account != null) {
				updateAccountPropertyInDb(em, account, accountProperty,
						accountValue);
			}
		} finally {
			em.close();
		}
	}

	private void updateAccountPropertyInDb(EntityManager em, Account account,
			String accountProperty, String accountValue) throws ParseException {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			switch (accountProperty) {
			case Account.FIRST_NAME:
				account.setFirstName(accountValue);
				break;
			case Account.LAST_NAME:
				account.setLastName(accountValue);
				break;
			case Account.EMAIL:
				account.setEmail(accountProperty);
				break;
			case Account.DATE_OF_BIRTH: {
				Date dateOfBirth = FORMATTER.parse(accountValue);
				account.setDateOfBirth(dateOfBirth);
			}
				break;
			default:
				// TODO
				break;
			}
			et.commit();
		} finally {
			if (et.isActive()) {
				et.rollback();
			}
		}
	}

	public void deleteAccount(Long id) {
		EntityManager em = emf.createEntityManager();
		try {
			Account account = getAccountFromDb(em, id);
			if (account != null) {
				deleteAccountFromDb(em, account);
			}
		} finally {
			em.close();
		}
	}

	private void deleteAccountFromDb(EntityManager em, Account account) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.remove(account);
			et.commit();
		} finally {
			if (et.isActive()) {
				et.rollback();
			}
		}
	}

}
