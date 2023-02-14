package io.flexio.docker.cmd.commands;

import io.flexio.docker.cmd.commands.exception.CommandException;
import org.codingmatters.poom.services.support.process.ProcessInvoker;
import org.codingmatters.poom.services.support.process.SimpleProcessInvoker;

public abstract class DockerPullCommand {

    static public DockerPullCommand process(ProcessInvoker invoker, String image) {
        return new DockerPullCommand(image) {
            @Override
            protected SimpleProcessInvoker.ProcessResponse dockerRm() throws SimpleProcessInvoker.ProcessException {
                return SimpleProcessInvoker.invoker(invoker, new ProcessBuilder()).invoke(
                        DockerContext.context().docker("pull", this.image)
//                        "docker", "--config", "/wkdir/.docker", "pull", this.image
                );
            }
        };
    }

    protected final String image;

    protected DockerPullCommand(String image) {
        this.image = image;
    }

    public void run() throws CommandException {
        SimpleProcessInvoker.ProcessResponse response;
        try {
            response = this.dockerRm();
        } catch (SimpleProcessInvoker.ProcessException e) {
            throw new CommandException("command failed : docker pull " + this.image, e);
        }
        if(response.isError()) {
            throw new CommandException(String.format("failed pulling %s, error status %s : %s", this.image, response.status(), response.err()));
        }
    }

    protected abstract SimpleProcessInvoker.ProcessResponse dockerRm() throws SimpleProcessInvoker.ProcessException;
}
