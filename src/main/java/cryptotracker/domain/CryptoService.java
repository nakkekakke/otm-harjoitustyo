package cryptotracker.domain;

import cryptotracker.dao.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


/**  The class that handles the application logic
 * 
 */ 
public class CryptoService {
    
    private final UserDao userDao;
    private final PortfolioDao portfolioDao;
    private final CryptocurrencyDao cryptoDao;
    private final CryptoBatchDao batchDao;
    private int usernameMinLength;
    private int usernameMaxLength;
    private User loggedIn;
    private Portfolio activePortfolio;
    
    public CryptoService(UserDao userDao, PortfolioDao portfolioDao, CryptocurrencyDao cryptoDao, CryptoBatchDao batchDao) {
        this.userDao = userDao;
        this.portfolioDao = portfolioDao;
        this.cryptoDao = cryptoDao;
        this.batchDao = batchDao;
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
    
    //
    // GENERAL (USER)
    //
    
    
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
        
        activePortfolio = findPortfolio(user);
        
        return true;
    }
    
    /** Logs out the user who is currently logged in
     *   
     */ 
    public void logout() {
        loggedIn = null;
        activePortfolio = null;
    }
    
    //
    // USER
    //
    
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
            return false;
        }
        return true;
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
    
    //
    // PORTFOLIO
    //
    
    
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
            System.out.println(e.getMessage());
            return false;
        }
        
        Portfolio p = findPortfolio(user);
        if (p == null) {
            return false;
        }
        
        user.setPortfolio(p);
        return true;
    }
    
    public Portfolio findPortfolio(User user) {
        Portfolio p = null;
        
        try {
            p = portfolioDao.findOneWithUser(user);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return p;
    }
    
    //
    // CRYPTOCURRENCY
    //
    
    private Cryptocurrency createCryptoInstance(String name) {

        try {
            Cryptocurrency newCrypto = new Cryptocurrency(1, name, activePortfolio);
            return cryptoDao.save(newCrypto, activePortfolio);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public boolean deleteCryptocurrency(Cryptocurrency crypto) {
        for (Cryptocurrency c : getCryptosInPortfolio()) {
            if (c.equals(crypto)) {
                try {
                    deleteBatchesOfCrypto(c);
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
    
    private Cryptocurrency findCryptoByName(String name) {
        for (Cryptocurrency c : getCryptosInPortfolio()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        
        return null;
    }
    
    //
    // CRYPTOBATCH
    //
    
    
    public CryptoBatch createCrypto(String name, int amount, int totalPaid, LocalDate date) {
        Cryptocurrency crypto = findCryptoByName(name);
        if (crypto == null) {
            crypto = createCryptoInstance(name);
            if (crypto == null) {
                return null;
            }
        }
        
        CryptoBatch newBatch = new CryptoBatch(1, amount, totalPaid, date, crypto);
        try {
            return batchDao.save(newBatch, crypto);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<CryptoBatch> getBatchesOfCrypto(Cryptocurrency crypto) {
        List<CryptoBatch> batches = new ArrayList<>();
        try {
            batches = batchDao.findAllFromCryptocurrency(crypto);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return batches;
    }
    
    private void deleteBatchesOfCrypto(Cryptocurrency crypto) {
        getBatchesOfCrypto(crypto).forEach((batch) -> {
            try {
                batchDao.delete(batch.getId());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
