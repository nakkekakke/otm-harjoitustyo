package cryptotracker.domain;

import java.util.ArrayList;
import java.util.List;



// Käyttäjän omistamaa portfoliota kuvaava luokka
public class Portfolio {
    
    private User user;
    private List<Cryptocurrency> cryptoList;
    
    public Portfolio(User user) {
        this.user = user;
        this.cryptoList = new ArrayList<>();
    }
    
    public User getUser() {
        return user;
    }
    
    public List<Cryptocurrency> getCryptoList() {
        return cryptoList;
    }
    
}