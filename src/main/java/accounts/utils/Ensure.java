package accounts.utils;

public class Ensure {

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void wordsOnly(String source) {
		boolean matches = source.matches("[a-zA-z]+");
		if (!matches) {
			throw new IllegalArgumentException("Words only allowed");
		}
	}

	public static void maxLengthOf(String source, int maxLength) {
		int sourceLength = source.length();
		if (sourceLength >= maxLength) {
			throw new IllegalArgumentException("Max length of " + maxLength
					+ " reached");
		}
	}
}
