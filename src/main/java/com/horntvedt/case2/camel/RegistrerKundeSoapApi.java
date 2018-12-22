package com.horntvedt.case2.camel;

import com.horntvedt.case2.camel.translator.KundeforespoerselForespoerselTranslator;
import com.horntvedt.case2.camel.translator.ProduktforespoerselForespoerselTranslator;
import com.horntvedt.case2.fagsystem.kunde.v1.KundeForespoersel;
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
    private static final String ACTION_KUNDE = "registrerKunde";
    private static final String ACTION_PRODUKT = "registrerProdukt";

    private static final String lagProviderEndpointKunde() {

        return "cxf:/fagsystem"
            + "?wsdlURL=wsdl/Fagsystem.wsdl"
            + "&address=/fagsystem/v1"
            + "&dataFormat=PAYLOAD"
            + "&serviceName={urn:com:horntvedt:case2:fagsystem:v1}fagsystem"
            + "&skipFaultLogging=false"
            + "&allowStreaming=false";
    }


    public void configure() throws Exception {


        onException(Exception.class)
                .handled(false)
                .log(LoggingLevel.INFO, LOGGER, "Dette gikk ikke...");


        from(lagProviderEndpointKunde()).routeId("Registrer Kunde")
                .log(LoggingLevel.INFO, LOGGER, "Mottatt forespørsel")

                .choice()
                .when().simple("${header.operationName} == 'registrerKunde'")
                    .log(LoggingLevel.INFO, LOGGER, "kunde")
                    .to("validator:xsd/Kunde.xsd")
                    .process(new KundeforespoerselForespoerselTranslator())
                    //.bean(lagreKundeInfoIenDatabasebean)
                    //prøv h2 her
                .when().simple("${header.operationName} == 'registrerProdukt'")
                    .log(LoggingLevel.INFO, LOGGER, "produkt")
                    .to("validator:xsd/Produkt.xsd")
                    .process(new ProduktforespoerselForespoerselTranslator())
                    //.bean(lagreProduktKjoepInfoIEnDatabaseBean)
                    //prøv h2 her
                .otherwise()
                    .log(LoggingLevel.WARN, LOGGER, "Ukjent operation, kunne ikke behandle")
                .end();
    }
}
