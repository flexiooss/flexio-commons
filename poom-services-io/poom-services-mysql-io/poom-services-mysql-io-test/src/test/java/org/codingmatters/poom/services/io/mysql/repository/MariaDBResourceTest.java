package org.codingmatters.poom.services.io.mysql.repository;

import io.flexio.docker.DockerResource;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MariaDBResourceTest {

    @ClassRule(order = 0)
    static public DockerResource docker = DockerResource.client();

    @ClassRule(order = 1)
    static public MariaDBResource mariaDBResource = new MariaDBResource(docker);

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        mariaDBResource.wipe();
        this.connection = this.mariaDBResource.ds().getConnection();
    }

    @Test
    public void swiped1() throws Exception {
        this.swipedTest("swiped one");
    }

    @Test
    public void swiped2() throws Exception {
        this.swipedTest("swiped two");
    }

    @Test
    public void swiped3() throws Exception {
        this.swipedTest("swiped three");
    }

    private void swipedTest(String testName) throws Exception {
        this.connection.prepareStatement("CREATE TABLE pet (" +
                "name VARCHAR(20), owner VARCHAR(20), species VARCHAR(20), sex CHAR(1), birth DATE, death DATE" +
                ")").execute();

        ResultSet rs = this.connection.prepareStatement("select count(*) as cnt from pet").executeQuery();
        rs.next();
        assertThat(testName + " table is empty", rs.getInt("cnt"), is(0));
    }
}