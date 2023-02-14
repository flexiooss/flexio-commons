package io.flexio.docker.cmd.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.optional.OptionalImage;
import io.flexio.docker.cmd.commands.exception.CommandException;
import io.flexio.docker.cmd.exceptions.Unparsable;
import io.flexio.docker.cmd.parsers.DockerImageInspectParser;
import org.codingmatters.poom.services.support.process.ProcessInvoker;
import org.codingmatters.poom.services.support.process.SimpleProcessInvoker;

public abstract class DockerImageInspectCommand {
    
    static public DockerImageInspectCommand process(ProcessInvoker invoker, ObjectMapper mapper, String imageNameOrId) {
        return new DockerImageInspectCommand(mapper, imageNameOrId) {
            @Override
            protected SimpleProcessInvoker.ProcessResponse dockerImageInspect() throws SimpleProcessInvoker.ProcessException {
                return SimpleProcessInvoker.invoker(invoker, new ProcessBuilder()).invoke("docker", "inspect", this.imageNameOrId);
            }
        };
    }

    protected abstract SimpleProcessInvoker.ProcessResponse dockerImageInspect() throws SimpleProcessInvoker.ProcessException;

    private final ObjectMapper mapper;
    protected final String imageNameOrId;

    protected DockerImageInspectCommand(ObjectMapper mapper, String imageNameOrId) {
        this.mapper = mapper;
        this.imageNameOrId = imageNameOrId;
    }

    public OptionalImage run() throws CommandException {
        SimpleProcessInvoker.ProcessResponse response;
        try {
            response = this.dockerImageInspect();
        } catch (SimpleProcessInvoker.ProcessException e) {
            throw new CommandException("command failed : docker image inspect " + this.imageNameOrId, e);
        }
        if(response.isError()) {
            return OptionalImage.of(null);
        }
        try {
            return new DockerImageInspectParser(this.mapper).imageFor(response.out());
        } catch (Unparsable e) {
            throw new CommandException("failed parsing command output", e);
        }
    }
}
