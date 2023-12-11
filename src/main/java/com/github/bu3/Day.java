package com.github.bu3;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllLines;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class Day {

    public File loadFile(String filename) throws URISyntaxException {
        ClassLoader classLoader = Day1.class.getClassLoader();
        URL resource = classLoader.getResource(filename);
        if (resource != null) {
            return new File(resource.toURI());
        }
        throw new IllegalArgumentException("file" + filename + " not found!");
    }

    public List<String> readFileContent(File file) throws IOException {
        return readAllLines(file.toPath(), UTF_8);
    }

}
