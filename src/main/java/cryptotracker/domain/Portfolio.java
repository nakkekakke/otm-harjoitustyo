package cryptotracker.domain;



// Käyttäjän omistamaa portfoliota kuvaava luokka
public class Portfolio {
    
    private User user;
    
    public Portfolio(User user) {
        this.user = user;
        
    }
    
    public User getUser() {
        return user;
    }
    
}