package cc.nfscan.server.controller.fe;

import cc.nfscan.server.domain.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pauloalmeida on 1/17/16.
 */
@Ignore
class BaseDatabaseControllerTest extends BaseControllerTest {

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


    @Override
    protected void init() {
        super.init();

        createTable(OCRTransaction.class);
        createTable(TaxReceipt.class);
        createTable(TaxReceiptArchive.class);
        createTable(ElectronicTaxReceipt.class);
        createTable(ElectronicTaxReceiptArchive.class);
    }

    public void createTable(Class<? extends IDomain> domain){
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(domain);
        tableRequest = tableRequest.withProvisionedThroughput(new ProvisionedThroughput(5L,5L));

        //check whether or not we need to add a provisioning throughput value for GSI
        for (Method method : domain.getMethods()) {
            if(method.isAnnotationPresent(DynamoDBIndexHashKey.class)){
                String tempGSI = method.getAnnotation(DynamoDBIndexHashKey.class).globalSecondaryIndexName();
                for (GlobalSecondaryIndex globalSecondaryIndex : tableRequest.getGlobalSecondaryIndexes()) {
                    if(globalSecondaryIndex.getIndexName().equals(tempGSI)){
                        globalSecondaryIndex.setProvisionedThroughput(new ProvisionedThroughput(5L,5L));
                    }
                }
            }
        }

        amazonDynamoDBClient.createTable(tableRequest);
    }

    protected void clean(){
        String prefix = "DES-";
        amazonDynamoDBClient.deleteTable(new DeleteTableRequest(prefix.concat(extractTableName(OCRTransaction.class))));
        amazonDynamoDBClient.deleteTable(new DeleteTableRequest(prefix.concat(extractTableName(TaxReceipt.class))));
        amazonDynamoDBClient.deleteTable(new DeleteTableRequest(prefix.concat(extractTableName(TaxReceiptArchive.class))));
        amazonDynamoDBClient.deleteTable(new DeleteTableRequest(prefix.concat(extractTableName(ElectronicTaxReceipt.class))));
        amazonDynamoDBClient.deleteTable(new DeleteTableRequest(prefix.concat(extractTableName(ElectronicTaxReceiptArchive.class))));
    }

    private String extractTableName(Class<? extends IDomain> domain){
        return domain.getAnnotation(DynamoDBTable.class).tableName();
    }



}
