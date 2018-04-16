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
        if (!(object instanceof User)) {
            return false;
        }
        
        User other = (User) object;
        return this.username.equals(other.username) && this.id == other.id;
    }
    
}