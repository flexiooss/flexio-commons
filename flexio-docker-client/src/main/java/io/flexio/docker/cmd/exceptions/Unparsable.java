package io.flexio.docker.cmd.exceptions;

public class Unparsable extends Exception {
    public Unparsable(String message) {
        super(message);
    }

    public Unparsable(String message, Throwable cause) {
        super(message, cause);
    }
}
