package space.initiate.Laika;

import space.initiate.Laika.model.bridge.BridgeDB;
import space.initiate.Laika.model.LaikaDB;
import space.initiate.Laika.model.manager.ServerManager;
import space.initiate.Laika.util.EmbedUI;
import space.initiate.Laika.util.LoggingManager;
import space.initiate.Laika.listener.MainListener;
import space.initiate.Laika.listener.SkynetListener;
import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandRegistrar;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.simpleyaml.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Laika Main Class
 * @author Laika
 * @version 1.0.0-rel
 * @apiNote Rewritten Jasper for Laika Replacement on initiate.space server.
 */
public class Laika {

    private final File CONFIG_FILE = new File("config.yml");

    public YamlConfiguration yamlConfiguration = new YamlConfiguration();

    public static final String name = "Laika";

    public static final String footer = name + " - " + "1.0.0-rel";

    public static Laika instance;

    private CommandRegistrar registrar;

    public static JDA JDA;

    public List<CommandClass> activeCommands;

    public LaikaConfig config;

    public LaikaDB database;
    public ServerManager serverManager;

    /**
     * Main Class function.
     * @param args - Arguments for program start.
     */
    public static void main(String[] args) {
        if(args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "-convert" -> {
                    //Convert legacy DB to updated DB without starting bot.
                    var init = new Laika();
                    instance = init;
                    init.convertLegacyFile(args[1]);
                }
                default -> {
                    //Help
                    LoggingManager.info(cliHelpPrompt());
                }
            }
        } else {
            try {
                var jas = new Laika();
                jas.start();
            } catch (Exception ex) {
                System.out.println("Failed to start Laika, check your Java installation.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * Ran on program start. Anything in here can determine whether the program will start.
     */
    public void start() {
        instance = this;
        LoggingManager.info("Starting Laika.");

        // All commands to be loaded on startup!
        registrar = new CommandRegistrar();
        activeCommands = registrar.getCommandClasses("space.initiate.Laika.command");

        //Load Configuration from system.
        tryLoadConfig();

        // Registers the stop() function if the program is stopped.
        LoggingManager.info("Registering shutdown hook.");
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

        // Initializes database and loads credentials.
        database = config.createDb();

        serverManager = new ServerManager();

        // Makes our JDA instance.
        startDiscord();
    }

    /**
     * Ran on program shutdown.
     */
    public void stop() {
        var build = new EmbedBuilder()
                .setColor(EmbedUI.FAILURE)
                .setTitle("Offline")
                .setFooter(footer)
                .setTimestamp(ZonedDateTime.now());
        JDA.getTextChannelById(config.getLogChannel()).sendMessageEmbeds(build.build()).queue();
        LoggingManager.info("Shutting down Laika.");
        if(database.saveServerInformation()) {
            LoggingManager.info("Successfully saved server information. Shutting down peacefully.");
        } else {
            for(int i = 0; i < 15; i++) {
                LoggingManager.error("FAILED TO SAVE SERVER INFORMATION.");
            }
        }
    }

    /**
     * Starts a JDA Instance.
     */
    public void startDiscord() {
        try {
            JDA = JDABuilder.create(config.getToken(), GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .disableCache(CacheFlag.EMOTE)
                    .setActivity(Activity.of(Activity.ActivityType.valueOf(config.getActivityType()), config.getActivityMsg()))
                    .setStatus(OnlineStatus.valueOf(config.getStatusType()))
                    .addEventListeners(
                            new MainListener(),
                            new SkynetListener()
                    ).build().awaitReady();
        } catch(Exception ex) {
            LoggingManager.error("Initialization broke...");
            ex.printStackTrace();
            return;
        }

        //todo command registry
        registrar.registerCommands(JDA, activeCommands);
        LoggingManager.info("Finished registering commands.");

        var eb = new EmbedBuilder()
                .setColor(EmbedUI.SUCCESS)
                .setTitle("Online")
                .setFooter(footer)
                .setTimestamp(ZonedDateTime.now());
        JDA.getTextChannelById(config.getLogChannel()).sendMessageEmbeds(eb.build()).queue();
    }

    public void registerForGuild(Guild g) {
        registrar.registerForGuild(g, activeCommands);
    }

    // Gets the active database.
    public LaikaDB getDatabase() { return database; }

    // Gets active ServerManager
    public ServerManager getServerManager() { return serverManager; }

    public String getName() { return name; }

    private void tryLoadConfig() {
        if (!CONFIG_FILE.exists()) {
            try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("config.yml")) {
                // Save the default config
                Files.copy(is, CONFIG_FILE.toPath());
                LoggingManager.info("Default Configuration saved to run directory. You will need to modify this before running the bot.");
                return;
            } catch (Exception ex) {
                LoggingManager.warn("Failed to create the configuration file. Stopping. (" + CONFIG_FILE.getAbsolutePath() + ")");
                ex.printStackTrace();
                return;
            }
        }

        // Try to load configuration into the configuration API.
        try {
            yamlConfiguration.load("config.yml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LoggingManager.warn("File not found, must've failed to create...");
        } catch (Exception e) {
            LoggingManager.warn("Ensure all values are inputted properly.");
            e.printStackTrace();
        }
        config = new LaikaConfig(yamlConfiguration);

        // Initializes our configuration helper & ensures it loads properly.
        if(config.load()) {
            LoggingManager.info("Fetched Laika config.");
        } else {
            LoggingManager.error("Failed to load configuration. Stopping process.");
            Runtime.getRuntime().exit(0);
        }
    }

    public static String cliHelpPrompt() {
        return """
                -convert <file> - converts legacy Laika SQLite3 DB to SQL Database, this dumps into the DB defined in Config.
                -help - displays this prompt.
                """;
    }

    public void convertLegacyFile(String location) {
        LoggingManager.warn("!!! CONVERSION OF PRIOR ASSETS WILL DUMP TO DB, USE WITH CARE !!!");
        var bridge = new BridgeDB(location);
        tryLoadConfig();
        bridge.convertLegacyLaikaDB();
    }
}