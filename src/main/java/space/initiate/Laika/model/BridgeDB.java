package space.initiate.Laika.model;

import space.initiate.Laika.util.LoggingManager;

import java.sql.Connection;
import java.sql.DriverManager;

public class BridgeDB {
    private Connection connection;

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

}
