package space.initiate.Laika.model.bridge;

import space.initiate.Laika.Laika;
import space.initiate.Laika.model.user.LegacyLaikaUser;
import space.initiate.Laika.util.LoggingManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BridgeDB {
    private Connection connection;

    public static String NOT_LINKED = "NOT_LINKED";

    private String PULL_OLD_USER = "SELECT * FROM fucku;";

    private List<LegacyLaikaUser> legacyUsers;

    /**
     * Legacy SQLite Bridge, until the website rewrite
     * @param sqlitePath path to SQLite DB
     */
    public BridgeDB(String sqlitePath) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + sqlitePath);
        } catch (Exception ex) {
            LoggingManager.error("Failed to initialize the SQLite instance.");
            ex.printStackTrace();
            return;
        }
    }

    /**
     * SQL Database Bridge, for the inevitable rewrite.
     * @param username username for the SQL db
     * @param password password for the db
     * @param host host for the db
     * @param port port for the db (3306 by def)
     * @param db database for the site
     */
    public BridgeDB(String username, String password, String host, int port, String db) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, username, password);
        } catch (Exception ex) {
            LoggingManager.error("Failed to initialize the MySQL instance.");
            ex.printStackTrace();
            return;
        }
    }

    public boolean validIR(String ir) {
        //TODO site verif
        //This should check to see if an irid exists, then if it does; if it's already linked
        //if its linked, respond false or if it doesn't exist, respond false.
        //Otherwise, set the DiscordID on the website backend, and validate the user on our side.
        return false;
    }

    public String fetchUserIp(String id) {
        //TODO fetch user IP via discordid from website db. if there is no user linked to the id, return NOT_LINKED.
        return NOT_LINKED;
    }

    //So we don't load an arraylist for no reason, we defer it to a function so its only created when needed.
    public void initializeLegacyList() {
        legacyUsers = new ArrayList<>();
    }

    /**
     * Legacy SQLite DB converter.
     */
    public void convertLegacyLaikaDB() {
        initializeLegacyList();
        //Here we go...
        try {
            PreparedStatement loadStat = connection.prepareStatement(PULL_OLD_USER);
            ResultSet rs = loadStat.executeQuery();
            while(rs.next()) {
                var newLegacy = new LegacyLaikaUser(
                        rs.getString("UserID"),
                        rs.getInt("XP"),
                        rs.getInt("Level"),
                        rs.getInt("Nword"),
                        rs.getInt("Credit"),
                        rs.getString("XPLock"),
                        rs.getString("GuildID")
                );
                legacyUsers.add(newLegacy);
            }
            LoggingManager.info("Loaded " + legacyUsers.size() + " users.");
        } catch (Exception ex) {
            ex.printStackTrace();
            LoggingManager.error("FAILED TO CONVERT LEGACY DATABASE, READ STACK.");
        }
        LoggingManager.info("Finished converting Legacy DB into objects. Attempting MariaDB Connection...");
        var db = Laika.instance.config.createDb();
        db.saveConvertedUsers(legacyUsers);
        LoggingManager.info("DONE!");
    }
}
