package accounts.exception;

import javax.persistence.RollbackException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class RollbackExceptionMapper implements
		ExceptionMapper<RollbackException> {

	@Override
	public Response toResponse(RollbackException e) {
		return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
				.build();
	}

}
