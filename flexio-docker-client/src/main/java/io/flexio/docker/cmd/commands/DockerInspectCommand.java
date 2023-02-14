package io.flexio.docker.cmd.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.cmd.commands.exception.CommandException;
import io.flexio.docker.cmd.exceptions.Unparsable;
import io.flexio.docker.cmd.parsers.DockerInspectParser;
import org.codingmatters.poom.services.support.process.ProcessInvoker;
import org.codingmatters.poom.services.support.process.SimpleProcessInvoker;

public abstract class DockerInspectCommand {

    static public DockerInspectCommand process(ProcessInvoker invoker, ObjectMapper mapper, String containerNameOrId) {
        return new DockerInspectCommand(mapper, containerNameOrId) {
            @Override
            protected SimpleProcessInvoker.ProcessResponse dockerInspect() throws SimpleProcessInvoker.ProcessException {
                return SimpleProcessInvoker.invoker(invoker, new ProcessBuilder()).invoke("docker", "inspect", containerNameOrId);
            }
        };
    }

    protected abstract SimpleProcessInvoker.ProcessResponse dockerInspect() throws SimpleProcessInvoker.ProcessException;

    private final ObjectMapper mapper;
    protected final String containerNameOrId;

    protected DockerInspectCommand(ObjectMapper mapper, String containerNameOrId) {
        this.mapper = mapper;
        this.containerNameOrId = containerNameOrId;
    }

    public OptionalContainer run() throws CommandException {
        SimpleProcessInvoker.ProcessResponse response;
        try {
            response = this.dockerInspect();
        } catch (SimpleProcessInvoker.ProcessException e) {
            throw new CommandException("command failed : docker inspect " + this.containerNameOrId, e);
        }
        if(response.isError()) {
            return OptionalContainer.of(null);
        }
        try {
            return new DockerInspectParser(this.mapper).containerFor(response.out());
        } catch (Unparsable e) {
            throw new CommandException("failed parsing command output", e);
        }
    }
}
