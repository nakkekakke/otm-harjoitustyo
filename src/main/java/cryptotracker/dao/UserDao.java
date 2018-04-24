package cryptotracker.dao;

import cryptotracker.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/** The class for communication between users and the database
 * 
 */
public class UserDao implements Dao<User, Integer> {
    
    private final Database database;
    
    public UserDao(Database database) {
        this.database = database;
    }
    
    /** Finds an user by id
     * 
     * @param id The id associated with a user
     * @return User, if any with the specified id was found; null if no user was found
     * @throws java.sql.SQLException
     */    
    @Override
    public User findOneWithId(Integer id) throws SQLException {
        User user = null;
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM User WHERE User.id = ?")) {
            
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("id"), rs.getString("username"));
                }
            } catch (Exception e) {
                return null;
            }
            
        }
        
        return user;
    }
    
    /** Finds an user by username 
     * 
     * @param username Username associated with a user
     * @return User, if any with the specified username was found; null if no user was found
     * @throws java.sql.SQLException
     */    
    public User findOneWithUsername(String username) throws SQLException {
        User user = null;
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM User WHERE User.username = ?")) {
            
            stat.setString(1, username);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("id"), rs.getString("username"));
                }
            } catch (Exception e) {
                return null;
            }
            
        }
        
        return user;
    }
    
    /** Finds all users stored in the database
     * 
     * @return A list containing every user found in the database, an empty list if nothing was found
     * @throws java.sql.SQLException
     */ 
    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM User");
             ResultSet rs = stat.executeQuery()) {
            
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username")));
            }
            
        }
        
        return users;
    }
    
    /** Adds a user to the database 
     * 
     * @param user The user that will be added to the database
     * @return The added user, or null if nothing was added
     * @throws java.sql.SQLException
     */ 
    public User save(User user) throws SQLException {
        if (findOneWithUsername(user.getUsername()) != null) {
            return null;
        }
        
        try (Connection conn = database.getConnection();
                PreparedStatement stat = conn.prepareStatement("INSERT INTO User (username) VALUES (?)")) {
            
            stat.setString(1, user.getUsername());
            stat.executeUpdate();
            
        }
        
        return user;
    }
    
    /** Deletes a user from the database using id
     * 
     * @param id The id of the user that is to be deleted
     * @throws java.sql.SQLException
     */ 
    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection conn = database.getConnection(); 
             PreparedStatement stat = conn.prepareStatement("DELETE FROM User WHERE User.id = ?")) {
            
            stat.setInt(1, id);
            stat.executeUpdate();
            
        }
    }
}
