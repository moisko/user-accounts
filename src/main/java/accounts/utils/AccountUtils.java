package accounts.utils;

public class AccountUtils {

	public static Long getAccountId(String id) {
		String[] tokens = id.split("_");
		return Long.parseLong(tokens[1]);
	}

	public static String getAccountProperty(String id) {
		String[] tokens = id.split("_");
		return tokens[0];
	}
}
