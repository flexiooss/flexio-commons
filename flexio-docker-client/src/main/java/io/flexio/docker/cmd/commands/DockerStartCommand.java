package io.flexio.docker.cmd.commands;

import io.flexio.docker.cmd.commands.exception.CommandException;
import org.codingmatters.poom.services.support.process.ProcessInvoker;
import org.codingmatters.poom.services.support.process.SimpleProcessInvoker;

public abstract class DockerStartCommand {

    static public DockerStartCommand process(ProcessInvoker invoker, String containerNameOrId) {
        return new DockerStartCommand(containerNameOrId) {
            @Override
            protected SimpleProcessInvoker.ProcessResponse dockerStart() throws SimpleProcessInvoker.ProcessException {
                return SimpleProcessInvoker.invoker(invoker, new ProcessBuilder()).invoke("docker", "start", containerNameOrId);
            }
        };
    }

    protected final String containerNameOrId;

    protected DockerStartCommand(String containerNameOrId) {
        this.containerNameOrId = containerNameOrId;
    }

    public void run() throws CommandException {
        SimpleProcessInvoker.ProcessResponse response;
        try {
            response = this.dockerStart();
        } catch (SimpleProcessInvoker.ProcessException e) {
            throw new CommandException("command failed : docker start " + this.containerNameOrId, e);
        }
        if(response.isError()) {
            throw new CommandException(String.format("failed starting %s, error status %s : %s", this.containerNameOrId, response.status(), response.err()));
        }
    }

    protected abstract SimpleProcessInvoker.ProcessResponse dockerStart() throws SimpleProcessInvoker.ProcessException;
}
