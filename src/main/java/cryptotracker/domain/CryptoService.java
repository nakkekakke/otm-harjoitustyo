package cryptotracker.domain;

import cryptotracker.dao.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**  The class that handles the application logic
 * 
 */ 
public class CryptoService {
    
    private final UserDao userDao;
    private final PortfolioDao portfolioDao;
    private final CryptocurrencyDao cryptoDao;
    private int usernameMinLength;
    private int usernameMaxLength;
    private User loggedIn;
    private Portfolio activePortfolio;
    
    public CryptoService(UserDao userDao, PortfolioDao portfolioDao, CryptocurrencyDao cryptoDao) {
        this.userDao = userDao;
        this.portfolioDao = portfolioDao;
        this.cryptoDao = cryptoDao;
        this.usernameMinLength = 4;
        this.usernameMaxLength = 20;
        this.loggedIn = null;
        this.activePortfolio = null;
    }
    
    public int getUsernameMinLength() {
        return usernameMinLength;
    }
    
    public void setUsernameMinLength(int length) {
        this.usernameMinLength = length;
    }
    
    public int getUsernameMaxLength() {
        return usernameMaxLength;
    }
    
    public void setUsernameMaxLength(int length) {
        this.usernameMaxLength = length;
    }
    
    public User getLoggedIn() {
        return loggedIn;
    }
    
    public Portfolio getActivePortfolio() {
        return activePortfolio;
    }
    
    public void setActivePortfolio(Portfolio portfolio) {
        this.activePortfolio = portfolio;
    }
    
    /** Creates a new user if the username is not in use
     * 
     * @param username Username of the new user 
     * @return True if username was available, false if user with the same username already exists
     */     
    public boolean createUser(String username) {
        User user = new User(1, username);
        try {
            user = userDao.save(user);
        } catch (SQLException e) {
            return false;
        }
        
        if (user == null) {
            return false;
        }
        
        if (createPortfolio(user) == false) {
            System.out.println("Error while creating a portfolio!");
            return false;
        }
        return true;
    }
    
    /** Checks if the length of a username is valid
     * 
     * @param username The username that will be checked
     * @return True if the length of the username is valid, false if too short or too long
     */ 
    public boolean usernameLengthValid(String username) {
        if (username.length() >= usernameMinLength 
                && username.length() <= usernameMaxLength) {
            return true;
        }
        return false;
    }
    
    public User findUserFromDatabase(User user) {
        User foundUser;
        
        try {
            foundUser = userDao.findOneWithUsername(user.getUsername());
        } catch (SQLException e) {
            return null;
        }
        
        return foundUser;
    }
    
    /** Logs the user in
     * 
     * @param username The username used to log in
     * @return True if login was successful, otherwise false
     */ 
    public boolean login(String username) {
        User user;
        try {
            user = userDao.findOneWithUsername(username);
        } catch (SQLException e) {
            return false;
        }
        
        if (user == null) {
            return false;
        }
        
        loggedIn = user;
        
        activePortfolio = findPortfolioFromDatabase(user);
        
        return true;
    }
    
    /** Logs out the user who is currently logged in
     *   
     */ 
    public void logout() {
        loggedIn = null;
        activePortfolio = null;
    }
    
    /** Creates a new portfolio for a user; used when creating a new user
     * 
     * @param user The user for which a new portfolio will be created
     * @return True if a portfolio was added for the user, otherwise false
     */
    private boolean createPortfolio(User user) {
        User foundUser = findUserFromDatabase(user);
        if (foundUser.getPortfolio() != null) {
            return false;
        }
        
        Portfolio portfolio = new Portfolio(1, foundUser);
        
        try {
            portfolioDao.save(portfolio);
        } catch (SQLException e) {
            return false;
        }
        
        Portfolio p = findPortfolioFromDatabase(user);
        if (p != null) {
            return false;
        }
        
        user.setPortfolio(p);
        return true;
    }
    
    public Portfolio findPortfolioFromDatabase(User user) {
        try {
            for (Portfolio p : portfolioDao.findAll()) {
                if (p.getUser().equals(user)) {
                    return p;
                }
            }
        } catch (SQLException e) {
            return null;
        }
        
        return null;
    }
    
    public void addCryptoBatch(int id, String name, int amount, int totalPaid, String date) {
        // under construction
    }
    
    public Cryptocurrency createCryptocurrency(String name) {
        try {
            return cryptoDao.save(new Cryptocurrency(1, name, activePortfolio), activePortfolio);
        } catch (SQLException e) {
            return null;
        }
    }
    
    public boolean deleteCryptocurrency(Cryptocurrency crypto) {
        for (Cryptocurrency c : getCryptosInPortfolio()) {
            if (c.equals(crypto)) {
                try {
                    cryptoDao.delete(c.getId());
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
                return true;
            }
        }
        
        return false;
    }
    
    public List<Cryptocurrency> getCryptosInPortfolio() {
        try {
            return cryptoDao.findAllInPortfolio(activePortfolio);
        } catch (SQLException e) {
            return null;
        }
    }
}
