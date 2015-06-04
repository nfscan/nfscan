package cc.nfscan.server.controller.response;

import cc.nfscan.server.domain.OCRTransaction;

/**
 * JSON process status model class
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public class ResultProcessStatusResponse extends ResultResponse {

    /**
     * OCR transaction object
     */
    private OCRTransaction ocrTransaction;

    /**
     * Default constructor
     *
     * @param success        whether we have processed this request successfully or not
     * @param ocrTransaction a OCR transaction
     */
    public ResultProcessStatusResponse(boolean success, OCRTransaction ocrTransaction) {
        super(success);
        this.ocrTransaction = ocrTransaction;
    }

    /**
     * Gets OCR transaction object
     *
     * @return OCRTransaction
     * @see OCRTransaction
     */
    public OCRTransaction getOcrTransaction() {
        return ocrTransaction;
    }

    /**
     * Sets the OCR transaction object
     *
     * @param ocrTransaction
     * @see OCRTransaction
     */
    public void setOcrTransaction(OCRTransaction ocrTransaction) {
        this.ocrTransaction = ocrTransaction;
    }

    /**
     * Returns a string representation of the object
     *
     * @return a string.
     */
    @Override
    public String toString() {
        return "ResultProcessStatusResponse{" +
                "ocrTransaction=" + ocrTransaction +
                "} " + super.toString();
    }
}
