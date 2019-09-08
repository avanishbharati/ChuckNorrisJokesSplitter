package com.avanishbharati.chucknorrisjokessplitter.camel;


import com.avanishbharati.chucknorrisjokessplitter.model.CharacterNameRequest;
import com.avanishbharati.chucknorrisjokessplitter.model.JokeResponse;
import org.apache.camel.CamelContext;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CamelTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelTest.class);

    @LocalServerPort
    private String port;

    @Autowired
    protected CamelContext context;

    @Autowired
    private WebTestClient  webTestClient;

    private String baseURL;

    @Before
    public void setUp(){
        baseURL = "/chucknorrisjokessplitter/api/v1";
    }

    @Test
    public void Test1SplitterRequestResponse(){

        CharacterNameRequest characterNameRequest = new CharacterNameRequest();
        characterNameRequest.setFirstName("Tom");
        characterNameRequest.setLastName("Hanks");

        webTestClient.post().uri(baseURL + "/chucknoriss/jokes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(characterNameRequest),CharacterNameRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(JokeResponse.class)
                .consumeWith(result -> {
                    LOGGER.info("TEST REQUEST/RESPONSE: {}", result);
                });
    }


}



