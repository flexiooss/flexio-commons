package io.flexio.docker.cmd.commands;

import org.codingmatters.poom.services.support.Env;

import java.util.LinkedList;
import java.util.List;

public class DockerContext {

    public static final String DOCKER_CLIENT_CONFIG_PATH = "DOCKER_CLIENT_CONFIG_PATH";
    static private DockerContext INSTANCE;

    static synchronized public DockerContext context() {
        if(INSTANCE == null) {
            INSTANCE = new DockerContext(
                    Env.optional(DOCKER_CLIENT_CONFIG_PATH).isPresent() ?
                            Env.mandatory(DOCKER_CLIENT_CONFIG_PATH).asString() :
                            null
            );
        }
        return INSTANCE;
    }

    private final String configPath;

    private DockerContext(String configPath) {
        this.configPath = configPath;
    }

    public String[] docker(String ... args) {
        List<String> result = new LinkedList<>();
        result.add("docker");
        if(this.configPath != null) {
            result.add("--config");
//            result.add("/wkdir/.docker");
            result.add(this.configPath);
        }
        if(args != null) {
            for (String arg : args) {
                result.add(arg);
            }
        }
        return result.toArray(new String[0]);
    }
}
