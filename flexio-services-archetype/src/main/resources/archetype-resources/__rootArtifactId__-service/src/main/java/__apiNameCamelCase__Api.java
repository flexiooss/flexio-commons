package ${package};

import io.flexio.services.support.api.Api;
import org.codingmatters.rest.api.Processor;


public class ${apiNameCamelCase}Api implements Api {

    private final String path = "/${apiNameTiret}/v1";
    private ${apiNameCamelCase}Handlers handlers;
    private ${apiNameCamelCase}Processor processor;

    public ${apiNameCamelCase}Api(){
        this.handlers = new ${apiNameCamelCase}Handlers
        .build();

        this.processor = new ${apiNameCamelCase}Processor(path, new JsonFactory(), handlers);
    }

    @Override
    public Processor processor() {
        return this.processor;
    }

    @Override
    public String docResource() {
        return "${apiNameTiret}.html";
    }

    @Override
    public String path() {
        return this.path;
    }

}

