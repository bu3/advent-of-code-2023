package com.github.bu3;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllLines;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

    public static final String FILE = "day1.txt";

    public static void main(String[] args) {
        try {
            System.out.println("Sum:" + new Day1().convert(FILE));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer convert(String filename) throws URISyntaxException, IOException {
        File file = loadFile(filename);
        List<String> fileContent = readFileContent(file);
        return convertContent(fileContent).stream().reduce(0, Integer::sum);
    }

    private File loadFile(String filename) throws URISyntaxException {
        ClassLoader classLoader = Day1.class.getClassLoader();
        URL resource = classLoader.getResource(filename);
        if (resource != null) {
            return new File(resource.toURI());
        }
        throw new IllegalArgumentException("file" + FILE + " not found!");
    }

    private List<String> readFileContent(File file) throws IOException {
        return readAllLines(file.toPath(), UTF_8);
    }

    private List<Integer> convertContent(List<String> content) {
        return content.stream().map(it-> {
            Integer firstDigit = findFirstDigit(it);
            Integer secondDigit = findFirstDigit(new StringBuilder(it).reverse().toString());
            return Integer.parseInt(firstDigit+""+secondDigit);
        }).collect(Collectors.toList());
    }

    private Integer findFirstDigit(String line) {
        List<Character> listCharacters = line
            .chars()
            .mapToObj(e -> (char) e)
            .collect(Collectors.toList());
        return listCharacters
            .stream()
            .filter(Character::isDigit)
            .findFirst().map(Character::getNumericValue).orElse(0);

    }

}
