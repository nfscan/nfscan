package cc.nfscan.server.service.sqs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Base JSON message model for queue out
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public class ProcessOutQueueModel {

    /**
     * transaction Id on database
     */
    @SerializedName("transaction_id")
    private String transactionId;

    /**
     * elapsed time to process it
     */
    private double elapsedTime;

    /**
     * cnpj
     */
    private String cnpj;

    /**
     * date
     */
    private String date;

    /**
     * COO
     */
    private String coo;

    /**
     * total
     */
    private Double total;

    /**
     * Default constructor
     */
    public ProcessOutQueueModel() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoo() {
        return coo;
    }

    public void setCoo(String coo) {
        this.coo = coo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ProcessOutQueueModel{" +
                "transactionId=" + transactionId +
                ", elapsedTime=" + elapsedTime +
                ", cnpj='" + cnpj + '\'' +
                ", date=" + date +
                ", coo=" + coo +
                ", total=" + total +
                '}';
    }
}
