package cryptotracker.dao;

import cryptotracker.domain.Cryptocurrency;
import cryptotracker.domain.Portfolio;
import cryptotracker.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CryptocurrencyDao implements Dao<Cryptocurrency, Integer> {
    private UserDao userDao;
    private PortfolioDao portfolioDao;
    private Database database;

    
    public CryptocurrencyDao(Database database, UserDao udao, PortfolioDao pdao) {
        this.database = database;
        this.userDao = udao;
        this.portfolioDao = pdao;
    }
/**  Finds one instance of cryptocurrency, specified by an id
 * 
 * @param id The id specifying the cryptocurrency to find
 * @return The found cryptocurrency; null if nothing was found
 * @throws SQLException 
 */    
    @Override
    public Cryptocurrency findOneWithId(Integer id) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM Cryptocurrency WHERE Cryptocurrency.id = " + id);
        
        return null; // KESKEN
    }
    
/**  Finds all instances of cryptocurrency stored in the database
 * 
 *   @return A list containing every cryptocurrency found in the database; an empty list if nothing was found
 *   @throws java.sql.SQLException
 */ 
    @Override
    public List<Cryptocurrency> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM Cryptocurrency");
        ResultSet rs = stat.executeQuery();
        
        List<Cryptocurrency> cryptos = new ArrayList<>();
        
        while (rs.next()) {
            Portfolio found = portfolioDao.findOneWithId(rs.getInt("portfolio_id"));
            
            if (found != null) {
                cryptos.add(new Cryptocurrency(rs.getInt("id"), rs.getString("username"), found));
            }
        }
        
        rs.close();
        stat.close();
        conn.close();
        
        return cryptos;
    }
    
/**  Adds a cryptocurrency to the database 
 * 
 *   @param crypto The cryptocurrency that will be added to the database
 *   @return The added cryptocurrency, or null if nothing was added
 *   @throws java.sql.SQLException
 */ 
    @Override
    public Cryptocurrency save(Cryptocurrency crypto) throws SQLException {
//        if (findOneWithName(crypto.getId()) != null) {
//            return null;
//        }
        
        Connection conn = database.getConnection();
        PreparedStatement stat = conn.prepareStatement("INSERT INTO Cryptocurrency (name) VALUES (?)");
        stat.setString(1, crypto.getName());
        
        stat.executeUpdate();
        
        stat.close();
        conn.close();
        
        return crypto;
    }
    
/**  Deletes a user from the database using id
 * 
 *   @param id The id of the user that is to be deleted
 *   @throws java.sql.SQLException
 */ 
    @Override
    public void delete(Integer id) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stat = conn.prepareStatement("DELETE FROM User WHERE User.id = " + id);
            
            stat.executeUpdate();
            
            stat.close();
        }
    }

}
