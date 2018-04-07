package cryptotracker.domain;

import java.util.ArrayList;
import java.util.List;



// Yksittäistä portfoliossa olevaa kryptovaluttaa kuvaava luokka
public class Cryptocurrency {
    
    private int id;
    private String name;
    private Portfolio portfolio;
    private List<CryptoBatch> batches;
    
    public Cryptocurrency(int id, String name, Portfolio portfolio) {
        this.id = id;
        this.name = name;
        this.portfolio = portfolio;
        this.batches = new ArrayList<>();
    }
    
    public int getId() {
        return id;
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
