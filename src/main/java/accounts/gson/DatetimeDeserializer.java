package accounts.gson;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DatetimeDeserializer implements JsonDeserializer<Date> {
	@Override
	public Date deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		long datetime = json.getAsLong();
		Date date = new Date(datetime);
		return date;
	}
}
