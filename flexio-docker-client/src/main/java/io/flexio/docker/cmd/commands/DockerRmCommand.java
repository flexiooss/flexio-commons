package io.flexio.docker.cmd.commands;

import io.flexio.docker.cmd.commands.exception.CommandException;
import org.codingmatters.poom.services.support.process.ProcessInvoker;
import org.codingmatters.poom.services.support.process.SimpleProcessInvoker;

public abstract class DockerRmCommand {

    static public DockerRmCommand process(ProcessInvoker invoker, String containerNameOrId) {
        return new DockerRmCommand(containerNameOrId) {
            @Override
            protected SimpleProcessInvoker.ProcessResponse dockerRm() throws SimpleProcessInvoker.ProcessException {
                return SimpleProcessInvoker.invoker(invoker, new ProcessBuilder()).invoke("docker", "rm", containerNameOrId);
            }
        };
    }
    
    protected final String containerNameOrId;

    protected DockerRmCommand(String containerNameOrId) {
        this.containerNameOrId = containerNameOrId;
    }
    
    public void run() throws CommandException {
        SimpleProcessInvoker.ProcessResponse response;
        try {
            response = this.dockerRm();
        } catch (SimpleProcessInvoker.ProcessException e) {
            throw new CommandException("command failed : docker rm " + this.containerNameOrId, e);
        }
        if(response.isError()) {
            throw new CommandException(String.format("failed removing %s, error status %s : %s", this.containerNameOrId, response.status(), response.err()));
        }
    }

    protected abstract SimpleProcessInvoker.ProcessResponse dockerRm() throws SimpleProcessInvoker.ProcessException;
}
