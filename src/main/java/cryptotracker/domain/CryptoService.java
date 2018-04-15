package cryptotracker.domain;

import cryptotracker.dao.*;
import java.sql.SQLException;


/**  The class that handles the application logic
 * 
 */ 
public class CryptoService {
    
    private final UserDao userDao;
    private final PortfolioDao portfolioDao;
    private int usernameMinLength;
    private int usernameMaxLength;
    private User loggedIn;
    
    public CryptoService(UserDao userDao, PortfolioDao portfolioDao) {
        this.userDao = userDao;
        this.portfolioDao = portfolioDao;
        this.usernameMinLength = 4;
        this.usernameMaxLength = 20;
        this.loggedIn = null;
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
        
        return true;
    }
    
    /** Logs out the user who is currently logged in
     *   
     */ 
    public void logout() {
        loggedIn = null;
    }
    
    /** Creates a new portfolio for a user; used when creating a new user
     * 
     * @param user The user for which a new portfolio will be created
     * @return True if a portfolio was added for the user, otherwise false
     */
    private boolean createPortfolio(User user) {
        if (user.getPortfolio() != null) {
            return false;
        }
        
        Portfolio portfolio = new Portfolio(1, user);
        Portfolio saved;
        
        try {
            saved = portfolioDao.save(portfolio);
        } catch (SQLException e) {
            return false;
        }
        
        if (saved == null) {
            return false;
        }
        
        user.setPortfolio(portfolio);
        return true;
    }
    
    public void addCryptocurrency(Cryptocurrency crypto, Portfolio portfolio) {
        
    }
}
