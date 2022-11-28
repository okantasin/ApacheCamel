package com.java.microservices.camelmicroservicesa.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

    @Override
    public void configure() throws Exception {
        from("timer:first-timer")
                .transform().constant("My Constant message ").log("${body}")
                //.transform().constant("Time now is : "+ LocalDateTime.now())
                .bean(getCurrentTimeBean).log("${body}")
                .bean(simpleLoggingProcessingComponent).log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer"); //database

    }
}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is" + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {
    private final Logger logger = LoggerFactory
            .getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message) {
        logger.info("SimpleLoggingProcessingComponent {}", message);
    }
}

@Component
class SimpleLoggingProcessor implements Processor {
    private final Logger logger = LoggerFactory
            .getLogger(SimpleLoggingProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
    }
}

