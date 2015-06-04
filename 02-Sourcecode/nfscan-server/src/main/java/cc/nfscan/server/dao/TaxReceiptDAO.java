package cc.nfscan.server.dao;

import cc.nfscan.server.domain.TaxReceipt;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;


/**
 * TaxReceipt Data Access Object
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
@Repository
public class TaxReceiptDAO extends AbstractDAO<TaxReceipt> {

    /**
     * Specifies which Entity we are going to provide this functionality
     *
     * @return a class object
     */
    @Override
    public Class<TaxReceipt> getType() {
        return TaxReceipt.class;
    }


    /**
     * Find object by id
     *
     * @param entity object we want to find
     * @return a entity object or null
     * @throws DataAccessException
     */
    @Override
    public TaxReceipt findById(TaxReceipt entity) throws DataAccessException {
        if (!entity.getId().trim().isEmpty()) {
            return dynamoDBMapper.load(getType(), entity.getId());
        } else {
            return null;
        }
    }

}
