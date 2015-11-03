package accounts.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class IllegalArgumentExceptionMapper implements
		ExceptionMapper<IllegalArgumentException> {

	@Override
	public Response toResponse(IllegalArgumentException e) {
		return Response.status(Response.Status.NOT_FOUND)
				.entity(e.getMessage()).build();
	}
}
