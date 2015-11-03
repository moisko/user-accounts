package accounts.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.google.gson.JsonParseException;

public class JsonParseExceptionMapper implements
		ExceptionMapper<JsonParseException> {

	@Override
	public Response toResponse(JsonParseException e) {
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(e.getMessage()).build();
	}

}
