package com.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soap.api.model.AddRequest;
import com.soap.api.model.AddResponse;
import com.soap.api.service.AdditionService;

@Endpoint
public class AddEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/soapaddition";

    @Autowired
    private AdditionService additionService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddRequest")
    @ResponsePayload
    public AddResponse add(@RequestPayload AddRequest request) {
        AddResponse response = new AddResponse();
        response.setResult(additionService.add(request.getNumber1(), request.getNumber2()));
        return response;
    }
}
