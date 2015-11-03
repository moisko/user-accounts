package accounts.exception;

import java.text.ParseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class ParseExceptionMapper implements ExceptionMapper<ParseException> {

	@Override
	public Response toResponse(ParseException e) {
		return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
				.build();
	}

}
