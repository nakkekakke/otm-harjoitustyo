package cryptotracker.dao;

import cryptotracker.domain.Cryptocurrency;
import cryptotracker.domain.Portfolio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/** The class for communication between cryptocurrencies and the database
 * 
 */
public class CryptocurrencyDao implements Dao<Cryptocurrency, Integer> {
    
    private final PortfolioDao portfolioDao;
    private final Database database;

    public CryptocurrencyDao(Database database, PortfolioDao portfoliodao) {
        this.database = database;
        this.portfolioDao = portfoliodao;
    }
    
    /** Finds one instance of cryptocurrency, specified by an id
     * 
     * @param id The id specifying the cryptocurrency to find
     * @return The found cryptocurrency; null if nothing was found
     * @throws java.sql.SQLException 
     */    
    @Override
    public Cryptocurrency findOneWithId(Integer id) throws SQLException {
        ResultSet rs = null;
        Portfolio p = null;
        try (Connection conn = database.getConnection(); 
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM Cryptocurrency WHERE Cryptocurrency.id = " + id)) {
            
            rs = stat.executeQuery();
            if (rs.next()) {
                p = portfolioDao.findOneWithId(rs.getInt("portfolio_id"));
            }
            if (p == null) {
                System.out.println("An error occurred while finding portfolio");
                return null;
            }
            
        }
        
        return new Cryptocurrency(id, rs.getString("name"), p);
    }
    
    /** Finds all instances of cryptocurrency stored in the database
     * 
     * @return A list containing every cryptocurrency found in the database; an empty list if nothing was found
     * @throws java.sql.SQLException
     */ 
    @Override
    public List<Cryptocurrency> findAll() throws SQLException {
        List<Cryptocurrency> cryptos = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM Cryptocurrency");
             ResultSet rs = stat.executeQuery()) {
            
            while (rs.next()) {
                Portfolio p = portfolioDao.findOneWithId(rs.getInt("portfolio_id"));
                
                if (p != null) {
                    cryptos.add(new Cryptocurrency(rs.getInt("id"), rs.getString("username"), p));
                }
            }
            
        }
        
        return cryptos;
    }
    
    /** Finds all instances of cryptocurrency in a specific portfolio
     * 
     * @param portfolio The portfolio whose cryptocurrencies will be returned
     * @return A list containing every cryptocurrency found in the specific portfolio; an empty list if nothing was found
     * @throws java.sql.SQLException 
     */
    public List<Cryptocurrency> findAllInPortfolio(Portfolio portfolio) throws SQLException {
        List<Cryptocurrency> cryptos = new ArrayList<>();
        if (portfolio == null) {
            return cryptos;
        }
        
        try (Connection conn = database.getConnection();
                PreparedStatement stat = 
                        conn.prepareStatement("SELECT * FROM Cryptocurrency WHERE Cryptocurrency.portfolio_id = " + portfolio.getId());
                ResultSet rs = stat.executeQuery()) {
            
            while (rs.next()) {
                Portfolio p = portfolioDao.findOneWithId(rs.getInt("portfolio_id"));
                
                if (p != null) {
                    cryptos.add(new Cryptocurrency(rs.getInt("id"), rs.getString("name"), p));
                }
            }
            
        }
        
        return cryptos;
    }
    
    public Cryptocurrency findOneInPortfolio(Cryptocurrency crypto, Portfolio portfolio) throws SQLException {
        try {
            for (Cryptocurrency c : findAllInPortfolio(portfolio)) {
                if (c.equals(crypto)) {
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /** Adds a cryptocurrency to the database 
     * 
     * @param crypto The cryptocurrency that will be added to the database
     * @param portfolio The portfolio of the logged in user
     * @return The added cryptocurrency, or null if nothing was added
     * @throws java.sql.SQLException
     */ 
    public Cryptocurrency save(Cryptocurrency crypto, Portfolio portfolio) throws SQLException {
        
        Cryptocurrency foundCrypto = findOneInPortfolio(crypto, portfolio);
        
        if (foundCrypto != null) {
            return null;
        }
        
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("INSERT INTO Cryptocurrency (name, portfolio_id) VALUES (?, ?)")) {
            
            stat.setString(1, crypto.getName());
            stat.setInt(2, portfolio.getId());
            stat.executeUpdate();
        
        }
        
        return crypto;
    }
    
    /**  Deletes an instance of cryptocurrency from the database using id
     * 
     *   @param id The id of the cryptocurrency that is to be deleted
     *   @throws java.sql.SQLException
     */ 
    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection conn = database.getConnection();
             PreparedStatement stat = 
                     conn.prepareStatement("DELETE FROM Cryptocurrency WHERE Cryptocurrency.id = " + id)) {
            
            stat.executeUpdate();
            
        }
    }
}
