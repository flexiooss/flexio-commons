package ${package};

import io.flexio.services.support.api.Api;
import io.flexio.services.support.api.ApiService;
import io.flexio.services.support.args.ArgumentsException;
import org.codingmatters.poom.services.logging.CategorizedLogger;
import org.codingmatters.poom.services.support.Env;

public class ${apiNameCamelCase}Services {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(FlexioDataProtectionServices.class);

    public static void main(String[] args){
        ApiService.main(${apiNameCamelCase}Services::createService, log);
    }

    public static ApiService createService() throws ArgumentsException {
        Api api = new ${apiNameCamelCase}Api();
        return new ApiService(
            Env.mandatory(Env.SERVICE_HOST).asString(),
            Env.mandatory(Env.SERVICE_PORT).asInteger(),
            ()->{},
            api
        );
    }
}

