package io.flexio.docker.auth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class DockerAuthTest {
    private final DockerAuth dockerAuth = DockerAuth.fromEnv();
    private String origDockerIoLoginUsername;
    private String origDockerIoLoginPassword;
    private String origDockerIoLoginEmail;
    private String origDockerRegistryOrgLoginUsername;
    private String origDockerRegistryOrgLoginPassword;
    private String origDockerRegistryOrgLoginEmail;

    @Before
    public void setUp() throws Exception {
        this.origDockerIoLoginUsername = System.clearProperty("docker.io.login.username");
        this.origDockerIoLoginPassword = System.clearProperty("docker.io.login.password");
        this.origDockerIoLoginEmail = System.clearProperty("docker.io.login.email");

        this.origDockerRegistryOrgLoginUsername = System.clearProperty("test.docker.registry.org.login.username");
        this.origDockerRegistryOrgLoginPassword = System.clearProperty("test.docker.registry.org.login.password");
        this.origDockerRegistryOrgLoginEmail = System.clearProperty("test.docker.registry.org.login.email");
    }

    @After
    public void tearDown() throws Exception {
        this.setSystemPropIf(this.origDockerIoLoginUsername, "docker.io.login.username");
        this.setSystemPropIf(this.origDockerIoLoginPassword, "docker.io.login.password");
        this.setSystemPropIf(this.origDockerIoLoginEmail, "docker.io.login.email");

        this.setSystemPropIf(this.origDockerRegistryOrgLoginUsername, "test.docker.registry.org.login.username");
        this.setSystemPropIf(this.origDockerRegistryOrgLoginPassword, "test.docker.registry.org.login.password");
        this.setSystemPropIf(this.origDockerRegistryOrgLoginEmail, "test.docker.registry.org.login.email");
    }

    private void setSystemPropIf(String value, String key) {
        if(value != null) {
            System.setProperty(key, value);
        }
    }

    @Test
    public void givenNoServerName__whenEnvNoSet__thenXRegistryAuthIsNull() throws Exception {
        assertThat(
                this.dockerAuth.xRegistryAuth(""),
                is(nullValue())
        );
    }

    @Test
    public void givenDockerIo__whenEnvSet__thenXRegistryAuthIsNull() throws Exception {
        assertThat(
                this.dockerAuth.xRegistryAuth("docker.io"),
                is(nullValue())
        );
    }

    @Test
    public void givenTestDockerRegistryOrg__whenEnvSet__thenXRegistryAuthIsNull() throws Exception {
        assertThat(
                this.dockerAuth.xRegistryAuth("test.docker.registry.org"),
                is(nullValue())
        );
    }

    @Test
    public void givenNoServerName__whenEnvSet__thenAuthSetWithEmptyServeraddress() throws Exception {
        System.setProperty("docker.io.login.username", "testuser");
        System.setProperty("docker.io.login.password", "testpassword");
        System.setProperty("docker.io.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth(""))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"\"}")
        );
    }

    @Test
    public void givenDockerIo__whenEnvSet__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("docker.io.login.username", "testuser");
        System.setProperty("docker.io.login.password", "testpassword");
        System.setProperty("docker.io.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("docker.io"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"docker.io\"}")
        );
    }

    @Test
    public void givenTestDockerRegistryOrg__whenEnvSet__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("test.docker.registry.org.login.username", "testuser");
        System.setProperty("test.docker.registry.org.login.password", "testpassword");
        System.setProperty("test.docker.registry.org.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("test.docker.registry.org"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"test.docker.registry.org\"}")
        );
    }

    @Test
    public void givenImageFromTestDockerRegistryOrg__whenEnvSet__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("test.docker.registry.org.login.username", "testuser");
        System.setProperty("test.docker.registry.org.login.password", "testpassword");
        System.setProperty("test.docker.registry.org.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("test.docker.registry.org/group/image:tag"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"test.docker.registry.org\"}")
        );
    }

    @Test
    public void givenImageFromTestDockerRegistryOrg__whenEnvSet_andNoTag__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("test.docker.registry.org.login.username", "testuser");
        System.setProperty("test.docker.registry.org.login.password", "testpassword");
        System.setProperty("test.docker.registry.org.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("test.docker.registry.org/group/image"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"test.docker.registry.org\"}")
        );
    }

    @Test
    public void givenImageFromTestDockerRegistryOrg__whenEnvSet_andNoTag_andNoGroup__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("test.docker.registry.org.login.username", "testuser");
        System.setProperty("test.docker.registry.org.login.password", "testpassword");
        System.setProperty("test.docker.registry.org.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("test.docker.registry.org/image"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"test.docker.registry.org\"}")
        );
    }

    @Test
    public void givenImageFromDockerIo__whenEnvSet__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("docker.io.login.username", "testuser");
        System.setProperty("docker.io.login.password", "testpassword");
        System.setProperty("docker.io.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("docker.io/group/image:tag"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"docker.io\"}")
        );
    }

    @Test
    public void givenImageFromDockerIo__whenEnvSet_andNoTag__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("docker.io.login.username", "testuser");
        System.setProperty("docker.io.login.password", "testpassword");
        System.setProperty("docker.io.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("docker.io/group/image"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"docker.io\"}")
        );
    }

    @Test
    public void givenImageFromDockerIo__whenEnvSet_andNoTag_andNoGroup__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("docker.io.login.username", "testuser");
        System.setProperty("docker.io.login.password", "testpassword");
        System.setProperty("docker.io.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("docker.io/image"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"docker.io\"}")
        );
    }

    @Test
    public void givenImageFromEmpty__whenEnvSet__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("docker.io.login.username", "testuser");
        System.setProperty("docker.io.login.password", "testpassword");
        System.setProperty("docker.io.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("group/image:tag"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"\"}")
        );
    }

    @Test
    public void givenImageFromEmpty__whenEnvSet_andNoGroup__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("docker.io.login.username", "testuser");
        System.setProperty("docker.io.login.password", "testpassword");
        System.setProperty("docker.io.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("image:tag"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"\"}")
        );
    }

    @Test
    public void givenImageFromEmpty__whenEnvSet_andNoGroup_andTagIsDoted__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("docker.io.login.username", "testuser");
        System.setProperty("docker.io.login.password", "testpassword");
        System.setProperty("docker.io.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("alpine:3.8"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"\"}")
        );
    }

    @Test
    public void givenImageFromEmpty__whenEnvSet_andNoTag_andNoGroup__thenAuthSetWithServeraddress() throws Exception {
        System.setProperty("docker.io.login.username", "testuser");
        System.setProperty("docker.io.login.password", "testpassword");
        System.setProperty("docker.io.login.email", "test@test.test");

        assertThat(
                new String(Base64.getDecoder().decode(this.dockerAuth.xRegistryAuth("image"))),
                is("{\"username\":\"testuser\",\"password\":\"testpassword\",\"email\":\"test@test.test\",\"serveraddress\":\"\"}")
        );
    }
}