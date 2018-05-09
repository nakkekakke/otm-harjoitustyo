package cryptotracker.domain;

import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class InputCheckerTest {
    
    private InputChecker testInputChecker;

    @Before
    public void setUp() {
        testInputChecker = new InputChecker();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void isIntegerReturnsTrueIfInteger() {
        assertTrue(testInputChecker.isInteger("15239"));
    }
    
    @Test
    public void isIntegerReturnsFalseIfNotInteger() {
        assertFalse(testInputChecker.isInteger("10lol199"));
    }
    
    @Test
    public void isIntegerReturnsFalseIfEmptyString() {
        assertFalse(testInputChecker.isInteger(""));
    }
    
    @Test
    public void isValidDateReturnsTrueIfValidDate() {
        assertTrue(testInputChecker.isValidDate("2018-01-01"));
    }
     
    @Test
    public void isValidDateReturnsFalseIfStringTooShortOrLong() {
        assertFalse(testInputChecker.isValidDate(""));
        assertFalse(testInputChecker.isValidDate("2018-01"));
        assertFalse(testInputChecker.isValidDate("2018-01-01-01"));
    }
     
    @Test
    public void isValidDateReturnsFalseIfContainsLetters() {
        assertFalse(testInputChecker.isValidDate("2018-m1-1p"));
    }
    
    @Test
    public void isValidDateReturnsFalseIfNoHyphens() {
        assertFalse(testInputChecker.isValidDate("2018.01.01"));
        assertFalse(testInputChecker.isValidDate("2018 01 01"));
    }
    
    @Test
    public void tryParseDateReturnsParsedDateIfSuccessful() {
        String dateString = "2010-12-07";
        LocalDate date = testInputChecker.tryParseDate(dateString);
        assertNotNull(date);
        assertEquals(date.toString(), dateString);
    }
    
    @Test
    public void tryParseDateReturnsNullIfNotSuccessful() {
        String dateString = "0000-00-00";
        assertNull(testInputChecker.tryParseDate(dateString));
    }
    
    

}