package com.horntvedt.case2.camel.translator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MottaForespoersel implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MottaForespoersel.class);
    private static final String ACTION_KUNDE = "registrerKunde";
    private static final String ACTION_PRODUKT = "registrerProdukt";

    @Override
    public void process(Exchange exchange) throws Exception {

        String action = exchange.getIn().getHeader("operationName", String.class);

        if (ACTION_KUNDE.equals(action)) {
            LOGGER.info("Mottatt registrer kunde forespoersel");
        } else if (ACTION_PRODUKT.equals(action)) {
            LOGGER.info("Mottatt registrer produkt forespoersel");
        }
    }
}
