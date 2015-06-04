package cc.nfscan.server.service.sqs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Base JSON message model for queue in
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public class ProcessInQueueModel {

    /**
     * transaction Id on database
     */
    @SerializedName("transaction_id")
    private String transactionId;

    /**
     * file path on AWS S3
     */
    @SerializedName("object")
    private String s3Object;

    /**
     * Default constructor
     *
     * @param transactionId the transaction Id on database
     * @param s3Object      file path on AWS S3
     */
    public ProcessInQueueModel(String transactionId, String s3Object) {
        this.transactionId = transactionId;
        this.s3Object = s3Object;
    }

    /**
     * Gets the transaction Id on database
     *
     * @return a string
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the transaction Id
     *
     * @param transactionId the transaction Id
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets the file path on AWS S3
     *
     * @return a string
     */
    public String getS3Object() {
        return s3Object;
    }

    /**
     * Sets the file path on AWS S3
     *
     * @param s3Object the file path on AWS S3
     */
    public void setS3Object(String s3Object) {
        this.s3Object = s3Object;
    }

    /**
     * Returns a string representation of the object
     *
     * @return a string.
     */
    @Override
    public String toString() {
        return "ProcessInQueueModel{" +
                "transactionId=" + transactionId +
                ", s3Object='" + s3Object + '\'' +
                '}';
    }
}
