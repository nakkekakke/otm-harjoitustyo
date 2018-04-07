package cryptotracker.domain;

import java.util.ArrayList;
import java.util.List;


// The class describing a portfolio of a user
public class Portfolio {
    
    private int id;
    private User user;
    private String nimi;
    private List<Cryptocurrency> cryptoList;
    

    public Portfolio(int id, User user, String nimi) {
        this.id = id;
        this.user = user;
        this.cryptoList = new ArrayList<>();
        this.nimi = nimi;
    }
    
    public int getId() {
        return id;
    }
    
    /**
     *
     * @return  The user whose portfolio this is
     */
    public User getUser() {
        return user;
    }

    public String getNimi() {
        return nimi;
    }

    public List<Cryptocurrency> getCryptoList() {
        return cryptoList;
    }
    
}