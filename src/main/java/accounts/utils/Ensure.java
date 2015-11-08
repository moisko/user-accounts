package accounts.utils;

public class Ensure {

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(String accountProperty, String accountValue) {
		if (accountValue.isEmpty()) {
			throw new IllegalArgumentException(accountProperty + " is empty");
		}
	}

	public static void wordsOnly(String accountProperty, String accountValue) {
		boolean matches = accountValue.matches("[a-zA-z]+");
		if (!matches) {
			throw new IllegalArgumentException(
					"Words only allowed for property " + accountProperty);
		}
	}

	public static void maxLengthOf(String accountProperty, String accountValue,
			int maxLength) {
		int sourceLength = accountValue.length();
		if (sourceLength >= maxLength) {
			throw new IllegalArgumentException("Max length of " + maxLength
					+ " reached for property " + accountProperty);
		}
	}
}
