package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(500);
    }
    
    @Test
    public void rahanMaaraOikeaAlussa() {
        assertTrue(kassa.kassassaRahaa() / 100 == 1000);
    }
    
    @Test
    public void myytyjenMaaraOikeaAlussa() {
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 0);
    }
    
    @Test
    public void syoEdullisestiKateisellaLisaaMyytyjaYhdella() {
        kassa.syoEdullisesti(240);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 1);
        kassa.syoEdullisesti(240);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 2);
        
        kassa.syoEdullisesti(70); // maksu ei riittävä
        assertTrue(kassa.edullisiaLounaitaMyyty() == 2);
    }
    
    @Test
    public void syoMaukkaastiKateisellaLisaaMyytyjaYhdella() {
        kassa.syoMaukkaasti(400);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 1);
        kassa.syoMaukkaasti(400);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 2);
        
        kassa.syoMaukkaasti(300); // maksu ei riittävä
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 2);
    }
    
    @Test
    public void syoEdullisestiKateisellaKasvattaaKassanSaldoaOikein() {
        kassa.syoEdullisesti(240);
        assertTrue(kassa.kassassaRahaa() == 100240);
        kassa.syoEdullisesti(500);
        assertTrue(kassa.kassassaRahaa() == 100480);
        
        kassa.syoEdullisesti(10); // maksu ei riittävä
        assertTrue(kassa.kassassaRahaa() == 100480);
    }
    
    @Test
    public void syoMaukkaastiKateisellaKasvattaaKassanSaldoaOikein() {
        kassa.syoMaukkaasti(400);
        assertTrue(kassa.kassassaRahaa() == 100400);
        kassa.syoMaukkaasti(1000);
        assertTrue(kassa.kassassaRahaa() == 100800);
        
        kassa.syoMaukkaasti(250); // maksu ei riittävä
        assertTrue(kassa.kassassaRahaa() == 100800);
    }
    
    @Test
    public void syoEdullisestiKateisellaPalauttaaOikeanVaihtorahan() {
        int vaihtoraha = kassa.syoEdullisesti(240);
        assertTrue(vaihtoraha == 0);
        vaihtoraha = kassa.syoEdullisesti(600);
        assertTrue(vaihtoraha == 360);
        
        vaihtoraha = kassa.syoEdullisesti(160); // maksu ei riittävä
        assertTrue(vaihtoraha == 160);
    }
    
    @Test
    public void syoMaukkaastiKateisellaPalauttaaOikeanVaihtorahan() {
        int vaihtoraha = kassa.syoMaukkaasti(400);
        assertTrue(vaihtoraha == 0);
        vaihtoraha = kassa.syoMaukkaasti(920);
        assertTrue(vaihtoraha == 520);
        
        vaihtoraha = kassa.syoMaukkaasti(200); // maksu ei riittävä
        assertTrue(vaihtoraha == 200);
    }
    
    // MAKSUKORTTITESTIT
    
    @Test
    public void syoEdullisestiKortillaLisaaMyytyjaYhdella() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 1);
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 2);
        
        kassa.syoEdullisesti(kortti); // maksu ei riittävä
        assertTrue(kassa.edullisiaLounaitaMyyty() == 2);
    }
    
    @Test
    public void syoMaukkaastiKortillaLisaaMyytyjaYhdella() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 1);
        
        kassa.syoMaukkaasti(kortti); // maksu ei riittävä
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 1);
    }
    
        @Test
    public void syoEdullisestiKortillaEiMuutaKassanSaldoa() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.kassassaRahaa() == 100000);
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.kassassaRahaa() == 100000);
        
        kassa.syoMaukkaasti(kortti); // maksu ei riittävä
        assertTrue(kassa.kassassaRahaa() == 100000);
    }
    
    @Test
    public void syoMaukkaastiKortillaEiMuutaKassanSaldoa() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kassa.kassassaRahaa() == 100000);
        
        kassa.syoMaukkaasti(kortti); // maksu ei riittävä
        assertTrue(kassa.kassassaRahaa() == 100000);
    }
    
    @Test
    public void syoEdullisestiVeloittaaKorttiaOikein() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kortti.saldo() == 260);
        kassa.syoEdullisesti(kortti);
        assertTrue(kortti.saldo() == 20);
        
        kassa.syoEdullisesti(kortti); // maksu ei riittävä
        assertTrue(kortti.saldo() == 20);
    }
    
    @Test
    public void syoMaukkaastiVeloittaaKorttiaOikein() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kortti.saldo() == 100);
        
        kassa.syoMaukkaasti(kortti); // maksu ei riittävä
        assertTrue(kortti.saldo() == 100);
    }
    
    @Test
    public void syoEdullisestiKortillaPalauttaaFalseJosEiOnnistu() {
        boolean onnistui = kassa.syoEdullisesti(kortti);
        assertTrue(onnistui == true);
        onnistui = kassa.syoEdullisesti(kortti);
        assertTrue(onnistui == true);
        
        onnistui = kassa.syoEdullisesti(kortti); // maksu ei riittävä
        assertTrue(onnistui == false);
    }
    
    @Test
    public void syoMaukkaastiKortillaPalauttaaFalseJosEiOnnistu() {
        boolean onnistui = kassa.syoMaukkaasti(kortti);
        assertTrue(onnistui == true);
        
        onnistui = kassa.syoMaukkaasti(kortti); // maksu ei riittävä
        assertTrue(onnistui == false);
    }
    
    @Test
    public void kortinLatausKasvattaaKortinSaldoaOikein() {
        kassa.lataaRahaaKortille(kortti, 1000);
        assertTrue(kortti.saldo() == 1500);
        kassa.lataaRahaaKortille(kortti, -200); // invalidi määrä
        assertTrue(kortti.saldo() == 1500);
    }
    
    @Test
    public void kortinLatausKasvattaaKassanSaldoaOikein() {
        kassa.lataaRahaaKortille(kortti, 1000);
        assertTrue(kassa.kassassaRahaa() == 101000);
        kassa.lataaRahaaKortille(kortti, -200); // invalidi määrä
        assertTrue(kassa.kassassaRahaa() == 101000);
    }
}