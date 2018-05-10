package cryptotracker.dao;

import cryptotracker.domain.CryptoBatch;
import cryptotracker.domain.Cryptocurrency;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class DBCryptoBatchDaoTest {
    
    @InjectMocks
    DBCryptoBatchDao batchDao;
    
    @Mock
    Database database;
    
    @Mock
    Connection conn;
    
    @Mock
    PreparedStatement stat;
    
    @Mock
    ResultSet rs;
    
    @Mock
    DBCryptocurrencyDao cryptoDao;
    
    @Mock
    Cryptocurrency testCrypto;
    
    @Mock
    CryptoBatch testBatch;

    @Before
    public void setUp() throws SQLException {
        assertNotNull(database);
        when(database.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(any(String.class))).thenReturn(stat);
        when(stat.executeQuery()).thenReturn(rs);
    }
    
    @Test
    public void findOneWithIdStatementWorksCorrectly() throws SQLException {
        batchDao.findOneWithId(99);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, 99);
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        
    }
    
    @Test
    public void findOneWithIdIfResultSetHasStuffGetIt() throws SQLException {
        when(rs.next()).thenReturn(true);
        doReturn(testCrypto).when(cryptoDao).findOneWithId(any(Integer.class));
        
        try {
            batchDao.findOneWithId(1);
        } catch (NullPointerException e) {
            
        }

        verify(rs).next();
        verify(rs, times(3)).getInt(any(String.class));
    }
    
    @Test
    public void findOneWithIdIfResultSetIsEmptyDontGetAnything() throws SQLException {
        when(rs.next()).thenReturn(false);
        batchDao.findOneWithId(40);
        
        verify(rs).next();
        verify(rs, never()).getInt(any(String.class));
        verify(rs, never()).getString(any(String.class));
    }
    
    @Test
    public void findAllStatementWorksCorrectly() throws SQLException {
        batchDao.findAll();
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllIfResultSetHasStuffThenGetIt() throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        doReturn(testCrypto).when(cryptoDao).findOneWithId(any(Integer.class));
        List<CryptoBatch> batches = new ArrayList<>();
        
        try {
            batches = batchDao.findAll();
        } catch (NullPointerException e) {
            
        }
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, times(4)).getInt(any(String.class));
        inOrder.verify(rs).getString("date");
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllIfResultSetIsEmptyDontGetAnything() throws SQLException {
        when(rs.next()).thenReturn(false);
        batchDao.findAll();
        
        verify(rs).next();
        verify(rs, never()).getInt(any(String.class));
        verify(rs, never()).getString(any(String.class));
        verify(rs).close();
    }
    
    @Test
    public void findAllIfCryptoIsNullDontGetAnything() throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        doReturn(null).when(cryptoDao).findOneWithId(any(Integer.class));
        batchDao.findAll();
        
        verify(rs, times(2)).next();
        verify(rs).getInt("cryptocurrency_id");
        verify(rs, never()).getString(any(String.class));
        verify(rs).close();
        verify(cryptoDao).findOneWithId(any(Integer.class));
    }
    
    @Test
    public void findAllFromCryptocurrencyStatementWorksCorrectly() throws SQLException {
        batchDao.findAllFromCryptocurrency(testCrypto);
        InOrder inOrder = inOrder(stat);
        
        inOrder.verify(stat).setInt(1, testCrypto.getId());
        inOrder.verify(stat).executeQuery();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllFromCryptocurrencyIfResultSetHasStuffThenGetIt() throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        doReturn(testCrypto).when(cryptoDao).findOneWithId(any(Integer.class));
        
        try {
            batchDao.findAllFromCryptocurrency(testCrypto);
        } catch (NullPointerException e) {
            
        }
        
        verify(rs).next();
        verify(rs, times(4)).getInt(any(String.class));
        verify(rs).getString("date");
        verify(rs).close();
    }
    
    @Test
    public void findAllFromCryptocurrencyIfResultSetIsEmptyDontGetAnything() throws SQLException {
        when(rs.next()).thenReturn(false);
        batchDao.findAllFromCryptocurrency(testCrypto);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs, never()).getString(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
    }
    
    @Test
    public void findAllFromCryptocurrencyIfParameterIsNullReturnEmptyList() throws SQLException {
        testCrypto = null;
        List<CryptoBatch> batches = batchDao.findAllFromCryptocurrency(testCrypto);
        
        assertTrue(batches.isEmpty());
    }
    
    @Test
    public void findOneFromCryptocurrencyReturnsBatchIfFound() throws SQLException {
        List<CryptoBatch> batches = new ArrayList<>();
        batches.add(testBatch);
        
        DBCryptoBatchDao spyDao = spy(batchDao);
        doReturn(batches).when(spyDao).findAllFromCryptocurrency(testCrypto);
        
        CryptoBatch returnBatch = spyDao.findOneFromCryptocurrency(testBatch, testCrypto);
        
        assertEquals(returnBatch, testBatch);
    }
    
    @Test
    public void findOneFromCryptocurrencyReturnsNullIfBatchNotFound() throws SQLException {
        List<CryptoBatch> batches = new ArrayList<>();
        batches.add(new CryptoBatch(100, 15, 13, LocalDate.now(), testCrypto));
        
        DBCryptoBatchDao spyDao = spy(batchDao);
        doReturn(batches).when(spyDao).findAllFromCryptocurrency(testCrypto);
        
        CryptoBatch returnBatch = spyDao.findOneFromCryptocurrency(testBatch, testCrypto);
        
        assertNull(returnBatch);
    }
    
    @Test
    public void saveSavesBatchCorrectly() throws SQLException {
        CryptoBatch paramBatch = new CryptoBatch(1, 20, 10, LocalDate.now(), testCrypto);
        CryptoBatch returnBatch = batchDao.save(paramBatch, testCrypto);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, 20);
        inOrder.verify(stat).setInt(2, 10);
        inOrder.verify(stat).setString(3, paramBatch.getDate().toString());
        inOrder.verify(stat).setInt(4, testCrypto.getId());
        inOrder.verify(stat).executeUpdate();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
        
        assertEquals(returnBatch, paramBatch);
    }
    
    @Test
    public void saveDoesntSaveIfParametersAreNull() throws SQLException {
        testBatch = null;
        CryptoBatch returnBatch = batchDao.save(testBatch, testCrypto);
        assertEquals(returnBatch, null);
        
        testCrypto = null;
        returnBatch = batchDao.save(testBatch, testCrypto);
        assertEquals(returnBatch, null);
        
        testBatch = new CryptoBatch(1, 1, 1, LocalDate.now(), testCrypto);
        returnBatch = batchDao.save(testBatch, testCrypto);
        assertEquals(returnBatch, null);
        
        verify(stat, never()).executeUpdate();
    }
    
    @Test
    public void deleteStatementWorksCorrectly() throws SQLException {
        batchDao.delete(10);
        verify(stat).setInt(1, 10);
        verify(stat).executeUpdate();
    }

}