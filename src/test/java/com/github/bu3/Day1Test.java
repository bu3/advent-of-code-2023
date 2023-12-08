package com.github.bu3;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.Test;

public class Day1Test {

    @Test
    public void shouldReturn142() throws URISyntaxException, IOException {
        Day1 day1 = new Day1();
        assertThat(day1.convert("day1.test.txt"), equalTo(142));
    }
}