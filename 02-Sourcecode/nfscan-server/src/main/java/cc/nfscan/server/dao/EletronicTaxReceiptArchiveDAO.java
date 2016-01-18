package cc.nfscan.server.dao;

import cc.nfscan.server.domain.ElectronicTaxReceiptArchive;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;


/**
 * ElectronicTaxReceipt Archive Data Access Object
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
@Repository
public class EletronicTaxReceiptArchiveDAO extends AbstractDAO<ElectronicTaxReceiptArchive> {

    /**
     * Specifies which Entity we are going to provide this functionality
     *
     * @return a class object
     */
    @Override
    public Class<ElectronicTaxReceiptArchive> getType() {
        return ElectronicTaxReceiptArchive.class;
    }


    /**
     * Find object by id
     *
     * @param entity object we want to find
     * @return a entity object or null
     * @throws DataAccessException
     */
    @Override
    public ElectronicTaxReceiptArchive findById(ElectronicTaxReceiptArchive entity) throws DataAccessException {
        if (!entity.getId().trim().isEmpty()) {
            return dynamoDBMapper.load(getType(), entity.getId());
        } else {
            return null;
        }
    }

}