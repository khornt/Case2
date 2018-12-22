package com.horntvedt.case2.camel.translator;

import com.horntvedt.case2.fagsystem.kunde.v1.KundeRespons;
import com.horntvedt.case2.fagsystem.kunde.v1.ObjectFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.UUID;

public class KundeforespoerselForespoerselTranslator implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(KundeforespoerselForespoerselTranslator.class);
    private static final String ACTION_KUNDE = "registrerKunde";

    //todo: Splitt opp i to steg og legg inn lagring til base

    @Override
    public void process(Exchange exchange) throws Exception {

        String action = exchange.getIn().getHeader("operationName", String.class);

        if (ACTION_KUNDE.equals(action)) {
            LOGGER.info("Mottatt registrer kunde forespoersel");

            KundeRespons kundeRespons = new KundeRespons();

            kundeRespons.setKundeId(UUID.randomUUID().toString());
            exchange.getOut().setBody(marshallKundesvar(kundeRespons));

        }
    }

    private String marshallKundesvar(KundeRespons kundeRespons) throws Exception {

        ObjectFactory objectFactory = new ObjectFactory();
        JAXBContext context = JAXBContext.newInstance(KundeRespons.class);
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<KundeRespons> je = objectFactory.createKundeRespons(kundeRespons);

        StringWriter sw = new StringWriter();
        marshaller.marshal(je, sw);

        return sw.toString();

    }
}
