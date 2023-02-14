package io.flexio.docker.cmd.commands;

import io.flexio.docker.api.types.ContainerCreationData;
import io.flexio.docker.cmd.commands.exception.CommandException;
import io.flexio.docker.cmd.commands.gen.DockerCommandGenerator;
import org.codingmatters.poom.services.support.process.ProcessInvoker;
import org.codingmatters.poom.services.support.process.SimpleProcessInvoker;

public abstract class DockerCreateCommand {

    static public DockerCreateCommand process(ProcessInvoker invoker, String containerName, ContainerCreationData containerCreationData) {
        return new DockerCreateCommand(containerName, containerCreationData) {
            @Override
            protected SimpleProcessInvoker.ProcessResponse dockerCreate() throws SimpleProcessInvoker.ProcessException {
                return SimpleProcessInvoker.invoker(invoker, new ProcessBuilder()).invoke(new DockerCommandGenerator().create(this.containerName, this.containerCreationData));
            }
        };
    }

    protected final String containerName;
    protected final ContainerCreationData containerCreationData;

    protected DockerCreateCommand(String containerName, ContainerCreationData containerCreationData) {
        this.containerName = containerName;
        this.containerCreationData = containerCreationData;
    }
    
    public void run() throws CommandException {
        SimpleProcessInvoker.ProcessResponse response;
        try {
            response = this.dockerCreate();
        } catch (SimpleProcessInvoker.ProcessException e) {
            throw new CommandException("command failed : docker create : " + this.containerName, e);
        }
        if(response.isError()) {
            throw new CommandException(String.format("failed creating %s, error status %s : %s", this.containerName, response.status(), response.err()));
        }
    }

    protected abstract SimpleProcessInvoker.ProcessResponse dockerCreate() throws SimpleProcessInvoker.ProcessException;
    
}
