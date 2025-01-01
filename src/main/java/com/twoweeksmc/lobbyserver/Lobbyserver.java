package com.twoweeksmc.lobbyserver;

import com.twoweeksmc.lobbyserver.console.JLineConsole;
import com.twoweeksmc.lobbyserver.inventory.NavigatorInventory;
import com.twoweeksmc.lobbyserver.listener.*;
import com.twoweeksmc.lobbyserver.util.EventRegister;
import de.eztxm.config.JsonConfig;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.world.Difficulty;

import java.io.IOException;

public class Lobbyserver {
    private final JLineConsole console;
    private final JsonConfig serverConfiguration;
    private final JsonConfig databaseConfiguration;
    private final MinecraftServer minecraftServer;
    private final GlobalEventHandler globalEventHandler;
    private final InstanceManager instanceManager;
    private final InstanceContainer lobbyInstance;
    private final Inventory navigatorInventory;

    public static void main(String[] args) throws IOException {
        new Lobbyserver();
    }

    public Lobbyserver() throws IOException {
        this.console = new JLineConsole();
        this.console.print("Starting Lobbyserver...");
        this.console.print("Configuring server configuration...");
        this.serverConfiguration = new JsonConfig(".", "server.json");
        this.serverConfiguration.addDefault("host", "0.0.0.0");
        this.serverConfiguration.addDefault("port", 25590);
        this.console.print("Configured server configuration.");
        this.console.print("Configuring database configuration...");
        this.databaseConfiguration = new JsonConfig(".", "database.json");
        this.databaseConfiguration.addDefault("host", "127.0.0.1");
        this.databaseConfiguration.addDefault("port", 27017);
        this.databaseConfiguration.addDefault("database", "database");
        this.databaseConfiguration.addDefault("username", "root");
        this.databaseConfiguration.addDefault("password", "password");
        this.console.print("Configured database configuration...");
        this.console.print("Configuring minecraft server...");
        this.minecraftServer = MinecraftServer.init();
        MinecraftServer.setDifficulty(Difficulty.PEACEFUL);
        MinecraftServer.setCompressionThreshold(0);
        MinecraftServer.setBrandName("2weeksmc.com - Lobbyserver");
        VelocityProxy.enable(this.serverConfiguration.get("velocity-secret").asString());
        this.console.print("Enabled mojang auth.");
        this.globalEventHandler = MinecraftServer.getGlobalEventHandler();
        this.instanceManager = MinecraftServer.getInstanceManager();
        this.console.print("Initializing worlds...");
        this.lobbyInstance = this.instanceManager.createInstanceContainer();
        this.lobbyInstance.setChunkLoader(new AnvilLoader("worlds/SMP-Startinsel"));
        this.lobbyInstance.setChunkSupplier(LightingChunk::new);
        this.lobbyInstance.setTimeRate(0);
        this.lobbyInstance.setTime(6000);
        this.console.print("Initialized worlds.");
        this.console.print("Initializing inventories...");
        this.navigatorInventory = new NavigatorInventory();
        this.console.print("Initialized inventories.");
        this.console.print("Registering event listeners...");
        this.registerEvents();
        this.console.print("Registered event listeners.");
        this.minecraftServer.start(this.serverConfiguration.get("host").asString(), this.serverConfiguration.get("port").asInteger());
        this.console.print("Started lobbyserver.");
        this.console.start();
    }

    public void registerEvents() {
        EventRegister eventRegister = new EventRegister(this.globalEventHandler);
        eventRegister.register(new InventoryPreClickListener(this.navigatorInventory).listen());
        eventRegister.register(new PlayerConfigurationListener(this.lobbyInstance).listen());
        eventRegister.register(new PlayerUseItemListener(this.navigatorInventory).listen());
        eventRegister.register(new PlayerSpawnListener().listen());
        eventRegister.register(new PlayerBlockBreakListener().listen());
        eventRegister.register(new PlayerBlockPlaceListener().listen());
        eventRegister.register(new ItemDropListener().listen());
    }
}
