package cryptotracker.dao;

import cryptotracker.domain.Cryptocurrency;
import cryptotracker.domain.Portfolio;
import java.sql.SQLException;
import java.util.List;


public interface CryptocurrencyDao extends Dao<Cryptocurrency, Integer> {

    /** Deletes a cryptocurrency from the database, using unique id.
     *
     * @param id The id of the cryptocurrency that will be deleted.
     * @throws java.sql.SQLException
     */
    @Override
    void delete(Integer id) throws SQLException;

    /** Finds all cryptocurrencies stored in the database.
     *
     * @return A list containing every cryptocurrency found in the database; an empty list if nothing was found
     * @throws java.sql.SQLException
     */
    @Override
    List<Cryptocurrency> findAll() throws SQLException;

    /** Finds all cryptocurrencies in a specified portfolio.
     *
     * @param portfolio The portfolio whose cryptocurrencies will be returned.
     * @return A list containing every cryptocurrency found in the specified portfolio, or an empty list if nothing was found.
     * @throws java.sql.SQLException
     */
    List<Cryptocurrency> findAllInPortfolio(Portfolio portfolio) throws SQLException;

    /** Finds a cryptocurrency from the database, using a unique id.
     *
     * @param id The id of the wanted cryptocurrency.
     * @return The found cryptocurrency, or null if nothing was found.
     * @throws java.sql.SQLException
     */
    @Override
    Cryptocurrency findOneWithId(Integer id) throws SQLException;

    /** Adds a cryptocurrency to the database.
     *
     * @param crypto The cryptocurrency that will be added to the database.
     * @param portfolio The portfolio of the logged in user.
     * @return The added cryptocurrency, or null if the cryptocurrency already exists in that portfolio.
     * @throws java.sql.SQLException
     */
    Cryptocurrency save(Cryptocurrency crypto, Portfolio portfolio) throws SQLException;

}
