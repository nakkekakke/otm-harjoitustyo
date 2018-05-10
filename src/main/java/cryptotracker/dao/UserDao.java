package cryptotracker.dao;

import cryptotracker.domain.User;
import java.sql.SQLException;
import java.util.List;


public interface UserDao extends Dao<User, Integer> {

    /** Deletes a user from the database using id.
     *
     * @param id The id of the user that will be deleted.
     * @throws java.sql.SQLException
     */
    @Override
    void delete(Integer id) throws SQLException;

    /** Finds all users stored in the database
     *
     * @return A list containing every user found in the database. An empty list if nothing was found
     * @throws java.sql.SQLException
     */
    @Override
    List<User> findAll() throws SQLException;

    /** Finds an user from the database, using a unique id.
     *
     * @param id The id associated with the wanted user.
     * @return The found user. Null if nothing was found.
     * @throws java.sql.SQLException
     */
    @Override
    User findOneWithId(Integer id) throws SQLException;

    /** Finds an user from the database, using a unique username.
     *
     * @param username The username of the wanted user.
     * @return The found user, or null if nothing was found.
     * @throws java.sql.SQLException
     */
    User findOneWithUsername(String username) throws SQLException;

    /** Adds a user to the database .
     *
     * @param user The user that will be added to the database.
     * @return The added user, or null if that user already exists.
     * @throws java.sql.SQLException
     */
    User save(User user) throws SQLException;

}
