# Parallel processing response, service integration, using Spring Boot, Apache Camel and WebTestClient

This project demonstrates how to merge results after asynchronous calls to multiple services using Apache Camel splitter technique.
As a mock service, we will be calling the Internet Chuck Norris Database twice, in parallel and merging the jokes data responses into one result.


## Swagger 
http://localhost:8080/chucknorrisjokessplitter/api/v1/api-doc
Import into PostMan for further information

## Service endpoint
Sample request:
```json
{"firstName":"Tom","lastName":"Hanks"}
```
POST to http://localhost:8080/chucknorrisjokessplitter/api/v1/chucknoriss/jokes

Sample response:
[{"type":"success","value":{"id":438,"joke":"Tom Hanks likes his ice like he likes his skulls: crushed.","categories":[]}},{"type":"success","value":{"id":132,"joke":"An anagram for Walker Texas Ranger is KARATE WRANGLER SEX. I don't know what that is, but it sounds AWESOME.","categories":[]}}]

## Test
WebTestClient is used to test the API

### Reference Documentation
For further reference, please consider the following sections:

* [The Technique of Data Flow Diagramming](https://spot.colorado.edu/~kozar/DFDtechnique.html)
* [Sequencer](https://www.enterpriseintegrationpatterns.com/patterns/messaging/Sequencer.html)
* [Aggregator](https://www.enterpriseintegrationpatterns.com/patterns/messaging/Aggregator.html)
* [pache-camel-how-to-send-two-http-requests-in-parallel-and-wait-for-the-respons](https://stackoverflow.com/questions/37388376/apache-camel-how-to-send-two-http-requests-in-parallel-and-wait-for-the-respons/37411289#37411289)
* [Apache Camel with Spring Boot](https://www.baeldung.com/apache-camel-spring-boot)
* [Chuck Noriss Jokes API](http://www.icndb.com/api/)
* [Maarteen Smeets](https://technology.amis.nl/2019/08/09/apache-camel-and-spring-boot-calling-multiple-services-in-parallel-and-merging-results/)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.1.7.RELEASE/maven-plugin/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#using-boot-devtools)
* [Spring Integration](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-integration)

### Guides
The following guides illustrate how to use some features concretely:

* [Using Apache Camel with Spring Boot](https://camel.apache.org/spring-boot)
* [Integrating Data](https://spring.io/guides/gs/integration/)


