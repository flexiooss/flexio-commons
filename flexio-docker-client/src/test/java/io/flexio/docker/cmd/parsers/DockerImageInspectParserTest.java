package io.flexio.docker.cmd.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.Image;
import io.flexio.docker.cmd.exceptions.Unparsable;
import org.junit.Test;

import static io.flexio.docker.cmd.TUtils.resourceAsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class DockerImageInspectParserTest {

    private final ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void whenUnparsableJson__thenThrowsUnparsableException() throws Exception {
        assertThrows(Unparsable.class, () -> new DockerImageInspectParser(this.mapper).imageFor("un par sa ble"));
    }
    @Test
    public void whenArrayOfNonMap__thenEmptyImage() throws Exception {
        assertThrows(Unparsable.class, () -> new DockerImageInspectParser(this.mapper).imageFor("[\"not a map\"]"));
    }
    @Test
    public void whenEmptyArray__thenEmptyImage() throws Exception {
        assertTrue(new DockerImageInspectParser(this.mapper).imageFor("[]").isEmpty());
    }

    @Test
    public void given__whenParsingWelleFormedJson__thenImageIdAndRepoTagsSet() throws Exception {
        assertThat(
                new DockerImageInspectParser(this.mapper).imageFor(resourceAsString("docker-cmds/docker-image-inspect.json")).get(),
                is(Image.builder()
                        .id("sha256:14cfea360513a4972fd7860c16869ce0bc7396061d6ed87e68744fab95403d74")
                        .repoTags("harbor.ci.flexio.io/ci/mongo:4.0.3")
                        .build())
        );
    }

}