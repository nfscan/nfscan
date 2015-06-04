package cc.nfscan.server.dao;

import cc.nfscan.server.domain.IDomain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Base Data Access Object
 *
 * @param <T> Class we want to provide DAO functionality
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public abstract class AbstractDAO<T extends IDomain> {

    /**
     * AmazonDynamoDBClient reference to this object
     */
    @Autowired
    protected AmazonDynamoDBClient amazonDynamoDBClient;

    /**
     * DynamoDBMapper reference to this object
     */
    @Autowired
    protected DynamoDBMapper dynamoDBMapper;


    /**
     * Find all rows in table given the entity object
     *
     * @return a list of entities found
     * @throws DataAccessException
     */
    public List<T> findAll() throws DataAccessException {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING);
        PaginatedScanList<T> paginatedScanList = dynamoDBMapper.scan(getType(), dynamoDBScanExpression, config);
        paginatedScanList.loadAllResults();

        List<T> list = new ArrayList<T>(paginatedScanList.size());

        Iterator<T> iterator = paginatedScanList.iterator();
        while (iterator.hasNext()) {
            T element = iterator.next();
            list.add(element);
        }

        return list;
    }

    /**
     * Saves an entity to database
     *
     * @param entity entity object
     * @throws DataAccessException
     */
    public void save(T entity) throws DataAccessException {
        dynamoDBMapper.save(entity);
    }

    /**
     * Removes an entity to database
     *
     * @param entity entity object
     * @throws DataAccessException
     */
    public void remove(T entity) throws DataAccessException {
        dynamoDBMapper.delete(entity);
    }

    /**
     * Counts all rows in this specific table.
     * Use it carefully since it loads all results from DynamoDB. To be honest there are only
     * a few situations where this could be considered a wise solution.
     *
     * @return a amount of rows found
     * @throws DataAccessException
     */
    public int count() throws DataAccessException {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING);
        PaginatedScanList<T> paginatedScanList = dynamoDBMapper.scan(getType(), dynamoDBScanExpression, config);
        paginatedScanList.loadAllResults();
        return paginatedScanList.size();
    }

    /**
     * Specifies which Entity we are going to provide this functionality
     *
     * @return a class object
     */
    public abstract Class<T> getType();

    /**
     * Find object by id
     *
     * @param entity object we want to find
     * @return a entity object or null
     * @throws DataAccessException
     */
    public abstract T findById(T entity) throws DataAccessException;

}
