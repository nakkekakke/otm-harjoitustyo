package cryptotracker.dao;

import cryptotracker.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    
//    @Rule
//    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Mock
    UserDao userDao;
    
    @Mock
    Database database;
    
    @Mock
    Connection conn;
    
    @Mock
    PreparedStatement stat;
    
    @Mock
    ResultSet rs;
    
    User testUser;
    
//    @BeforeClass
//    public void initialize() {
//        userDao = new UserDao(database);
//        testUser = new User(20, "testUsername");
//    }

    @Before
    public void setUp() throws SQLException {
        assertNotNull(database);
        when(database.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(any(String.class))).thenReturn(stat);
        userDao = new UserDao(database);
        testUser = new User(20, "testUsername");
        
    }

    @Test
    public void deleteTest() throws SQLException {
        userDao.delete(testUser.getId());
        verify(stat).setInt(1, 20);
    }

}