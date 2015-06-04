package cc.nfscan.server.dao;

import cc.nfscan.server.domain.TaxReceiptArchive;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 * TaxReceiptArchive Data Access Object
 *
 * @author Marcelo Carlos Agostinho Junior <a href="http://github.com/magostinhojr">@magostinhojr</a>
 */
@Repository
public class TaxReceiptArchiveDAO extends AbstractDAO<TaxReceiptArchive> {

    /**
     * Specifies which Entity we are going to provide this functionality
     *
     * @return a class object
     */
    @Override
    public Class<TaxReceiptArchive> getType() {
        return TaxReceiptArchive.class;
    }


    /**
     * Find object by id
     *
     * @param entity object we want to find
     * @return a entity object or null
     * @throws DataAccessException
     */
    @Override
    public TaxReceiptArchive findById(TaxReceiptArchive entity) throws DataAccessException {
        if (!entity.getId().trim().isEmpty()) {
            return dynamoDBMapper.load(getType(), entity.getId());
        } else {
            return null;
        }
    }
}

