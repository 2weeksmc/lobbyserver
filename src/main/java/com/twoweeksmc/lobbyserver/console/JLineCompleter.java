package com.twoweeksmc.lobbyserver.console;

import java.util.ArrayList;
import java.util.List;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

public class JLineCompleter implements Completer {

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        var readLine = line.line();
        var suggestions = new ArrayList<>(List.of("clear", "create", "exit", "help", "shutdown", "stop"));
        if (suggestions.isEmpty()) {
            return;
        }
        var answers = new ArrayList<String>();
        answers.addAll(suggestions);
        for (var answer : answers) {
            if (answer.startsWith(readLine)) {
                candidates.add(new Candidate(answer));
            }
        }
    }
}
