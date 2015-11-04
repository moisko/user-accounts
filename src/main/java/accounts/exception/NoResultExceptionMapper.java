package accounts.exception;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class NoResultExceptionMapper implements
		ExceptionMapper<NoResultException> {

	@Override
	public Response toResponse(NoResultException e) {
		return Response.status(Status.NOT_FOUND).build();
	}

}
