package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertTrue(kortti.saldo() == 1000);
    }
    
    @Test
    public void kortinLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(300);
        assertTrue(kortti.saldo() == 1300);
        kortti.lataaRahaa(1000);
        assertTrue(kortti.saldo() == 2300);
    }
    
    @Test
    public void rahanOttaminenToimiiOikein() {
        boolean riittaa1 = kortti.otaRahaa(400);
        assertTrue(kortti.saldo() == 600); // vähenee
        
        boolean riittaa2 = kortti.otaRahaa(500);
        assertTrue(kortti.saldo() == 100); // vähenee
        
        boolean riittaa3 = kortti.otaRahaa(600);
        assertTrue(kortti.saldo() == 100); // ei vähene
        
        assertTrue(riittaa1 && riittaa2 && !riittaa3);
    }
    
    @Test
    public void toStringPrinttaaOikein() {
        int euroa = kortti.saldo() / 100;
        int senttia = kortti.saldo() % 100;
        assertEquals("saldo: " + euroa + "." + senttia, kortti.toString());
    }
}
