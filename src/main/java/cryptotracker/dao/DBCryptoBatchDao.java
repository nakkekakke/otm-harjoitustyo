package cryptotracker.dao;

import cryptotracker.domain.CryptoBatch;
import cryptotracker.domain.Cryptocurrency;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/** The class for handling CryptoBatches between the service class and the database.
 * 
 */
public class DBCryptoBatchDao implements CryptoBatchDao {
    
    private final Database database;
    private final DBCryptocurrencyDao cryptoDao;

    public DBCryptoBatchDao(Database database, DBCryptocurrencyDao cryptoDao) {
        this.database = database;
        this.cryptoDao = cryptoDao;
    }
    
    /** Finds a CryptoBatch from the database, using a unique id.
     * 
     * @param id The id specifying the wanted CryptoBatch.
     * @return The found cryptoBatch, or null if nothing was found.
     * @throws java.sql.SQLException 
     */    
    @Override
    public CryptoBatch findOneWithId(Integer id) throws SQLException {
        ResultSet rs = null;
        Cryptocurrency crypto = null;
        try (Connection conn = database.getConnection(); 
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM CryptoBatch WHERE CryptoBatch.id = ?")) {
            
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                crypto = cryptoDao.findOneWithId(rs.getInt("cryptocurrency_id"));
            }
            if (crypto == null) {
                return null;
            }
            
        }
        
        return new CryptoBatch(id, rs.getInt("amount"), rs.getInt("totalPaid"), LocalDate.parse(rs.getString("date")), crypto);
    }
    
    /** Finds all instances of CryptoBatch stored in the database.
     * 
     * @return A list containing every CryptoBatch found in the database, or an empty list if nothing was found.
     * @throws java.sql.SQLException
     */ 
    @Override
    public List<CryptoBatch> findAll() throws SQLException {
        List<CryptoBatch> batches = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM CryptoBatch");
             ResultSet rs = stat.executeQuery()) {
            
            while (rs.next()) {
                Cryptocurrency crypto = cryptoDao.findOneWithId(rs.getInt("cryptocurrency_id"));
                
                if (crypto != null) {
                    batches.add(new CryptoBatch(rs.getInt("id"), rs.getInt("amount"), rs.getInt("totalPaid"), LocalDate.parse(rs.getString("date")), crypto));
                }
            }
        }
        
        return batches;
    }
    
    /** Finds all instances of CryptoBatch that belong to a speficied cryptocurrency.
     * 
     * @param crypto The cryptocurrency whose CryptoBatches will be returned.
     * @return A list containing every CryptoBatch of the specified cryptocurrency, or an empty list if nothing was found.
     * @throws java.sql.SQLException 
     */
    @Override
    public List<CryptoBatch> findAllFromCryptocurrency(Cryptocurrency crypto) throws SQLException {
        List<CryptoBatch> batches = new ArrayList<>();
        if (crypto == null) {
            return batches;
        }
        
        try (Connection conn = database.getConnection();
            PreparedStatement stat = 
                    conn.prepareStatement("SELECT * FROM CryptoBatch WHERE CryptoBatch.cryptocurrency_id = ?")) {
            
            stat.setInt(1, crypto.getId());
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    Cryptocurrency c = cryptoDao.findOneWithId(rs.getInt("cryptocurrency_id"));
                    
                    if (c != null) {
                        batches.add(new CryptoBatch(rs.getInt("id"), rs.getInt("amount"), rs.getInt("totalPaid"), LocalDate.parse(rs.getString("date")), c));
                    }
                }
            }
        }
        
        return batches;
    }
    
    
    /** Finds a specified CryptoBatch belonging to a specified cryptocurrency.
     * 
     * @param batch The CryptoBatch to be found.
     * @param crypto The cryptocurrency from which the CryptoBatch will be searched.
     * 
     * @see 
     * 
     * @return The found CryptoBatch, or null if nothing was found.
     * @throws java.sql.SQLException 
     */
    public CryptoBatch findOneFromCryptocurrency(CryptoBatch batch, Cryptocurrency crypto) throws SQLException {
        for (CryptoBatch b : findAllFromCryptocurrency(crypto)) {
            if (b.equals(batch)) {
                return b;
            }
        }
        
        return null;
    }
    
    /** Adds a CryptoBatch to the database, linking it to a cryptocurrency.
     * 
     * @param batch The CryptoBatch that will be added to the database.
     * @param crypto The cryptocurrency for which the new CryptoBatch will be added.
     * @return The added CryptoBatch, or null if nothing was added.
     * @throws java.sql.SQLException
     */ 
    @Override
    public CryptoBatch save(CryptoBatch batch, Cryptocurrency crypto) throws SQLException {
        
        if (batch == null || crypto == null) {
            return null;
        }
        
        try (Connection conn = database.getConnection();
             PreparedStatement stat = conn.prepareStatement("INSERT INTO CryptoBatch (amount, totalPaid, date, cryptocurrency_id) VALUES (?, ?, ?, ?)")) {
            
            stat.setInt(1, batch.getAmount());
            stat.setInt(2, batch.getTotalPaid());
            stat.setString(3, batch.getDate().toString());
            stat.setInt(4, crypto.getId());
            stat.executeUpdate();
        
        }
        
        return batch;
    }

    /**  Deletes a CryptoBatch from the database, using a unique id.
     * 
     *   @param id The id of the CryptoBatch that will be deleted.
     *   @throws java.sql.SQLException
     */ 
    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection conn = database.getConnection();
             PreparedStatement stat = 
                     conn.prepareStatement("DELETE FROM CryptoBatch WHERE CryptoBatch.id = ?")) {
            stat.setInt(1, id);
            
            stat.executeUpdate();
            
        }
    }
}
