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
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    
//    @Rule
//    public TemporaryFolder testFolder = new TemporaryFolder();
        
    @InjectMocks
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
        when(stat.executeQuery()).thenReturn(rs);
        userDao = new UserDao(database);
        testUser = new User(20, "testUsername");
    }
    
//    @Test
//    public void saveWorks() throws SQLException {
//        userDao.save(testUser);
//        verify(stat).setString(1, "testUsername");
//        verify(stat).setString(1, "testUsername");
//    }
    
    @Test
    public void findOneWithIdStatementWorksCorrectly() throws SQLException {
        userDao.findOneWithId(82);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, 82);
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithIdIfResultSetIsEmptyDontGetAnything() throws SQLException {
        userDao.findOneWithId(111);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs, never()).getString(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithIdIfResultSetHasStuffThenGetIt() throws SQLException {
        when(rs.next()).thenReturn(true);
        userDao.findOneWithId(20);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt(any(String.class));
        inOrder.verify(rs).getString(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithUsernameStatementWorksCorrectly() throws SQLException {
        userDao.findOneWithUsername("someName");
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setString(1, "someName");
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithUsernameIfResultSetIsEmptyDontGetAnything() throws SQLException {
        when(rs.next()).thenReturn(false);
        userDao.findOneWithUsername("yetAnotherName");
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs, never()).getString(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithUsernameIfResultSetHasStuffThenGetIt() throws SQLException {
        when(rs.next()).thenReturn(true);
        userDao.findOneWithUsername("yetAnotherName");
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt(any(String.class));
        inOrder.verify(rs).getString(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllStatementWorksCorrectly() throws SQLException {
        userDao.findAll();
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllResultSetWorksCorrectly() throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        assertEquals(userDao.findAll().getClass().getName(), "java.util.ArrayList");
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt(any(String.class));
        inOrder.verify(rs).getString(any(String.class));
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs, never()).getString(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void saveStatementWorksCorrectly() throws SQLException {
        userDao.save(testUser);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setString(1, testUser.getUsername());
        inOrder.verify(stat).executeUpdate();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void saveReturnsUserIfUserDoesntExistYet() throws SQLException {
        UserDao spyDao = spy(userDao);
        doReturn(null).when(spyDao).findOneWithUsername(any(String.class));
        
        User returnUser = spyDao.save(testUser);
        assertTrue(returnUser != null);
    }
    
    @Test
    public void saveReturnsNullIfUserAlreadyExists() throws SQLException {
        UserDao spyDao = spy(userDao);
        User existingUser = new User(99, "name");
        doReturn(existingUser).when(spyDao).findOneWithUsername(any(String.class));
        
        User returnUser = spyDao.save(testUser);
        assertTrue(returnUser == null);
    }

    @Test
    public void deleteStatementWorksCorrectly() throws SQLException {
        userDao.delete(20);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, 20);
        inOrder.verify(stat).executeUpdate();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }

}