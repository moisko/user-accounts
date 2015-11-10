package accounts.utils;

public class AccountUtils {

	public static Long getAccountId(String id) {
		Ensure.containsString(id, "_");
		String[] tokens = id.split("_");
		return Long.parseLong(tokens[1]);
	}

	public static String getAccountProperty(String id) {
		Ensure.containsString(id, "_");
		String[] tokens = id.split("_");
		String accountProperty = tokens[0];
		return accountProperty;
	}
}
