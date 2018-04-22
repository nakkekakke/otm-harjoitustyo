package cryptotracker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/** A class describing a registered user
 * 
 */
public class User {
    
    private int id;
    private String username;
    private Portfolio portfolio;
    
    public User(int id, String username) {
        this.id = id;
        this.username = username;
        this.portfolio = null;
    }
    
    public int getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setPortfolio(Portfolio p) {
        portfolio = p;
    }
    
    public Portfolio getPortfolio() {
        return portfolio;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        
        if (!(object instanceof User)) {
            return false;
        }
        
        User other = (User) object;
        if (!this.username.equals(other.getUsername())) {
            return false;
        }
//        if (this.id != other.id) {
//            return false;
//        }
//        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.username);
        return hash;
    }
    
}