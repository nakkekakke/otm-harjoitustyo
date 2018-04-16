package cryptotracker.domain;

import java.util.ArrayList;
import java.util.List;


/** The class describing a portfolio of a user 
 *
 */
public class Portfolio {
    
    private int id;
    private User user;
    private List<Cryptocurrency> cryptoList;
    

    public Portfolio(int id, User user) {
        this.id = id;
        this.user = user;
        this.cryptoList = new ArrayList<>();
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

    public List<Cryptocurrency> getCryptoList() {
        return cryptoList;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Portfolio)) {
            return false;
        }
        
        Portfolio other = (Portfolio) object;
        return this.user.equals(other.getUser()) && this.id == other.getId();
    }
}