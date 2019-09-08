package com.avanishbharati.chucknorrisjokessplitter.config;


import org.apache.camel.CamelContext;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    @Bean
    public CamelContextConfiguration contextConfiguration(){

        return new CamelContextConfiguration(){

            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                camelContext.setUseMDCLogging(true);
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                //nothing
            }
        };
    }

}
