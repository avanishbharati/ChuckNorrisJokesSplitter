package com.avanishbharati.chucknorrisjokessplitter.route;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.avanishbharati.chucknorrisjokessplitter.model.CharacterNameRequest;
import com.avanishbharati.chucknorrisjokessplitter.model.JokeResponse;

@Component
public class RestAPIRoute extends RouteBuilder {

    private static final String LOG_NAME = "RestAPIRoute";

    @Value("${api.path}")
    String basePath;

    @Value("${server.port}")
    String serverPort;

    @Override
    public void configure() throws Exception {

        CamelContext context = new DefaultCamelContext();

        //Common Rest config
        restConfiguration()
            .component("servlet")
            .contextPath("chucknorrisjokessplitter").host("localhost").port(serverPort)
            .enableCORS(true)
            .bindingMode(RestBindingMode.json)
            //Enable swagger endpoint.
            .apiContextPath("/api-doc") 
            .apiContextRouteId("swagger")
            //Swagger properties
            .apiProperty("cors","true")
            .apiProperty("api.title", "Chuck Noriss Jokes Splitter API")
            .apiProperty("schemes","http")
            .apiProperty("api.version", "v1");

        rest("/chucknoriss/").id("chuck-noriss-jokes-api").description("Chuck Noriss Jokes Splitter API")
            .post("/jokes").description("Get Random Chuck Noriss Splitter Jokes")
            .consumes("application/json")
            .produces("application/json")
            .type(CharacterNameRequest.class)
            .bindingMode(RestBindingMode.json)
            .outType(JokeResponse.class)
            .to("direct:getJokes");
    }
}
