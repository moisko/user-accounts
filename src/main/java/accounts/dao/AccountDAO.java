package accounts.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import accounts.model.Account;
import accounts.utils.AccountUtils;
import accounts.validation.AccountValidator;

public class AccountDAO {

	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm");

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

	public Account getAccount(Long id) {
		EntityManager em = emf.createEntityManager();
		try {
			return getAccountFromDb(em, id);
		} finally {
			em.close();
		}
	}

	public void createAccount(Account account) {
		AccountValidator.validate(account);
		EntityManager em = emf.createEntityManager();
		try {
			persistAccountInDb(em, account);
		} finally {
			em.close();
		}
	}

	public void updateAccount(String id, Object value) {
		Long accountId = AccountUtils.getAccountId(id);
		String accountProperty = AccountUtils.getAccountProperty(id);
		AccountValidator.validateAccountProperty(accountProperty, value);
		EntityManager em = emf.createEntityManager();
		try {
			Account account = getAccountFromDb(em, accountId);
			if (account != null) {
				updateAccountPropertyInDb(em, account, accountProperty, value);
			} else {
				throw new NoResultException("Account with id [" + id
						+ "] not found");
			}
		} finally {
			em.close();
		}
	}

	public void deleteAccount(Long id) {
		EntityManager em = emf.createEntityManager();
		try {
			Account account = getAccountFromDb(em, id);
			if (account != null) {
				deleteAccountFromDb(em, account);
			} else {
				throw new NoResultException("Account with id [" + id
						+ "] not found");
			}
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

	private Account getAccountFromDb(EntityManager em, Long id) {
		Query q = em.createNamedQuery("getAccountById");
		q.setParameter("id", id);
		Account account = (Account) q.getSingleResult();
		return account;
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

	private void updateAccountPropertyInDb(EntityManager em, Account account,
			String accountProperty, Object accountValue) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			switch (accountProperty) {
			case Account.FIRST_NAME:
				account.setFirstName((String) accountValue);
				break;
			case Account.LAST_NAME:
				account.setLastName((String) accountValue);
				break;
			case Account.EMAIL:
				account.setEmail((String) accountValue);
				break;
			case Account.DATE_OF_BIRTH: {
				account.setDateOfBirth((Date) accountValue);
			}
				break;
			default:
				throw new IllegalArgumentException("Account property ["
						+ accountProperty + "] does not exist");
			}
			et.commit();
		} finally {
			if (et.isActive()) {
				et.rollback();
			}
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
