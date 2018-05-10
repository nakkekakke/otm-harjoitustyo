package cryptotracker.dao;

import cryptotracker.domain.Cryptocurrency;
import cryptotracker.domain.Portfolio;
import cryptotracker.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DBCryptocurrencyDaoTest {
    
    @InjectMocks
    DBCryptocurrencyDao cryptoDao;
    
    @Mock
    Database database;
    
    @Mock
    Connection conn;
    
    @Mock
    PreparedStatement stat;
    
    @Mock
    ResultSet rs;
    
    @Mock
    DBPortfolioDao portfolioDao;
    
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
        inOrder.verify(rs).close();
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
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findOneWithIdReturnsCryptoIfPortfolioExists() throws SQLException {
        User testUser = new User(19, "test");
        Portfolio foundFolio = new Portfolio(233, testUser);
        doReturn(foundFolio).when(portfolioDao).findOneWithId(any(Integer.class));
        when(rs.next()).thenReturn(true);
        Cryptocurrency foundCrypto = cryptoDao.findOneWithId(10);
        
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt("portfolio_id");
        inOrder.verify(rs).getString("name");
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
        
        assertTrue(foundCrypto != null);
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
    public void findAllReturnsNonEmptyListIfPortfolioExists() throws SQLException {
        User testUser = new User(11, "test");
        Portfolio foundFolio = new Portfolio(99, testUser);
        doReturn(foundFolio).when(portfolioDao).findOneWithId(any(Integer.class));
        when(rs.next()).thenReturn(true).thenReturn(false);
        List<Cryptocurrency> cryptoList = cryptoDao.findAll();
        
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt("portfolio_id");
        inOrder.verify(rs).getInt("id");
        inOrder.verify(rs).getString("username");
        inOrder.verify(rs).next();
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
        
        assertFalse(cryptoList.isEmpty());
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
        List<Cryptocurrency> cryptoList = cryptoDao.findAllInPortfolio(testPortfolio);
        
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
        List<Cryptocurrency> cryptoList = cryptoDao.findAllInPortfolio(testPortfolio);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs, never()).getString(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
        
        assertTrue(cryptoList.isEmpty());
    }
    
    @Test
    public void findAllInPortfolioReturnsNonEmptyListIfPortfolioExists() throws SQLException {
        User testUser = new User(112, "test");
        Portfolio foundFolio = new Portfolio(10, testUser);
        doReturn(foundFolio).when(portfolioDao).findOneWithId(any(Integer.class));
        when(rs.next()).thenReturn(true).thenReturn(false);
        List<Cryptocurrency> cryptoList = cryptoDao.findAllInPortfolio(testPortfolio);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt("portfolio_id");
        inOrder.verify(rs).getInt("id");
        inOrder.verify(rs).getString("name");
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
        
        assertFalse(cryptoList.isEmpty());
    }
    
    @Test
    public void findAllInPortfolioIfParameterIsNullReturnEmptyList() throws SQLException {
        testPortfolio = null;
        List<Cryptocurrency> cryptoList = cryptoDao.findAllInPortfolio(testPortfolio);
        assertTrue(cryptoList.isEmpty());
    }
    
    
    @Test
    public void findOneInPortfolioReturnsCryptoIfFound() throws SQLException {
        List<Cryptocurrency> cryptoList = new ArrayList<>();
        cryptoList.add(testCrypto);
        
        DBCryptocurrencyDao spyDao = spy(cryptoDao);
        doReturn(cryptoList).when(spyDao).findAllInPortfolio(testPortfolio);
        Cryptocurrency returnCrypto = spyDao.findOneInPortfolio(testCrypto, testPortfolio);
        
        assertEquals(returnCrypto, testCrypto);
    }
    
    @Test
    public void findOneInPortfolioReturnsNullIfCryptoNotFound() throws SQLException {
        DBCryptocurrencyDao spyDao = spy(cryptoDao);
        doReturn(null).when(spyDao).findOneInPortfolio(testCrypto, testPortfolio);
        Cryptocurrency returnCrypto = spyDao.findOneInPortfolio(testCrypto, testPortfolio);
        assertEquals(returnCrypto, null);
    }
    
    @Test
    public void saveSavesCryptoIfItDoesntExistYet() throws SQLException {
        DBCryptocurrencyDao spyDao = spy(cryptoDao);
        doReturn(null).when(spyDao).findOneInPortfolio(testCrypto, testPortfolio);
        Cryptocurrency returnCrypto = spyDao.save(testCrypto, testPortfolio);
        
        verify(stat).setString(1, testCrypto.getName());
        verify(stat).setInt(2, testPortfolio.getId());
        verify(stat).executeUpdate();
        assertEquals(returnCrypto, testCrypto);
    }
    
    @Test
    public void saveDoesntSaveCryptoIfItAlreadyExists() throws SQLException {
        DBCryptocurrencyDao spyDao = spy(cryptoDao);
        testCrypto = new Cryptocurrency(20, "test", testPortfolio);
        doReturn(testCrypto).when(spyDao).findOneInPortfolio(testCrypto, testPortfolio);
        Cryptocurrency returnCrypto = spyDao.save(testCrypto, testPortfolio);
        
        verify(stat, never()).setString(1, testCrypto.getName());
        verify(stat, never()).setInt(2, testPortfolio.getId());
        verify(stat, never()).executeUpdate();
        assertEquals(returnCrypto, null);
    }
    
    @Test
    public void deleteWorksCorrectly() throws SQLException {
        cryptoDao.delete(10);
        verify(stat).setInt(1, 10);
        verify(stat).executeUpdate();
    }

}