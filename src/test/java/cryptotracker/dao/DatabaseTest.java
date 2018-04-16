package cryptotracker.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    
    File testFile;
    Database database;

    @Before
    public void setUp() throws ClassNotFoundException {
        this.testFile = new File("db/databaseTest.db");
        initializeDatabase(testFile.getPath());
    }

    @After
    public void tearDown() {
        testFile.delete();
    }
    
    private void initializeDatabase(String testAddress) throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:" + testAddress);
    }

    @Test
    public void connectionCanBeEstablished() throws SQLException {
        Connection conn = database.getConnection();
        assertFalse(conn.isClosed());
    }

}