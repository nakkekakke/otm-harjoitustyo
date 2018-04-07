package cryptotracker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;




// Käyttäjää kuvaava luokka
public class User {
    
    private int id;
    private String username;
    private List<Portfolio> portfolios;
    
    public User(int id, String username){
        this.id = id;
        this.username = username;
        this.portfolios = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public List<Portfolio> getPortfolios() {
        return portfolios;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        
        User other = (User) object;
        return this.username.equals(other.username);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.username);
        return hash;
    }
    
}