package com.horntvedt.case2.camel.translator;


import com.horntvedt.case2.fagsystem.produkt.v1.ObjectFactory;
import com.horntvedt.case2.fagsystem.produkt.v1.ProduktRespons;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.UUID;

public class ProduktforespoerselForespoerselTranslator implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProduktforespoerselForespoerselTranslator.class);
    private static final String ACTION_PRODUKT = "registrerProdukt";

    @Override
    public void process(Exchange exchange) throws Exception {

        String action = exchange.getIn().getHeader("operationName", String.class);

        if (ACTION_PRODUKT.equals(action)) {

            LOGGER.info("Mottatt registrer produkt forespoersel");
            ProduktRespons produktRespons = new ProduktRespons();
            produktRespons.setAvtalenummer(UUID.randomUUID().toString());

            exchange.getOut().setBody(marshallProduktsvar(produktRespons));
        }
    }

    private String marshallProduktsvar(ProduktRespons produktRespons) throws Exception {

        ObjectFactory objectFactory = new ObjectFactory();
        JAXBContext context = JAXBContext.newInstance(ProduktRespons.class);
        Marshaller marshaller = context.createMarshaller();

        JAXBElement<ProduktRespons> je = objectFactory.createProduktRespons(produktRespons);

        StringWriter sw = new StringWriter();
        marshaller.marshal(je, sw);

        return sw.toString();

    }
}
