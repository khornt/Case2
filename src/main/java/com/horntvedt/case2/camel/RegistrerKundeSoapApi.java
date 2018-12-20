package com.horntvedt.case2.camel;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RegistrerKundeSoapApi extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrerKundeSoapApi.class);

    private static final String lagProviderEndpoint() {

        return "cxf:/registrerKunde"
            + "?wsdlURL=wsdl/Fagsystem.wsdl"
            + "&address=/fagsystem/v1"
            + "&dataFormat=PAYLOAD"
            + "&serviceName={urn:com:horntvedt:case2:bestill:v1}RegistrerKunde"
            + "&skipFaultLogging=false"
            + "&allowStreaming=false";
    }

    public void configure() throws Exception {

        from(lagProviderEndpoint()).routeId("Registrer Kunde")
            .log(LoggingLevel.INFO, LOGGER, "Mottatt registrer kunde forespørsel")
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    String s = exchange.getIn().getBody(String.class);
                }
            })
            .end();
    }

}
