package cryptotracker.domain;

import java.time.LocalDate;



// Yksittäistä kryptovaluutan ostoerää kuvaava luokka
public class CryptoBatch {
    
    private Cryptocurrency crypto;
    private int amount;
    private int totalPaid;
    private LocalDate date;
    
    public CryptoBatch(Cryptocurrency crypto, int amount, int totalPaid, LocalDate date) {
        this.crypto = crypto;
        this.amount = amount;
        this.totalPaid = totalPaid;
        this.date = date;
    }

    public Cryptocurrency getCrypto() {
        return crypto;
    }

    public int getAmount() {
        return amount;
    }

    public int getTotalPaid() {
        return totalPaid;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public double getExchangeRate() {
        return totalPaid / amount;
    }
}
