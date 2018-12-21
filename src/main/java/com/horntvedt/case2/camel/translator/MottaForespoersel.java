package com.horntvedt.case2.camel.translator;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horntvedt.case2.fagsystem.kunde.v1.KundeRespons;
import com.horntvedt.case2.fagsystem.kunde.v1.ObjectFactory;
import com.horntvedt.case2.fagsystem.produkt.v1.ProduktRespons;

public class MottaForespoersel implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MottaForespoersel.class);
    private static final String ACTION_KUNDE = "registrerKunde";
    private static final String ACTION_PRODUKT = "registrerProdukt";

    @Override
    public void process(Exchange exchange) throws Exception {

        String action = exchange.getIn().getHeader("operationName", String.class);

        if (ACTION_KUNDE.equals(action)) {
            LOGGER.info("Mottatt registrer kunde forespoersel");

            KundeRespons kundeRespons = new KundeRespons();
            kundeRespons.setSvar("Ok");


            JAXBContext context = JAXBContext.newInstance(KundeRespons.class);
            Marshaller m = context.createMarshaller();

            ObjectFactory objectFactory = new ObjectFactory();
            JAXBElement<KundeRespons> je = objectFactory.createKundeRespons(kundeRespons);

            StringWriter sw = new StringWriter();
            m.marshal(je, sw);

            exchange.getOut().setBody(sw.toString());


        } else if (ACTION_PRODUKT.equals(action)) {
            LOGGER.info("Mottatt registrer produkt forespoersel");

            ProduktRespons produktRespons = new ProduktRespons();
            produktRespons.setSvar("ok");
            exchange.getOut().setBody(produktRespons);

        }
    }
}
