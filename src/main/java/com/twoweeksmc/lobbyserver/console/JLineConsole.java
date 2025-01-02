package com.twoweeksmc.lobbyserver.console;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.InfoCmp;

import com.twoweeksmc.lobbyserver.Lobbyserver;
import com.twoweeksmc.lobbyserver.server.Server;
import com.twoweeksmc.lobbyserver.server.ServerTemplate;

public final class JLineConsole {
    private final Terminal terminal;
    private final LineReaderImpl reader;

    private boolean isRunning;

    public JLineConsole() throws IOException {
        this.terminal = TerminalBuilder.builder()
                .system(true)
                .jansi(true)
                .dumb(true)
                .encoding(StandardCharsets.UTF_8)
                .build();
        this.terminal.enterRawMode();
        this.reader = (LineReaderImpl) LineReaderBuilder.builder()
                .terminal(this.terminal)
                .completer(new JLineCompleter())
                .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
                .option(LineReader.Option.AUTO_PARAM_SLASH, false)
                .variable(LineReader.COMPLETION_STYLE_LIST_SELECTION, "fg:cyan")
                .variable(LineReader.COMPLETION_STYLE_LIST_BACKGROUND, "bg:default")
                .build();
        AttributedString coloredPrefix = new AttributedString(this.userPrefix());
        this.reader.setPrompt(coloredPrefix.toAnsi());
        this.isRunning = true;
        this.clear();
        this.sendWelcomeMessage();
    }

    public void start() {
        while (this.isRunning) {
            try {
                AttributedString coloredPrefix = new AttributedString(this.userPrefix());
                String input = this.reader.readLine(coloredPrefix.toAnsi()).trim();
                if (input.isEmpty()) {
                    this.print("[FF3333]The input field can not be empty.");
                    continue;
                }
                String[] inputParts = input.split(" ");
                String command = inputParts[0];
                String[] args = Arrays.copyOfRange(inputParts, 1, inputParts.length);
                switch (command) {
                    case "clear" -> this.clear();
                    case "create" -> {
                        if (args.length < 1) {
                            this.print("Usage: create server <method> <options...>");
                            continue;
                        }
                        switch (args[0].toLowerCase()) {
                            case "server" -> {
                                if (args.length < 2) {
                                    this.print("Usage: create server <method> <options...>");
                                    continue;
                                }
                                if (args[1].equalsIgnoreCase("template")) {
                                    if (args.length < 3) {
                                        this.print("Usage: create server template <template>");
                                        continue;
                                    }
                                    Lobbyserver.getInstance().getDatabaseProcessor().addServer(
                                            UUID.fromString("b8309d91-e43b-4e17-955d-ca09a056dc7d"),
                                            Server.getFromTemplate(ServerTemplate.valueOf(args[2].toUpperCase())));
                                    continue;
                                }
                                if (args[1].equalsIgnoreCase("custom")) {
                                    if (args.length < 5) {
                                        this.print("Usage: create server custom <options...>");
                                        continue;
                                    }
                                    Lobbyserver.getInstance().getDatabaseProcessor().addServer(
                                            UUID.fromString("b8309d91-e43b-4e17-955d-ca09a056dc7d"),
                                            new Server(0, 0, 0, new ArrayList<>()));
                                    continue;
                                }
                                this.print("Usage: create server <method> <options...>");
                            }
                            default -> {
                                this.print("Usage: create server <method> <options...>");
                            }
                        }
                    }
                    case "exit", "stop", "shutdown" -> {
                        this.isRunning = false;
                        this.print("Stopping server...");
                    }
                    case "help" -> {
                        this.print("&7-------------------------------&eHelp&7-------------------------------");
                        this.print(" &eclear &7- &fClear the console.");
                        this.print(" &ecreate server custom <options...> &7- &fCreate a custom server.");
                        this.print(" &ecreate server template <template>, &7- &fCreate a template-based server");
                        this.print(" &eexit, shutdown, stop &7- &fShutdown the cloud.");
                        this.print(" &ehelp &7- &fShow this help menu.");
                        this.print("&7-------------------------------&eHelp&7-------------------------------");
                    }
                    default -> this.print("Unknown command: " + command);
                }
            } catch (EndOfFileException e) {
                throw new RuntimeException(e);
            }
        }
        this.print("Stopped server.");
        System.exit(0);
    }

    public String prefix() {
        String prefix = "[FB1364-F9A608]2weeksmc &7» &f";
        return ConsoleColor.apply("\r" + prefix);
    }

    public String userPrefix() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            String prefix = "[FB1364-F9A608]%hostname &7» &f".replace("%hostname", hostname);
            return ConsoleColor.apply("\r" + prefix);

        } catch (UnknownHostException e) {
            return ConsoleColor
                    .apply("\r" + "[FB1364-F9A608]2weeksmc&7@&e%hostname &7» &f".replace("%hostname", "unknown"));
        }
    }

    public void sendWelcomeMessage() {
        System.out.print("\n");
        System.out.print("\n");
        System.out.println(ConsoleColor.apply("       [FB1364-F9A608]2weeksmc &7- &e1.0.0&7@&edevelopment"));
        System.out.println(ConsoleColor.apply("       &fby &eezTxmMC&7 & &eDragonRex"));
        System.out.print("\n");
        System.out.println(ConsoleColor.apply("       &fType &ehelp &fto list all commands."));
        System.out.print("\n");
        System.out.print("\n");
    }

    public void print(String message) {
        this.print(message, true);
    }

    public void print(String message, boolean newLine) {
        String coloredMessage = ConsoleColor.apply(this.prefix() + message);
        if (newLine) {
            System.out.println(coloredMessage);
            return;
        }
        System.out.print(coloredMessage);
    }

    public void clear() {
        this.terminal.puts(InfoCmp.Capability.clear_screen);
        this.terminal.flush();
    }
}
