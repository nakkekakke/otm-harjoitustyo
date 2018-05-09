package cryptotracker.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** The class for initializing database connection
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
    
    public void initializeTables() throws SQLException {
        String batch = "CREATE TABLE IF NOT EXISTS CryptoBatch(id integer PRIMARY KEY, amount integer, totalPaid integer, date text, cryptocurrency_id integer, FOREIGN KEY (cryptocurrency_id) REFERENCES Cryptocurrency(id))";
        String portfolio = "CREATE TABLE IF NOT EXISTS Portfolio(id integer PRIMARY KEY, user_id integer, FOREIGN KEY (user_id) REFERENCES User(id))";
        String user = "CREATE TABLE IF NOT EXISTS User(id integer PRIMARY KEY, username varchar(20))";
        String crypto = "CREATE TABLE IF NOT EXISTS Cryptocurrency(id integer PRIMARY KEY, name varchar(50), portfolio_id integer, FOREIGN KEY (portfolio_id) REFERENCES Portfolio(id))";
        
        try (Connection conn = this.getConnection();
            Statement stat = conn.createStatement()) {
            stat.execute(user);
            stat.execute(portfolio);
            stat.execute(crypto);
            stat.execute(batch);
        }
        
    }

}
