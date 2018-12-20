package com.horntvedt.case2.camel;

import com.horntvedt.case2.camel.translator.MottaForespoersel;
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

    private static final String lagProviderEndpointKunde() {

        return "cxf:/fagsystem"
            + "?wsdlURL=wsdl/Fagsystem.wsdl"
            + "&address=/fagsystem/v1"
            + "&dataFormat=PAYLOAD"
            + "&serviceName={urn:com:horntvedt:case2:bestill:v1}fagsystem"
            + "&skipFaultLogging=false"
            + "&allowStreaming=false";
    }


    public void configure() throws Exception {

        from(lagProviderEndpointKunde()).routeId("Registrer Kunde")
                .log(LoggingLevel.INFO, LOGGER, "Mottatt forespørsel")
                .process(new MottaForespoersel())
                .end();
    }
}
