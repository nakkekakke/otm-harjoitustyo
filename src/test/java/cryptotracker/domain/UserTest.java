package cryptotracker.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    User testUser;

    @Before
    public void setUp() {
        testUser = new User(313, "testUsername");
    }

    @Test
    public void constructorSetsUsernameCorrectly() {
        assertEquals("testUsername", testUser.getUsername());
    }
    
    @Test
    public void constructorSetsIdCorrectly() {
        assertEquals(313, testUser.getId());
    }
    
    @Test
    public void constructorSetsPortfolioNull() {
        assertEquals(null, testUser.getPortfolio());
    }
    
    @Test
    public void setPortfolioSetsPortfolioCorrectly() {
        Portfolio p = new Portfolio(15, testUser);
        testUser.setPortfolio(p);
        
        assertEquals(p, testUser.getPortfolio());
        assertEquals(testUser, testUser.getPortfolio().getUser());
    }
    
    @Test
    public void equalsFailsIfObjectsDifferentTypes() {
        Object o = new String();
        assertFalse(testUser.equals(o));
    }
}