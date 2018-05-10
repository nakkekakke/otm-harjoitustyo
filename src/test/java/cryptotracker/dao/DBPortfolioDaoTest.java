package cryptotracker.dao;

import cryptotracker.domain.Portfolio;
import cryptotracker.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DBPortfolioDaoTest {
    
    @InjectMocks
    DBPortfolioDao portfolioDao;
    
    @Mock
    Database database;
    
    @Mock
    Connection conn;
    
    @Mock
    PreparedStatement stat;
    
    @Mock
    ResultSet rs;
    
    @Mock
    DBUserDao userDao;
    
    @Mock
    User testUser;


    @Before
    public void setUp() throws SQLException {
        assertNotNull(database);
        when(database.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(any(String.class))).thenReturn(stat);
        when(stat.executeQuery()).thenReturn(rs);
        portfolioDao = new DBPortfolioDao(database, userDao);
    }

    @Test
    public void findOneWithIdStatementWorksCorrectly() throws SQLException {
        portfolioDao.findOneWithId(45);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, 45);
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
     
    @Test
    public void findOneWithIdIfResultSetHasStuffThenGetIt() throws SQLException {
        when(rs.next()).thenReturn(true);
        portfolioDao.findOneWithId(111);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, times(2)).getInt(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithIdIfResultSetEmptyDontGetAnything() throws SQLException {
        when(rs.next()).thenReturn(false);
        portfolioDao.findOneWithId(111);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
     
    @Test
    public void findAllStatementWorksCorrectly() throws SQLException {
        portfolioDao.findAll();
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
     
    @Test
    public void findAllResultSetWorksCorrectly() throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        portfolioDao.findAll();
         
        InOrder inOrder = inOrder(rs); 
        inOrder.verify(rs).next();
        inOrder.verify(rs, times(2)).getInt(any(String.class));
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithUserStatementWorksCorrectly() throws SQLException {
        portfolioDao.findOneWithUser(testUser);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, testUser.getId());
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithUserIfResultSetHasStuffThenGetIt() throws SQLException {
        when(rs.next()).thenReturn(true);
        Portfolio p = portfolioDao.findOneWithUser(testUser);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
        
        assertTrue(p == null);
    }
    
    @Test
    public void findOneWithUserIfUserWasFoundThenReturnIt() throws SQLException {
        User u = new User(95, "test");
        doReturn(u).when(userDao).findOneWithId(any(Integer.class));
        when(rs.next()).thenReturn(true);
        Portfolio p = portfolioDao.findOneWithUser(testUser);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, times(2)).getInt(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
        
        assertTrue(p != null);
    }
    
    @Test
    public void findOneWithUserIfResultSetEmptyDontGetAnything() throws SQLException {
        when(rs.next()).thenReturn(false);
        portfolioDao.findOneWithUser(testUser);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithUserReturnsNullIfParameterNull() throws SQLException {
        testUser = null;
        Portfolio p = portfolioDao.findOneWithUser(testUser);
        assertEquals(p, null);
    }
    
    @Test
    public void saveStatementWorksCorrectly() throws SQLException {
        portfolioDao.save(new Portfolio(10, testUser));
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, testUser.getId());
        inOrder.verify(stat).executeUpdate();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void saveReturnsParameterPortfolio() throws SQLException {
        Portfolio savedFolio = new Portfolio(5, testUser);
        Portfolio returnFolio = portfolioDao.save(savedFolio);
        assertEquals(savedFolio, returnFolio);
    }
    
    @Test
    public void saveReturnsNullWhenThrowsException() throws SQLException {
        when(stat.executeUpdate()).thenThrow(new SQLException());
        Portfolio p = portfolioDao.save(new Portfolio(14, testUser));
        assertEquals(p, null);
    }
    
    @Test
    public void deleteStatementWorksCorrectly() throws SQLException {
        portfolioDao.delete(16);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, 16);
        inOrder.verify(stat).executeUpdate();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    


}