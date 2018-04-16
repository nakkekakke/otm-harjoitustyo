package cryptotracker.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class PortfolioTest {
    
    Portfolio testPortfolio;
    User assistanceUser;

    @Before
    public void setUp() {
        this.assistanceUser = new User(28, "username");
        this.testPortfolio = new Portfolio(1, assistanceUser);
    }

    @Test
    public void constructorSetsIdCorrectly() {
        User assistanceUser2 = new User(13, "usernameTwo");
        Portfolio testPortfolio2 = new Portfolio(100, assistanceUser2);
        
        assertEquals(1, testPortfolio.getId());
        assertEquals(100, testPortfolio2.getId());
    }
     
    @Test
    public void constructorSetsUserCorrectly() {
        User assistanceUser2 = new User(13, "usernameTwo");
        Portfolio testPortfolio2 = new Portfolio(100, assistanceUser2);
        
        assertEquals(assistanceUser, testPortfolio.getUser());
        assertEquals(assistanceUser2, testPortfolio2.getUser());
    }
     
    @Test
    public void constructorInitializesListAsArrayList() {
        assertTrue(testPortfolio.getCryptoList() != null);
        assertEquals("java.util.ArrayList", testPortfolio.getCryptoList().getClass().getName());
    }
    
    @Test
    public void equalsFailsIfObjectsDifferentTypes() {
        Object o = new String();
        assertFalse(testPortfolio.equals(o));
    }    
    
//    @Test
//    public void contentsOfCryptoListCanBeChanged() {
//        Cryptocurrency assistanceCrypto = new Cryptocurrency(5, "Bitcoin", testPortfolio);
//        assertFalse(testPortfolio.getCryptoList().contains(assistanceCrypto));
//        
//        testPortfolio.getCryptoList().add(assistanceCrypto);
//        assertTrue(testPortfolio.getCryptoList().contains(assistanceCrypto));
//    }

}