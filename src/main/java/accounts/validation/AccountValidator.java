package accounts.validation;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import accounts.dao.AccountDAO;
import accounts.model.Account;
import accounts.utils.Ensure;

public class AccountValidator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static void validate(Account account) {
		String firstName = account.getFirstName();
		validateAccountProperty(Account.FIRST_NAME, firstName);
		Ensure.wordsOnly(Account.FIRST_NAME, firstName);
		Ensure.maxLengthOf(Account.FIRST_NAME, firstName, 50);

		String lastName = account.getLastName();
		validateAccountProperty(Account.LAST_NAME, lastName);
		Ensure.wordsOnly(Account.LAST_NAME, lastName);
		Ensure.maxLengthOf(Account.LAST_NAME, lastName, 50);

		String email = account.getEmail();
		validateAccountProperty(Account.EMAIL, email);

		Date dateOfBirth = account.getDateOfBirth();
		Ensure.notNull(dateOfBirth, "Missing property [dateOfBirth]");
	}

	public static void validateAccountProperty(String accountProperty,
			String accountValue) {
		switch (accountProperty) {
		case Account.FIRST_NAME: {
			Ensure.notNull(accountValue, "Missing property [firstName]");
			Ensure.wordsOnly(Account.FIRST_NAME, accountValue);
			Ensure.maxLengthOf(accountProperty, accountValue, 50);
		}
			break;
		case Account.LAST_NAME: {
			Ensure.notNull(accountValue, "Missing property [lastName]");
			Ensure.wordsOnly(Account.LAST_NAME, accountValue);
			Ensure.maxLengthOf(accountProperty, accountValue, 50);
		}
			break;
		case Account.EMAIL: {
			Ensure.notNull(accountValue, "Missing property [email]");
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(accountValue);
			boolean valid = matcher.matches();
			if (!valid) {
				throw new IllegalArgumentException("Email [" + accountValue
						+ "] is not valid");
			}
		}
			break;
		case Account.DATE_OF_BIRTH: {
			Ensure.notNull(accountValue, "Missing property [dateOfBirth]");
			try {
				AccountDAO.FORMATTER.parse(accountValue);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Date of birth ["
						+ accountValue + "] is not valid");
			}
		}
			break;
		default:
			throw new IllegalArgumentException("Account property ["
					+ accountProperty + "] does not exist");
		}
	}

}
