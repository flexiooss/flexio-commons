package io.flexio.io.mongo.repository.util;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.hamcrest.Matchers.*;

public class EndIndexTest {

    @Test
    public void givenRequestedFrom0__whenFoundMoreThan0__thenFoundMinus1() throws Exception {
        assertThat(
                new EndIndex(0, 10).index(),
                is(9L)
        );
    }

    @Test
    public void givenRequestedFromNon0__whenFoundMoreThan0__thenStartPlusFoundMinus1() throws Exception {
        assertThat(
                new EndIndex(10, 10).index(),
                is(19L)
        );
    }

    @Test
    public void givenRequestedFrom0__whenFound0__then0() throws Exception {
        assertThat(
                new EndIndex(0, 0).index(),
                is(0L)
        );
    }

    @Test
    public void givenRequestedFromNon0__whenFound0__thenStartMinus1() throws Exception {
        assertThat(
                new EndIndex(10, 0).index(),
                is(9L)
        );
    }
}