package cryptotracker.dao;

import cryptotracker.domain.CryptoBatch;
import cryptotracker.domain.Cryptocurrency;
import java.sql.SQLException;
import java.util.List;

/** The DAO interface for communicating with t
 * 
 * 
 */

public interface CryptoBatchDao extends Dao<CryptoBatch, Integer> {

    /** Deletes a CryptoBatch from the database, using a unique id.
     *
     * @param id The id of the CryptoBatch that will be deleted.
     * @throws java.sql.SQLException
     */
    @Override
    void delete(Integer id) throws SQLException;

    /** Finds all instances of CryptoBatch stored in the database.
     *
     * @return A list containing every CryptoBatch found in the database, or an empty list if nothing was found.
     * @throws java.sql.SQLException
     */
    @Override
    List<CryptoBatch> findAll() throws SQLException;

    /** Finds all instances of CryptoBatch that belong to a speficied cryptocurrency.
     *
     * @param crypto The cryptocurrency whose CryptoBatches will be returned.
     * @return A list containing every CryptoBatch of the specified cryptocurrency, or an empty list if nothing was found.
     * @throws java.sql.SQLException
     */
    List<CryptoBatch> findAllFromCryptocurrency(Cryptocurrency crypto) throws SQLException;

    /** Finds a CryptoBatch from the database, using a unique id.
     *
     * @param id The id specifying the wanted CryptoBatch.
     * @return The found cryptoBatch, or null if nothing was found.
     * @throws java.sql.SQLException
     */
    @Override
    CryptoBatch findOneWithId(Integer id) throws SQLException;

    /** Adds a CryptoBatch to the database, linking it to a cryptocurrency.
     *
     * @param batch The CryptoBatch that will be added to the database.
     * @param crypto The cryptocurrency for which the new CryptoBatch will be added.
     * @return The added CryptoBatch, or null if nothing was added.
     * @throws java.sql.SQLException
     */
    CryptoBatch save(CryptoBatch batch, Cryptocurrency crypto) throws SQLException;

}
