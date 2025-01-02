package com.twoweeksmc.lobbyserver.console.command;

import java.util.ArrayList;
import java.util.UUID;

import com.twoweeksmc.lobbyserver.Lobbyserver;
import com.twoweeksmc.lobbyserver.console.JLineConsole;
import com.twoweeksmc.lobbyserver.server.Server;
import com.twoweeksmc.lobbyserver.server.ServerTemplate;

public class CreateServerCommand {
    private final JLineConsole console;

    public CreateServerCommand(JLineConsole console) {
        this.console = console;
    }

    public void execute(String[] args) {
        if (args.length < 1) {
            console.print("Usage: create server <method> <options...>");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "server" -> {
                if (args.length < 2) {
                    console.print("Usage: create server <method> <options...>");
                    return;
                }
                if (args[1].equalsIgnoreCase("template")) {
                    if (args.length < 3) {
                        console.print("Usage: create server template <template>");
                        return;
                    }
                    Lobbyserver.getInstance().getDatabaseProcessor().addServer(
                            UUID.fromString("b8309d91-e43b-4e17-955d-ca09a056dc7d"),
                            Server.getFromTemplate(ServerTemplate.valueOf(args[2].toUpperCase())));
                    return;
                }
                if (args[1].equalsIgnoreCase("custom")) {
                    if (args.length < 5) {
                        console.print("Usage: create server custom <options...>");
                        return;
                    }
                    Lobbyserver.getInstance().getDatabaseProcessor().addServer(
                            UUID.fromString("b8309d91-e43b-4e17-955d-ca09a056dc7d"),
                            new Server(0, Integer.parseInt(args[2]), 0,
                                    Integer.parseInt(args[3]),
                                    Integer.parseInt(args[4]), new ArrayList<>()));
                    return;
                }
                console.print("Usage: create server <method> <options...>");
            }
            default -> {
                console.print("Usage: create server <method> <options...>");
            }
        }
    }
}
