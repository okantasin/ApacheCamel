package com.java.microservices.camelmicroservicesa.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSenderRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //timer
        from("timer:active-mq-timer?period=1000")
                .transform().constant("My message for Active mq")
                .log("${body}")
                .to("activemq:my-activemq-queue");
        //queue
        //
    }
}
