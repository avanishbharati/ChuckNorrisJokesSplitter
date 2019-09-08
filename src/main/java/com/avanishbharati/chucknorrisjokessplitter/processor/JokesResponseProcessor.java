package com.avanishbharati.chucknorrisjokessplitter.processor;

import com.avanishbharati.chucknorrisjokessplitter.model.JokeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JokesResponseProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JokesResponseProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.info("JokesResponseProcessor::process() - START");

        String body = exchange.getIn().getBody(String.class);

        String requestBody = exchange.getProperty("RequestBodyAsProperty", String.class);

        LOGGER.info("Request was : {}", requestBody);
        LOGGER.info("Response from exchange : {}", body);

        ObjectMapper mapper = new ObjectMapper();
        List<JokeResponse> jokesResponse = mapper.readValue(body, new TypeReference<List<JokeResponse>>(){});

        exchange.getIn().setBody(jokesResponse);
        LOGGER.info("JokesResponseProcessor::process() - END");
    }
}
