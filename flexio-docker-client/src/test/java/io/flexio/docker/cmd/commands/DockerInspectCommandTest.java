package io.flexio.docker.cmd.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.cmd.TUtils;
import org.codingmatters.poom.services.support.process.SimpleProcessInvoker;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

import static io.flexio.docker.cmd.TUtils.resourceAsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class DockerInspectCommandTest {
    @Test
    public void whenNoSuchContainer__thenEmpty() throws Exception {
        OptionalContainer container = new DockerInspectCommand(new ObjectMapper(), "container") {
            @Override
            protected SimpleProcessInvoker.ProcessResponse dockerInspect() {
                return new SimpleProcessInvoker.ProcessResponse(1, "[]", "Error: No such object: plop");
            }
        }.run();

        assertTrue(container.isEmpty());
    }

    @Test
    public void whenValidResponse__thenContainerBuilt() throws Exception {
        OptionalContainer container = new DockerInspectCommand(new ObjectMapper(), "container") {
            @Override
            protected SimpleProcessInvoker.ProcessResponse dockerInspect() throws SimpleProcessInvoker.ProcessException{
                try {
                    return new SimpleProcessInvoker.ProcessResponse(0, resourceAsString("docker-cmds/docker-inspect-running-container-with-toplevel-ip.json"), "");
                } catch (IOException e) {
                    throw new SimpleProcessInvoker.ProcessException("exception reading resource", e);
                }
            }
        }.run();

        assertThat(container.get().id(), is("d35f50d0ebc786f6d7d1ab1a3e2be475116253942644209e3aef08e2c6f87d21"));
    }
}