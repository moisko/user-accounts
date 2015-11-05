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
	private ServletConfig servletConfig;

	public PreInvokeInInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	public PreInvokeInInterceptor(ServletContext servletContext,
			ServletConfig servletConfig) {
		this();
		this.servletContext = servletContext;
		this.servletConfig = servletConfig;
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		message.put(AbstractHTTPDestination.HTTP_CONTEXT, servletContext);
		message.put(AbstractHTTPDestination.HTTP_CONFIG, servletConfig);
	}

}
