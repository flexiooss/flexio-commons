package it.pkg;

import io.flexio.services.support.api.Api;
import org.codingmatters.rest.api.Processor;
import com.fasterxml.jackson.core.JsonFactory;
import it.pkg.api.TotoApiHandlers;
import it.pkg.service.TotoApiProcessor;

public class TotoApiApi implements Api {

    private final String name = "toto-api";
    private TotoApiHandlers handlers;
    private TotoApiProcessor processor;

    public TotoApiApi(){
        this.handlers = new TotoApiHandlers.Builder()
                            .build();

        this.processor = new TotoApiProcessor(this.path(), new JsonFactory(), handlers);
    }

    @Override
    public Processor processor() {
        return this.processor;
    }

    @Override
    public String docResource() {
        return "toto-api.html";
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String version() {
        return "v2";
    }
}

