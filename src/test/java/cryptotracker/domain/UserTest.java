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

    @After
    public void tearDown() {
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
        
        assertEquals(15, testUser.getPortfolio().getId());
        assertEquals(testUser, testUser.getPortfolio().getUser());
    }
}