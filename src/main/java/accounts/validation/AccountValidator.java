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
			String firstName = String.valueOf(accountValue);
			Ensure.wordsOnly(Account.FIRST_NAME, firstName);
			Ensure.maxLengthOf(accountProperty, firstName, 50);
		}
			break;
		case Account.LAST_NAME: {
			Ensure.notNull(accountValue, "Missing property [lastName]");
			String lastName = String.valueOf(accountValue);
			Ensure.wordsOnly(Account.LAST_NAME, lastName);
			Ensure.maxLengthOf(accountProperty, lastName, 50);
		}
			break;
		case Account.EMAIL: {
			Ensure.notNull(accountValue, "Missing property [email]");
			String email = String.valueOf(accountValue);
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
			if (!(accountValue instanceof Date)) {
				throw new IllegalArgumentException("Date of birth ["
						+ (String) accountValue + "] is not valid");
			}
		}
			break;
		default:
			throw new IllegalArgumentException("Account property ["
					+ accountProperty + "] does not exist");
		}
	}

}
