package cryptotracker.dao;

import cryptotracker.domain.Cryptocurrency;
import cryptotracker.domain.Portfolio;
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
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CryptocurrencyDaoTest {
    
    @InjectMocks
    CryptocurrencyDao cryptoDao;
    
    @Mock
    Database database;
    
    @Mock
    Connection conn;
    
    @Mock
    PreparedStatement stat;
    
    @Mock
    ResultSet rs;
    
    @Mock
    PortfolioDao portfolioDao;
    
    @Mock
    Portfolio testPortfolio;
    
    @Mock
    Cryptocurrency testCrypto;

    @Before
    public void setUp() throws SQLException {
        assertNotNull(database);
        when(database.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(any(String.class))).thenReturn(stat);
        when(stat.executeQuery()).thenReturn(rs);
        
    }

    @Test
    public void findOneWithIdStatementWorksCorrectly() throws SQLException {
        cryptoDao.findOneWithId(66);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, 66);
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
        
    }
    
    @Test
    public void findOneWithIdIfResultSetHasStuffGetIt() throws SQLException {
        when(rs.next()).thenReturn(true);
        cryptoDao.findOneWithId(40);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt(any(String.class));
//        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithIdIfResultSetIsEmptyDontGetAnything() throws SQLException {
        
        when(rs.next()).thenReturn(false);
        cryptoDao.findOneWithId(40);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs, never()).getString(any(String.class));
//        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllStatementWorksCorrectly() throws SQLException {
        cryptoDao.findAll();
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllIfResultSetHasStuffThenGetIt() throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        cryptoDao.findAll();
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt(any(String.class));
        inOrder.verify(rs).next();
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
        
        verify(rs, times(2)).next();
    }
    
    @Test
    public void findAllIfResultSetIsEmptyDontGetAnything() throws SQLException {
        when(rs.next()).thenReturn(false);
        cryptoDao.findAll();
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllInPortfolioStatementWorksCorrectly() throws SQLException {
        cryptoDao.findAllInPortfolio(testPortfolio);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllInPortfolioIfResultSetHasStuffThenGetIt() throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        cryptoDao.findAllInPortfolio(testPortfolio);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt(any(String.class));
        inOrder.verify(rs).next();
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
        
        verify(rs, times(2)).next();
    }
    
    @Test
    public void findAllInPortfolioIfResultSetIsEmptyDontGetAnything() throws SQLException {
        when(rs.next()).thenReturn(false);
        cryptoDao.findAllInPortfolio(testPortfolio);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs, never()).getString(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneInPortfolioWorksCorrectly() throws SQLException {
        Cryptocurrency c = cryptoDao.findOneInPortfolio(testCrypto, testPortfolio);
        assertEquals(c, null);
    }
    
    @Test
    public void saveWorksCorrectly() throws SQLException {
        Cryptocurrency c = cryptoDao.save(testCrypto, testPortfolio);
        
        verify(stat).setString(1, testCrypto.getName());
        verify(stat).setInt(2, testPortfolio.getId());
        verify(stat).executeUpdate();
        assertEquals(c, testCrypto);
    }
    
    @Test
    public void deleteWorksCorrectly() throws SQLException {
        cryptoDao.delete(10);
        verify(stat).setInt(1, 10);
        verify(stat).executeUpdate();
    }

}