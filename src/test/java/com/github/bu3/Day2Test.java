package com.github.bu3;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.Test;

public class Day2Test {

    @Test
    public void shouldReturn8() throws URISyntaxException, IOException {
        assertThat(new Day2(12, 13, 14).count("day2.test.txt"), equalTo(8));
    }
}