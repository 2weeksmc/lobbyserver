package com.twoweeksmc.lobbyserver;

import com.nexoscript.dsm.common.server.manager.ServerManager;
import com.twoweeksmc.connector.MongoConnector;
import com.twoweeksmc.lobbyserver.console.JLineConsole;
import com.twoweeksmc.lobbyserver.listener.InventoryPreClickListener;
import com.twoweeksmc.lobbyserver.listener.ItemDropListener;
import com.twoweeksmc.lobbyserver.listener.PlayerBlockBreakListener;
import com.twoweeksmc.lobbyserver.listener.PlayerBlockPlaceListener;
import com.twoweeksmc.lobbyserver.listener.PlayerConfigurationListener;
import com.twoweeksmc.lobbyserver.listener.PlayerSpawnListener;
import com.twoweeksmc.lobbyserver.listener.PlayerUseItemListener;
import com.twoweeksmc.lobbyserver.util.EventRegister;
import com.twoweeksmc.lobbyserver.util.PlayerManager;
import de.eztxm.ezlib.config.JsonConfig;

import java.io.IOException;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.world.Difficulty;

public final class Lobbyserver {

    private static Lobbyserver instance;
    private final JLineConsole console;
    private final JsonConfig serverConfiguration;
    private final JsonConfig databaseConfiguration;
    private final ServerManager serverManager;
    private final MongoConnector mongoConnector;
    private final PlayerManager playerManager;
    private final MinecraftServer minecraftServer;
    private final GlobalEventHandler globalEventHandler;
    private final InstanceManager instanceManager;
    private final InstanceContainer lobbyInstance;

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(() -> Lobbyserver.getInstance().getMongoConnector().getMongoClient().close())
                );
        new Lobbyserver();
    }

    public Lobbyserver() throws IOException {
        instance = this;
        this.console = new JLineConsole();
        this.console.print("Starting Lobbyserver...");
        this.console.print("Configuring server configuration...");
        this.serverConfiguration = new JsonConfig(".", "server.json");
        this.serverConfiguration.addDefault("host", "0.0.0.0");
        this.serverConfiguration.addDefault("port", 25590);
        this.serverConfiguration.addDefault("velocity-secret", "");
        this.console.print("Configured server configuration.");
        this.console.print("Configuring database configuration...");
        this.databaseConfiguration = new JsonConfig(".", "database.json");
        this.databaseConfiguration.addDefault("url", "mongodb-url");
        this.databaseConfiguration.addDefault("database", "database");
        this.console.print("Configured database configuration...");
        this.console.print("Initializing servermanager...");
        this.serverManager = new ServerManager("2weeksmc-server", 11000, "C:/2weeksmc/dsm-containers-pub");
        this.serverManager.start();
        this.console.print("Initialized servermanager.");
        this.console.print("Initializing database...");
        this.mongoConnector = new MongoConnector(this.databaseConfiguration);
        this.console.print("Initialized database.");
        this.console.print("Initializing playermanager...");
        this.playerManager = new PlayerManager();
        this.console.print("Initialized playermanager.");
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
        this.console.print("Registering event listeners...");
        this.registerEvents();
        this.console.print("Registered event listeners.");
        this.minecraftServer.start(
                this.serverConfiguration.get("host").asString(),
                this.serverConfiguration.get("port").asInteger()
        );
        this.console.print("Started lobbyserver.");
        this.console.start();
    }

    public void registerEvents() {
        EventRegister eventRegister = new EventRegister(this.globalEventHandler);
        eventRegister.register(
                new PlayerConfigurationListener(this.lobbyInstance, this.mongoConnector, this.playerManager).listen()
        );
        eventRegister.register(new InventoryPreClickListener(this.playerManager).listen());
        eventRegister.register(new PlayerUseItemListener(this.playerManager).listen());
        eventRegister.register(new PlayerSpawnListener().listen());
        eventRegister.register(new PlayerBlockBreakListener().listen());
        eventRegister.register(new PlayerBlockPlaceListener().listen());
        eventRegister.register(new ItemDropListener().listen());
    }

    public MongoConnector getMongoConnector() {
        return mongoConnector;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public static Lobbyserver getInstance() {
        return instance;
    }
}
