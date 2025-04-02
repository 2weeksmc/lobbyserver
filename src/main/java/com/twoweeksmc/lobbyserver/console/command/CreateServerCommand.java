package com.twoweeksmc.lobbyserver.console.command;

import java.util.ArrayList;
import java.util.UUID;

import com.twoweeksmc.lobbyserver.Lobbyserver;
import com.twoweeksmc.lobbyserver.console.JLineConsole;
import com.twoweeksmc.lobbyserver.server.Server;
import com.twoweeksmc.lobbyserver.server.ServerTemplate;
import de.eztxm.ezlib.config.object.JsonObject;
import org.jetbrains.annotations.NotNull;

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
                    Server server = Server.getFromTemplate(ServerTemplate.valueOf(args[2].toUpperCase()));
                    JsonObject serverObject = getJsonObject(server);
                    Lobbyserver.getInstance().getMongoConnector().getServerModel().addServer(
                            UUID.fromString("b8309d91-e43b-4e17-955d-ca09a056dc7d"),
                            serverObject
                    );
                    return;
                }
                if (args[1].equalsIgnoreCase("custom")) {
                    if (args.length < 5) {
                        console.print("Usage: create server custom <options...>");
                        return;
                    }
                    Server server = new Server(0, Integer.parseInt(args[2]), 0,
                            Integer.parseInt(args[3]),
                            Integer.parseInt(args[4]), new ArrayList<>());
                    Lobbyserver.getInstance().getMongoConnector().getServerModel().addServer(
                            UUID.fromString("a0000a00-a00a-0a00-000a-aa00a000aa0a"),
                            getJsonObject(server));
                    /*
                     * TODO: ServerManager createServerContainer with generated uniqueId in Lobbyserver while creating server for database.
                     * TODO: Also add get server-info.json file data from created server container.
                     * TODO: Add check if container is existing in the database after creating and the other ways also. If not remove form database/docker.
                     */
                    Lobbyserver.getInstance().getServerManager().createServerContainer("purpur", "1.21.4");
                    return;
                }
                console.print("Usage: create server <method> <options...>");
            }
            default -> {
                console.print("Usage: create server <method> <options...>");
            }
        }
    }

    private static @NotNull JsonObject getJsonObject(Server server) {
        JsonObject serverObject = new JsonObject();
        serverObject.set("gemsPrice", String.valueOf(server.getGemsPrice()));
        serverObject.set("weeks", String.valueOf(server.getWeeks()));
        serverObject.set("cooldownWeeks", String.valueOf(server.getCooldownWeeks()));
        serverObject.set("maxPlayers", String.valueOf(server.getMaxPlayers()));
        serverObject.set("maxMemory", String.valueOf(server.getMaxMemory()));
        serverObject.set("plugins", String.valueOf(server.getPlugins()));
        return serverObject;
    }
}
