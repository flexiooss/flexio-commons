package io.flexio.services.support.mondo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import org.codingmatters.poom.services.support.Env;

import java.util.LinkedList;
import java.util.List;

public class MongoProvider {
    static public final String MONGO_URL = "MONGO_URL";
    static public final String MONGO_HOST = "MONGO_HOST";
    static public final String MONGO_PORT = "MONGO_PORT";

    static public final String MONGO_CONNECTION_TIMEOUT = "MONGO_CONNECTION_TIMEOUT";
    static private final String DEFAULT_MONGO_CONNECTION_TIMEOUT = "2000";

    private static final String MONGO_SELECT_TIMEOUT = "MONGO_SELECT_TIMEOUT";
    private static final String DEFAULT_MONGO_SELECT_TIMEOUT = "2000";

    static public boolean isAvailable() {
        return Env.optional(MONGO_URL).isPresent() || Env.optional(MONGO_HOST).isPresent() && Env.optional(MONGO_PORT).isPresent();
    }

    static public MongoClient fromEnv() {
        MongoClientOptions.Builder builder = MongoClientOptions.builder()
                .serverSelectionTimeout(Env.optional(MONGO_SELECT_TIMEOUT)
                        .orElse(Env.Var.value(DEFAULT_MONGO_SELECT_TIMEOUT))
                        .asInteger())
                .connectTimeout(Env.optional(MONGO_CONNECTION_TIMEOUT)
                        .orElse(Env.Var.value(DEFAULT_MONGO_CONNECTION_TIMEOUT))
                        .asInteger());
        return from(builder);
    }

    static public MongoClient from(MongoClientOptions.Builder optionsBuilder) {
        if (Env.optional(MONGO_URL).isPresent()) {
            return new MongoClient(new MongoClientURI(Env.mandatory(MONGO_URL).asString(), optionsBuilder));
        } else {
            return new MongoClient(addressListFromEnv(), optionsBuilder.build());
        }
    }

    private static List<ServerAddress> addressListFromEnv() {
        List<ServerAddress> result = new LinkedList<>();

        result.add(new ServerAddress(
                Env.mandatory(MONGO_HOST).asString(),
                Env.mandatory(MONGO_PORT).asInteger()
        ));
        return result;
    }

}
