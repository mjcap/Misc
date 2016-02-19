package com.capbpm;

import java.io.IOException;

import javax.xml.transform.Result;

import org.springframework.oxm.XmlMappingException;

public interface Marshaller {

    /**
     * Marshals the object graph with the given root into the provided Result.
     */
    void marshal(Object graph, Result result) throws XmlMappingException, IOException;

}
