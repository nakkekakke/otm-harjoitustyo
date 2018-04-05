package cryptotracker.domain;

import java.util.Objects;




// Käyttäjää kuvaava luokka
public class User {
    
    private String username;
    
    public User(String username){
        this.username = username;
    }
    
    public String getUsername() {
        return username;
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