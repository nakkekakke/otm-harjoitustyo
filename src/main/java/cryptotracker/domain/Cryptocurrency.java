package cryptotracker.domain;

import java.util.ArrayList;
import java.util.List;



// Yksittäistä portfoliossa olevaa kryptovaluttaa kuvaava luokka
public class Cryptocurrency {
    
    private Portfolio portfolio;
    private List<CryptoBatch> batches;
    
    public Cryptocurrency(Portfolio portfolio) {
        this.portfolio = portfolio;
        this.batches = new ArrayList<>();
    }
    
    public Portfolio getPortfolio() {
        return portfolio;
    }
    
    public List<CryptoBatch> getBatches() {
        return batches;
    }
    
    
}
