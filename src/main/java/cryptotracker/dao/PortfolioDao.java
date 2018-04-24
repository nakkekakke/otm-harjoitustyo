package cryptotracker.dao;

import cryptotracker.domain.Portfolio;
import cryptotracker.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/** The class for communication between portfolios and the database
 * 
 */
public class PortfolioDao implements Dao<Portfolio, Integer> {
    
    private final Database database;
    private final UserDao userDao;
    
    public PortfolioDao(Database database, UserDao userDao) {
        this.database = database;
        this.userDao = userDao;
    }

    /** Finds a portfolio by id
     * 
     * @param id The id of a portfolio
     * @return A portfolio, if any with the specified id was found; null if nothing was found
     * @throws java.sql.SQLException
     */    
    @Override
    public Portfolio findOneWithId(Integer id) throws SQLException {
        Portfolio portfolio = null;
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM Portfolio WHERE Portfolio.id = ?")) {
            stat.setInt(1, id);
            
            try (ResultSet rs = stat.executeQuery()) {
            
                if (rs.next()) {
                    User user = userDao.findOneWithId(rs.getInt("user_id"));
                    portfolio = new Portfolio(rs.getInt("id"), user);
                }
            }    
            
        }
        
        return portfolio;
    }    
    
    /** Finds all portfolios stored in the database
     * 
     * @return A list containing every portfolio found in the database; an empty list if nothing was found
     * @throws java.sql.SQLException
     */ 
    @Override
    public List<Portfolio> findAll() throws SQLException {
        List<Portfolio> portfolios = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM Portfolio");
             ResultSet rs = stat.executeQuery()) {
            
            while (rs.next()) {
                User user = userDao.findOneWithId(rs.getInt("user_id"));
                portfolios.add(new Portfolio(rs.getInt("id"), user));
            }
            
        }
        
        return portfolios;
    }

    
    /** Finds a portfolio belonging to a specified user
     * 
     * @param user The user whose portfolio will be searched for from the database
     * @return The found cryptocurrency; null if the cryptocurrency wasn't found
     * @throws java.sql.SQLException 
     */
    public Portfolio findOneWithUser(User user) throws SQLException {
        Portfolio portfolio = null;
        if (user == null) {
            return portfolio;
        }
        
        try (Connection conn = database.getConnection();
             PreparedStatement stat = 
                     conn.prepareStatement("SELECT * FROM Portfolio WHERE Portfolio.user_id = ?")) {
            
            stat.setInt(1, user.getId());
            try (ResultSet rs = stat.executeQuery()) {
            
                if (rs.next()) {
                    User u = userDao.findOneWithId(rs.getInt("user_id"));                
                    if (u != null) {
                        return new Portfolio(rs.getInt("id"), u);
                    }
                }
            }
        }
        
        return null;
    }
    
    /** Adds a portfolio to the database 
     * 
     * @param portfolio The portfolio that will be saved to the database
     * @return The saved portfolio; null if an error occurred
     * @throws java.sql.SQLException
     */ 
    public Portfolio save(Portfolio portfolio) throws SQLException {        
        try (Connection conn = database.getConnection(); 
             PreparedStatement stat = conn.prepareStatement("INSERT INTO Portfolio (user_id) VALUES (?)")) {
            
            stat.setInt(1, portfolio.getUser().getId());
            stat.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        return portfolio;
    }
    
    /** Deletes a portfolio from the database using the id of the portfolio
     * 
     * @param id The id of the portfolio that is to be deleted
     * @throws java.sql.SQLException
     */ 
    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("DELETE FROM Portfolio WHERE Portfolio.id = ?")) {
            
            stat.setInt(1, id);
            stat.executeUpdate();
            
        }
    }
}
