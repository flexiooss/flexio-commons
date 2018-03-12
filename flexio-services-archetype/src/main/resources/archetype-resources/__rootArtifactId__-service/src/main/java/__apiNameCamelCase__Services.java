package ${package};

import io.flexio.services.support.args.Arguments;
import io.flexio.services.support.args.ArgumentsException;
import io.flexio.services.support.api.ApiService;
import io.flexio.services.support.mondo.MongoProvider;
import com.mongodb.MongoClient;


public class ${apiNameCamelCase}Services {
    
    public static void main(String[] args){
        try{
            ApiService service = fromArguments(Arguments.parse(args));
            service.go();
        } catch (ArgumentsException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static ApiService fromArguments(Arguments arguments) throws ArgumentException {
        MongoClient mongoClient = MongoProvider.fromArguments(arguments);
        return new ApiService(arguments.mandatoryInteger("port"), mongoClient::close, handlers, notificationService);
    }
    
}

