package accounts.exception;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Produces(MediaType.APPLICATION_JSON)
public class NumberFormatExceptionMapper implements
		ExceptionMapper<NumberFormatException> {

	@Override
	public Response toResponse(NumberFormatException e) {
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(e.getMessage()).build();
	}

}
