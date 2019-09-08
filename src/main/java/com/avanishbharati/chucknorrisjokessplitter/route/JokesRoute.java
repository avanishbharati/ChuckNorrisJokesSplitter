package com.avanishbharati.chucknorrisjokessplitter.route;


import com.avanishbharati.chucknorrisjokessplitter.model.CharacterNameRequest;
import com.avanishbharati.chucknorrisjokessplitter.model.JokeResponse;
import com.avanishbharati.chucknorrisjokessplitter.processor.JokesResponseProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Component
public class JokesRoute extends RouteBuilder {

    private static final String LOG_NAME = "JokesRoute";
    private static final Logger LOGGER = LoggerFactory.getLogger(JokesResponseProcessor.class);

    @Autowired
    private JokesResponseProcessor responseProcessor;

    @Override
    public void configure() {

        DataFormat requestFormat = new JacksonDataFormat(CharacterNameRequest.class);
        DataFormat responseFormat = new JacksonDataFormat(JokeResponse.class);

        onException(Exception.class)
                .convertBodyTo(String.class)
                .log(LoggingLevel.ERROR, LOG_NAME, "Error : ${body}")
                .handled(true);


        from("direct:getJokes").description("REST Service Call").id("api-route")
           .log(LoggingLevel.INFO, LOGGER.getName(), "FirstName : ${body.getFirstName()}")
           .log(LoggingLevel.INFO, LOG_NAME, "LastName : ${body.getLastName()}")
                .process(exchange -> { exchange.setProperty("RequestBodyAsProperty", exchange.getIn().getBody(CharacterNameRequest.class));
                    LOGGER.info("RequestBodyAsProperty : {}", exchange.getProperty("RequestBodyAsProperty"));
                })
                .marshal(requestFormat)
                .process(exchange -> {
                    CamelContext context = exchange.getContext();
                    ProducerTemplate producerTemplate = context.createProducerTemplate();

                    CharacterNameRequest requestBody = exchange.getProperty("RequestBodyAsProperty", CharacterNameRequest.class);

                    Map<String, Object> headers = new HashMap<>();
                    headers.put(Exchange.HTTP_METHOD, HttpMethods.GET);
                    headers.put(Exchange.HTTP_QUERY, "firstName="+ requestBody.getFirstName() + "&lastName="+ requestBody.getLastName());

                    //make async calls
                    Future<String> firstJoke = producerTemplate.asyncRequestBodyAndHeaders("direct:jokeApiCall", null, headers, String.class);
                    Future<String> secondJoke = producerTemplate.asyncRequestBodyAndHeaders("direct:jokeApiCall", null, headers, String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    ArrayNode arrayNode = mapper.createArrayNode();
                    JsonNode actualJokeObj1 = mapper.readTree(firstJoke.get());
                    JsonNode actualJokeObj2 = mapper.readTree(secondJoke.get());
                    arrayNode.add(actualJokeObj1);
                    arrayNode.add(actualJokeObj2);
                    exchange.getIn().setBody(arrayNode.toString());

            })
           .process(responseProcessor);


        from("direct:jokeApiCall").routeId("api-call")
                .to("http://api.icndb.com/jokes/random?bridgeEndpoint=true");

        from("direct:syncCall").routeId("api-sync-call")
                .setHeader(Exchange.HTTP_QUERY).simple("firstName=${body.getFirstName}&lastName=${body.getLastName}")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
                .to("http://api.icndb.com/jokes/random?bridgeEndpoint=true")
                .log(LoggingLevel.INFO, LOG_NAME, "secondJokeCall response : ${body}");

    }

}
