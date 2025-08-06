package com.soap.api.config;

import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

public class SoapActionInterceptor implements EndpointInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        // Use TransportContext to get HttpServletRequest
        TransportContext transportContext = TransportContextHolder.getTransportContext();
        String soapAction = null;

        if (transportContext != null && transportContext.getConnection() instanceof HttpServletConnection httpConn) {
            soapAction = httpConn.getHttpServletRequest().getHeader("SOAPAction");
        }

        if (soapAction == null || soapAction.trim().isEmpty()) {
            throw new RuntimeException("SOAPAction header is required");
        }

        // Optionally, enforce the exact value (adjust as needed):
        String expectedAction = "\"http://anantsoap.com/AddRequest\"";
        if (!expectedAction.equals(soapAction)) {
            throw new RuntimeException("Invalid SOAPAction header value: " + soapAction);
        }

        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) { return true; }
    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) { return true; }
    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) {}
}
