package com.github.bu3;

import static java.lang.Integer.parseInt;
import static java.util.Collections.max;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 extends Day {
    private final Pattern numericPattern = Pattern.compile("\\d+");
    private final Pattern redNumericPattern = Pattern.compile("\\d+ red");
    private final Pattern greenNumericPattern = Pattern.compile("\\d+ green");
    private final Pattern blueNumericPattern = Pattern.compile("\\d+ blue");
    private final Integer reds;
    private final Integer greens;
    private final Integer blues;

    public static final String FILE = "day2.txt";

    public Day2(Integer reds, Integer greens, Integer blues) {
        this.reds = reds;
        this.greens = greens;
        this.blues = blues;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Sum:" + new Day2(12, 13, 14).count(FILE));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer count(String filename) throws URISyntaxException, IOException {
        List<Game> games = parseGames(filename);
        List<Integer> gameIds = games.stream()
            .filter(this::isValid)
            .map(Game::getId).collect(toList());
        return gameIds.stream()
            .reduce(Integer::sum).orElse(0);
    }

    public boolean isValid(Game game) {
        return max(game.getRounds().stream().map(Round::getReds).collect(toList())) <= reds &&
               max(game.getRounds().stream().map(Round::getGreens).collect(toList())) <= greens &&
               max(game.getRounds().stream().map(Round::getBlues).collect(toList())) <= blues;
    }

    private List<Game> parseGames(String filename) throws IOException, URISyntaxException {
        List<String> gamesContent = readFileContent(loadFile(filename));
        return gamesContent.stream().map(line -> {
            String[] breakDownLine = line.split(":");
            String gameHeader = breakDownLine[0];
            String roundsChunks = breakDownLine[1];
            return new Game(findGameId(gameHeader), findRounds(roundsChunks));
        }).collect(toList());
    }

    public Integer findGameId(String gameHeader) {
        Matcher matcher = numericPattern.matcher(gameHeader);
        if (matcher.find()) {
            return parseInt(matcher.group(0));
        }
        return 0;
    }

    public List<Round> findRounds(String roundChunks) {
        List<Round> rounds = new ArrayList<>();
        String[] roundsContent = roundChunks.split(";");
        for (String round : roundsContent) {
            int reds = 0, blues = 0, greens = 0;
            String[] ballsGroups = round.split(",");
            for (String balls : ballsGroups) {
                Matcher numericMatcher = numericPattern.matcher(balls);
                boolean numberFound = numericMatcher.find();
                if (redNumericPattern.matcher(balls).find() && numberFound) {
                    reds = parseInt(numericMatcher.group());
                }
                if (greenNumericPattern.matcher(balls).find() && numberFound) {
                    greens = parseInt(numericMatcher.group());
                }
                if (blueNumericPattern.matcher(balls).find() && numberFound) {
                    blues = parseInt(numericMatcher.group(0));
                }
            }
            rounds.add(new Round(reds, greens, blues));
        }
        return rounds;
    }

    class Game {

        private final Integer id;
        private final List<Round> rounds;

        public Game(Integer id, List<Round> rounds) {
            this.id = id;
            this.rounds = rounds;
        }

        public Integer getId() {
            return id;
        }

        public List<Round> getRounds() {
            return rounds;
        }

        public int countReds() {
            return rounds.stream().map(Round::getReds).reduce(Integer::sum).orElse(0);
        }

        public int countGreens() {
            return rounds.stream().map(Round::getGreens).reduce(Integer::sum).orElse(0);
        }

        public int countBlues() {
            return rounds.stream().map(Round::getBlues).reduce(Integer::sum).orElse(0);
        }
    }

    class Round {
        private final Integer reds;
        private final Integer greens;
        private final Integer blues;

        public Round(Integer reds, Integer greens, Integer blues) {
            this.reds = reds;
            this.greens = greens;
            this.blues = blues;
        }

        public Integer getReds() {
            return reds;
        }

        public Integer getGreens() {
            return greens;
        }

        public Integer getBlues() {
            return blues;
        }
    }
}
