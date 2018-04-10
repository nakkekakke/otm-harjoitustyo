package cryptotracker.dao;

import java.sql.SQLException;
import java.util.List;


/* The DAO interface for communication with the database
 *
 */
public interface Dao<T, K> {
/**
 * 
 * @param id Finds the object with the unique id from the database
 * @return The found object
 * @throws SQLException 
 */    
    T findOneWithId(K id) throws SQLException;
    
    
/**  Finds all objects of type T from the database 
 * 
 *   @return A list with all found objects
     * @throws java.sql.SQLException
 */    
    List<T> findAll() throws SQLException;
    
    
/**  Adds an object to the database 
 * 
 *   @param object The object that will be added to the database
 *   @return The added object; returns null if nothing was added
     * @throws java.sql.SQLException
 */ 
    T save(T object) throws SQLException;
    
    
/**  Deletes an object with matching key from the database
 * 
 *   @param key A key that specifies the object which will be deleted
     * @throws java.sql.SQLException
 */ 
    void delete(K key) throws SQLException;

}
