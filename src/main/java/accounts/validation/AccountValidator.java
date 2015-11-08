package accounts.validation;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		validateAccountProperty(Account.DATE_OF_BIRTH, dateOfBirth);
	}

	public static void validateAccountProperty(String accountProperty,
			Object accountValue) {
		switch (accountProperty) {
		case Account.FIRST_NAME: {
			Ensure.notNull(accountValue, "Missing property [firstName]");
			Ensure.wordsOnly(Account.FIRST_NAME, (String) accountValue);
			Ensure.maxLengthOf(accountProperty, (String) accountValue, 50);
		}
			break;
		case Account.LAST_NAME: {
			Ensure.notNull(accountValue, "Missing property [lastName]");
			Ensure.wordsOnly(Account.LAST_NAME, (String) accountValue);
			Ensure.maxLengthOf(accountProperty, (String) accountValue, 50);
		}
			break;
		case Account.EMAIL: {
			String email = (String) accountValue;
			Ensure.notNull(email, "Missing property [email]");
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(email);
			boolean valid = matcher.matches();
			if (!valid) {
				throw new IllegalArgumentException("Email [" + email
						+ "] is not valid");
			}
		}
			break;
		case Account.DATE_OF_BIRTH: {
			Ensure.notNull(accountValue, "Missing property [dateOfBirth]");
		}
			break;
		default:
			throw new IllegalArgumentException("Account property ["
					+ accountProperty + "] does not exist");
		}
	}

}
