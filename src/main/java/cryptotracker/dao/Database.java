package cryptotracker.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Tietokantayhteyden luova luokka
 * 
 */
public class Database {
    
    private String databaseAddress;
    
    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

}
