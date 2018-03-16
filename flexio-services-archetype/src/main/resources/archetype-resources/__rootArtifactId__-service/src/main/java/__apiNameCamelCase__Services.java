package ${package};

import io.flexio.services.support.args.Arguments;
import io.flexio.services.support.args.ArgumentsException;
import io.flexio.services.support.api.ApiService;

public class ${apiNameCamelCase}Services {
    
    public static void main(String[] args){
        try{
            ApiService service = fromArguments(Arguments.parse(args));
            service.go();
        } catch (ArgumentsException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    public static ApiService fromArguments(Arguments arguments) throws ArgumentsException {
        return new ApiService(arguments.mandatoryInteger("port"), null, null);
    }
    
}

