package cryptotracker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
        return this.id == other.getId();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.user);
        return hash;
    }
}