package cc.nfscan.server.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

/**
 * Entity class that represents Electronic TaxReceipt Archive (CF-e and SAT) on database
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
@DynamoDBTable(tableName = "NFSCAN-ELECTRONICTAXRECEIPTARCHIVE")
public class ElectronicTaxReceiptArchive implements IDomain {

    private static final long serialVersionUID = 1L;

    /**
     * identifier
     */
    private String id;

    /**
     * access key that contains all required receipt information to donate
     */
    private String accessKey;

    /**
     * total
     */
    private Double total;

    /**
     * dateInsertion of this object
     */
    private Date dateInsertion;


    public ElectronicTaxReceiptArchive() {
    }

    @DynamoDBHashKey(attributeName = "electronictaxreceiptarchive_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "accessKey")
    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @DynamoDBAttribute(attributeName = "total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @DynamoDBAttribute(attributeName = "dateInsertion")
    public Date getDateInsertion() {
        return dateInsertion;
    }

    public void setDateInsertion(Date dateInsertion) {
        this.dateInsertion = dateInsertion;
    }

    @Override
    public String toString() {
        return "ElectronicTaxReceiptArchive{" +
                "id='" + id + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", dateInsertion=" + dateInsertion +
                '}';
    }
}