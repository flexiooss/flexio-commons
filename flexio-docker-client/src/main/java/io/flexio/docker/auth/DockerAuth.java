package io.flexio.docker.auth;

import org.codingmatters.poom.services.support.Env;

import java.util.Base64;
import java.util.Optional;

public class DockerAuth {

    static public DockerAuth fromEnv() {
        return new DockerAuth();
    }

    public String xRegistryAuth(String serverOrImage) {
        String server = this.server(serverOrImage);
        String base = this.base(server);
        Optional<Env.Var> username = Env.optional(base + "_USERNAME");
        Optional<Env.Var> password = Env.optional(base + "_PASSWORD");
        Optional<Env.Var> email = Env.optional(base + "_EMAIL");

        if(username.isEmpty()) {
            System.out.printf("[docker registry auth] missing username for %s\n", serverOrImage);
            return null;
        }
        if(password.isEmpty()) {
            System.out.printf("[docker registry auth] missing password for %s\n", serverOrImage);
            return null;
        }
        if(email.isEmpty()) {
            System.out.printf("[docker registry auth] missing email for %s\n", serverOrImage);
            return null;
        }

        String jsonAuth = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"serveraddress\":\"%s\"}",
                username.get().asString(),
                password.get().asString(),
                email.get().asString(),
                server
        );
        return Base64.getEncoder().encodeToString(jsonAuth.getBytes());
    }

    private String server(String serverOrImage) {
        if(serverOrImage.indexOf('/') != -1) {
            String candidate = serverOrImage.substring(0, serverOrImage.indexOf('/'));
            if(candidate.indexOf('.') != -1) {
                return candidate;
            } else {
                return "";
            }
        }

        if(serverOrImage.indexOf(':') != -1) {
            return "";
        }

        if(serverOrImage.indexOf('.') != -1) {
            return serverOrImage;
        } else {
            return "";
        }
    }

    private String base(String serverOrImage) {
        if(serverOrImage == null || serverOrImage.isEmpty()) {
            serverOrImage = "docker.io";
        }

        return serverOrImage.replaceAll("\\.", "_").toUpperCase() + "_LOGIN";
    }

}
