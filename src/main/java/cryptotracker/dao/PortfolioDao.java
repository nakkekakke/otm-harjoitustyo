package cryptotracker.dao;

import cryptotracker.domain.Portfolio;
import cryptotracker.domain.User;
import java.sql.SQLException;
import java.util.List;


public interface PortfolioDao extends Dao<Portfolio, Integer> {

    /** Deletes a portfolio from the database, using a unique id.
     *
     * @param id The id of the portfolio that will be deleted.
     * @throws java.sql.SQLException
     */
    @Override
    void delete(Integer id) throws SQLException;

    /** Finds all portfolios stored in the database.
     *
     * @return A list containing every portfolio found in the database; an empty list if nothing was found.
     * @throws java.sql.SQLException
     */
    @Override
    List<Portfolio> findAll() throws SQLException;

    /** Finds a portfolio from the database, using a unique id.
     *
     * @param id The id associated with the wanted portfolio.
     * @return The found portfolio, or null if nothing was found.
     * @throws java.sql.SQLException
     */
    @Override
    Portfolio findOneWithId(Integer id) throws SQLException;

    /** Finds a portfolio belonging to a specified user.
     *
     * @param user The user whose portfolio will be searched for from the database.
     * @return The found cryptocurrency; null if the cryptocurrency wasn't found.
     * @throws java.sql.SQLException
     */
    Portfolio findOneWithUser(User user) throws SQLException;

    /** Adds a portfolio to the database.
     *
     * @param portfolio The portfolio that will be saved to the database.
     * @return The saved portfolio; null if an exception occurred.
     * @throws java.sql.SQLException
     */
    Portfolio save(Portfolio portfolio) throws SQLException;

}
