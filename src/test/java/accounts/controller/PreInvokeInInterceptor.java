package accounts.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

public class PreInvokeInInterceptor extends AbstractPhaseInterceptor<Message> {

	private ServletContext servletContext;

	public PreInvokeInInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	public PreInvokeInInterceptor(ServletContext servletContext) {
		this();
		this.servletContext = servletContext;
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		message.put(AbstractHTTPDestination.HTTP_CONTEXT, servletContext);
	}

}
