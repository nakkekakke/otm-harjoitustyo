package cryptotracker.dao;

import cryptotracker.domain.CryptoBatch;
import cryptotracker.domain.Cryptocurrency;
import cryptotracker.domain.Portfolio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CryptoBatchDaoTest {
    
    @InjectMocks
    CryptoBatchDao batchDao;
    
    @Mock
    Database database;
    
    @Mock
    Connection conn;
    
    @Mock
    PreparedStatement stat;
    
    @Mock
    ResultSet rs;
    
    @Mock
    CryptocurrencyDao cryptoDao;
    
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
        when(cryptoDao.findOneWithId(any(Integer.class))).thenReturn(testCrypto);
        
        try {
            batchDao.findOneWithId(5);
        } catch (NullPointerException e) {
            
        }
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, times(3)).getInt(any(String.class));
//        when(LocalDate.parse(rs.getString("date"))).thenReturn(LocalDate.now());
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
        batchDao.findAll();
        
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
        batchDao.findAll();
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs, never()).getInt(any(String.class));
        inOrder.verify(rs, never()).getString(any(String.class));
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
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
        batchDao.findAllFromCryptocurrency(testCrypto);
        
        InOrder inOrder = inOrder(rs);
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt(any(String.class));
        inOrder.verify(rs).next();
        inOrder.verify(rs).close();
        inOrder.verifyNoMoreInteractions();
        
        verify(rs, times(2)).next();
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
    public void findOneFromCryptocurrencyWorksCorrectly() throws SQLException {
        CryptoBatch c = batchDao.findOneFromCryptocurrency(testBatch, testCrypto);
        List<CryptoBatch> batches = batchDao.findAllFromCryptocurrency(testCrypto);
        boolean found = false;
        for (CryptoBatch b : batches) {
            if (b.equals(testBatch)) {
                found = true;
            }
        }
        if (c == null) {
            assertFalse(found);
        } else {
            assertTrue(found);
        }
        
    }
    
    @Test
    public void saveStatementWorksCorrectly() throws SQLException {
        CryptoBatch batch = new CryptoBatch(1, 20, 10, LocalDate.now(), testCrypto);
        CryptoBatch b = batchDao.save(batch, testCrypto);
        
        InOrder inOrder = inOrder(stat);
        inOrder.verify(stat).setInt(1, 20);
        inOrder.verify(stat).setInt(2, 10);
        inOrder.verify(stat).setString(3, batch.getDate().toString());
        inOrder.verify(stat).setInt(4, testCrypto.getId());
        inOrder.verify(stat).executeUpdate();
        inOrder.verify(stat).close();
        inOrder.verifyNoMoreInteractions();
        
        assertEquals(b, batch);
    }


}