package accounts.exception;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

@Produces(MediaType.APPLICATION_JSON)
public class NumberFormatExceptionMapper implements
		ExceptionMapper<NumberFormatException> {

	@Override
	public Response toResponse(NumberFormatException e) {
		return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
	}

}
