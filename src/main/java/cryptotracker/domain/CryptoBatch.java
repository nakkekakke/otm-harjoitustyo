package cryptotracker.domain;

import java.time.LocalDate;


/** The class describing a single purchase of a cryptocurrency
 * 
 */
public class CryptoBatch {
    
    private int id;
    private Cryptocurrency crypto;
    private int amount;
    private int totalPaid;
    private LocalDate date;
    
    public CryptoBatch(int id, Cryptocurrency crypto, int amount, int totalPaid, LocalDate date) {
        this.id = id;
        this.crypto = crypto;
        this.amount = amount;
        this.totalPaid = totalPaid;
        this.date = date;
    }
    
    public int getId() {
        return id;
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
