package cc.nfscan.server.controller.response;

/**
 * JSON Authentication model class
 *
 * @author Marcelo Carlos Agostinho Junior <a href="http://github.com/magostinhojr">@magostinhojr</a>
 */
public class ResultProcessAuthResponse extends ResultResponse {

    /**
     * The transaction Id on database
     */
    private String transactionId;

    /**
     * The server generated signature
     */
    private String signature;

    /**
     * Default constructor
     *
     * @param success       whether we have processed this request successfully or not
     * @param transactionId the transaction Id on database
     * @param signature     the server generated signature
     */
    public ResultProcessAuthResponse(boolean success, String transactionId, String signature) {
        super(success);
        this.transactionId = transactionId;
        this.signature = signature;
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
     * Gets the server generated signature
     *
     * @return a string
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the server generated signature
     *
     * @param signature server generated signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * Returns a string representation of the object
     *
     * @return a string.
     */
    @Override
    public String toString() {
        return "ResultProcessStartedResponse{" +
                "transactionId=" + transactionId +
                ", signature=" + signature +
                "} " + super.toString();
    }
}