package space.initiate.Laika;

import space.initiate.Laika.model.bridge.BridgeDB;
import space.initiate.Laika.model.LaikaDB;
import space.initiate.Laika.util.LoggingManager;
import org.simpleyaml.configuration.file.YamlConfiguration;

import java.util.List;

/**
 * JasperConfig class
 * Helps out with loading things from config and fetching them in an easy manner.
 */
public class LaikaConfig {

    // Setup
    public YamlConfiguration configuration;

    /*
    Discord Variable Section
     */
    private String token;
    private String logChannel;
    private String ownerId;
    private String mainGuild;
    private String clientId;
    private int defaultInvitePermissionLevel;

    /*
    Jas Variable Section
     */
    private boolean sharded;
    private String activityType;
    private String activityMsg;
    private String statusType;
    private List<String> pingResponses;
    private int gameCheckTime;

    /*
    SQL Variable Section
     */
    private String host;
    private int port;
    private String username;
    private String password;
    private String database;

    /*
    Bridge Variable Section
     */
    private boolean sqlite;
    private String sqlitePath;
    private String bridgehost;
    private int bridgeport;
    private String bridgedb;
    private String bridgeusername;
    private String bridgepassword;

    /**
     * Constructor for the config.
     * @param configuration - the configuration file to be passed through in our friendly YamlConfiguration API :)
     */
    public LaikaConfig(YamlConfiguration configuration) {
        //Load config on class creation...
        this.configuration = configuration;
    }

    /**
     * Loads all configuration values from config.yml
     * @return - whether the values were loaded successfully.
     * Note: this function will perpetually be a mess, there is definitely a better way to do this,
     * but I refuse to do it a better way because it is EASY this way.
     */
    public boolean load() {
        try {
            // Discord loaders
            var discord = configuration.getConfigurationSection("discord");
            token = discord.getString("token");
            logChannel = discord.getString("logChannel");
            ownerId = discord.getString("ownerId");
            mainGuild = discord.getString("mainGuild");
            clientId = discord.getString("clientId");
            defaultInvitePermissionLevel = discord.getInt("invitePermissionLevel");
            // Written by dumbass :^)
            LoggingManager.info("""
                    Printing loaded discord configuration.
                    DISCORD CONFIGURATION
                    --------------------------------
                    Log Channel: %s
                    Owner ID: %s
                    Primary Guild: %s
                    Invite URL: %s
                    --------------------------------""".formatted(logChannel, ownerId, mainGuild, assembleDefaultInvite()));

            // Jasper loaders
            var main = configuration.getConfigurationSection("main");
            sharded = main.getBoolean("sharded");
            activityType = main.getString("activityType");
            activityMsg = main.getString("activityMsg");
            statusType = main.getString("statusType");
            pingResponses = main.getStringList("pingResponses");
            gameCheckTime = main.getInt("gameCheckTime");

            // SQL loaders
            var sql = configuration.getConfigurationSection("sql");
            host = sql.getString("host");
            port = sql.getInt("port");
            username = sql.getString("username");
            password = sql.getString("password");
            database = sql.getString("database");

            // Bridge loaders
            var bridge = configuration.getConfigurationSection("siteBridge");
            sqlite = bridge.getBoolean("sqlite");
            bridgehost = bridge.getString("host");
            bridgedb = bridge.getString("database");
            bridgeport = bridge.getInt("port");
            bridgeusername = bridge.getString("username");
            bridgepassword = bridge.getString("password");
        } catch(Exception ex) {
            ex.printStackTrace();
            LoggingManager.error("Failed to load configuration!");
            return false;
        }
        return true;
    }

    /**
     * Assembles the default invite that can be passed to a user requesting it.
     * @return - the invite
     */
    public String assembleDefaultInvite() {
        return "https://discord.com/oauth2/authorize?client_id=" + clientId + "&scope=bot+applications.commands&permissions=" + defaultInvitePermissionLevel;
    }

    public String getToken() { return token; }

    public String getActivityType() { return activityType; }

    public String getActivityMsg() { return activityMsg; }

    public String getStatusType() { return statusType; }

    public String getLogChannel() { return logChannel; }

    public int getGameCheckTime() { return gameCheckTime; }

    public List<String> getPingResponses() { return pingResponses; }

    public LaikaDB createDb() { return new LaikaDB(username, password, host, port, database); }

    public BridgeDB createBridge() {
        if(sqlite) {
            return new BridgeDB(sqlitePath);
        } else {
            return new BridgeDB(bridgeusername, bridgepassword, bridgehost, bridgeport, bridgedb);
        }
    }
}
