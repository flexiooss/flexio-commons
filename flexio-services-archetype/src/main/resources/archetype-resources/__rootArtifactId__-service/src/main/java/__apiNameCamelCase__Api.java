package ${package};

import io.flexio.services.support.api.Api;
import org.codingmatters.rest.api.Processor;
import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.services.tabular.api.${apiNameCamelCase}Handlers;
import io.flexio.services.tabular.service.${apiNameCamelCase}Processor;

public class ${apiNameCamelCase}Api implements Api {

    private final String name = "${apiNameTiret}";
    private ${apiNameCamelCase}Handlers handlers;
    private ${apiNameCamelCase}Processor processor;

    public ${apiNameCamelCase}Api(){
        this.handlers = new ${apiNameCamelCase}Handlers.Builder()
                            .build();

        this.processor = new ${apiNameCamelCase}Processor(this.path(), new JsonFactory(), handlers);
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
        return "/" + this.name;
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

