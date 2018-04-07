package cryptotracker.domain;

import java.util.ArrayList;
import java.util.List;



// Yksittäistä portfoliossa olevaa kryptovaluttaa kuvaava luokka
public class Cryptocurrency {
    
    private String name;
    private Portfolio portfolio;
    private List<CryptoBatch> batches;
    
    public Cryptocurrency(String name, Portfolio portfolio) {
        this.name = name;
        this.portfolio = portfolio;
        this.batches = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public Portfolio getPortfolio() {
        return portfolio;
    }
    
    public List<CryptoBatch> getBatches() {
        return batches;
    }
    
    
}
