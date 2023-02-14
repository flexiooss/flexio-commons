package io.flexio.docker.cmd.commands;

import io.flexio.docker.cmd.commands.exception.CommandException;
import org.codingmatters.poom.services.support.process.ProcessInvoker;
import org.codingmatters.poom.services.support.process.SimpleProcessInvoker;

public abstract class DockerStopCommand {

    static public DockerStopCommand process(ProcessInvoker invoker, String containerNameOrId) {
        return new DockerStopCommand(containerNameOrId) {
            @Override
            protected SimpleProcessInvoker.ProcessResponse dockerStop() throws SimpleProcessInvoker.ProcessException {
                return SimpleProcessInvoker.invoker(invoker, new ProcessBuilder()).invoke("docker", "stop", containerNameOrId);
            }
        };
    }
    
    protected final String containerNameOrId;

    protected DockerStopCommand(String containerNameOrId) {
        this.containerNameOrId = containerNameOrId;
    }

    public void run() throws CommandException {
        SimpleProcessInvoker.ProcessResponse response;
        try {
            response = this.dockerStop();
        } catch (SimpleProcessInvoker.ProcessException e) {
            throw new CommandException("command failed : docker stop " + this.containerNameOrId, e);
        }
        if(response.isError()) {
            throw new CommandException(String.format("failed stopping %s, error status %s : %s", this.containerNameOrId, response.status(), response.err()));
        }
    }

    protected abstract SimpleProcessInvoker.ProcessResponse dockerStop() throws SimpleProcessInvoker.ProcessException;
}
