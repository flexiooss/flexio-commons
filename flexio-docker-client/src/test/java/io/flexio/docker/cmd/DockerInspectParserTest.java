package io.flexio.docker.cmd;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.container.NetworkSettings;
import io.flexio.docker.api.types.container.State;
import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.cmd.exceptions.Unparsable;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class DockerInspectParserTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @Test
    public void whenUnparsableJson__thenThrowsUnparsableException() throws Exception {
        assertThrows(Unparsable.class, () -> new DockerInspectParser(this.mapper).containerFor("un par sa ble"));
    }
    @Test
    public void whenArrayOfNonMap__thenEmptyContainer() throws Exception {
        assertThrows(Unparsable.class, () -> new DockerInspectParser(this.mapper).containerFor("[\"not a map\"]"));
    }
    @Test
    public void whenEmptyArray__thenEmptyContainer() throws Exception {
        assertTrue(new DockerInspectParser(this.mapper).containerFor("[]").isEmpty());
    }

    @Test
    public void whenStoppedContainer__thenDataParsedFromJson_andNameHeadingSlashRemoved() throws Exception {
        assertThat(
                new DockerInspectParser(this.mapper).containerFor(this.json("docker-cmds/docker-inspect-stopped-container.json")).get(),
                is(Container.builder()
                                .id("d723d76f4ffc5630071bc69583382b6c8292dfae72e2113b51116456e610e99a")
                                .state(State.builder()
                                        .status(State.Status.exited)
                                        .running(false)
                                        .restarting(false)
                                        .paused(false)
                                        .dead(false)
                                        .build())
                                .image("sha256:3d524ee645f0b09ff5b4bac952c72516387c9c6483dde36b9ec3c0b2eeb4cb1b")
                                .names("mariadb-ut")
                                .networkSettings(NetworkSettings.builder()
                                        .iPAddress("")
                                        .build())
                        .build())
        );
    }

    @Test
    public void givenRunningContainer__whenNoLocalIP__thenDataParsedFromJson_andNameHeadingSlashRemoved_andIPIsEmpty() throws Exception {
        assertThat(
                new DockerInspectParser(this.mapper).containerFor(this.json("docker-cmds/docker-inspect-running-container-without-toplevel-ip.json")).get(),
                is(Container.builder()
                                .id("3af2195a1855c45e69c4ae8da7a8b8261a0d45a9a550bffbef07648329aa6f73")
                                .state(State.builder()
                                        .status(State.Status.running)
                                        .running(true)
                                        .restarting(false)
                                        .paused(false)
                                        .dead(false)
                                        .build())
                                .image("sha256:c6f45fb1f0c415ad1b2f3758ae6bfb6ec1fde31feded4c4477e85b7689e5e0a4")
                                .names("dev-ui-bootstrap_dev-ui-bootstrap.1.32r4l9ii3rkkz6fns2d55ne8e")
                                .networkSettings(NetworkSettings.builder()
                                        .iPAddress("")
                                        .build())
                        .build())
        );
    }

    @Test
    public void givenRunningContainer__whenLocalIP__thenDataParsedFromJson_andNameHeadingSlashRemoved_andIPIsEmpty() throws Exception {
        assertThat(
                new DockerInspectParser(this.mapper).containerFor(this.json("docker-cmds/docker-inspect-running-container-with-toplevel-ip.json")).get(),
                is(Container.builder()
                                .id("d35f50d0ebc786f6d7d1ab1a3e2be475116253942644209e3aef08e2c6f87d21")
                                .state(State.builder()
                                        .status(State.Status.running)
                                        .running(true)
                                        .restarting(false)
                                        .paused(false)
                                        .dead(false)
                                        .build())
                                .image("sha256:14cfea360513a4972fd7860c16869ce0bc7396061d6ed87e68744fab95403d74")
                                .names("mongo-ut")
                                .networkSettings(NetworkSettings.builder()
                                        .iPAddress("172.17.0.2")
                                        .build())
                        .build())
        );
    }

    private String json(String resourceName) throws IOException {
        try(
                InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
                Reader reader = new InputStreamReader(in)
        ) {
            StringBuilder result = new StringBuilder();
            char [] buffer = new char[1024];
            for(int read = reader.read(buffer) ; read != -1 ; read = reader.read(buffer)) {
                result.append(buffer, 0, read);
            }
            return result.toString();
        }
    }
}