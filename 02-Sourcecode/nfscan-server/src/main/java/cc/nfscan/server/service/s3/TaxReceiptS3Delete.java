package cc.nfscan.server.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class that integrates with AWS S3 service.
 * This service is in charge of delete objects on a bucket for TaxReceipt purposes
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
@Component
public class TaxReceiptS3Delete extends S3Delete {

    /**
     * AWS S3 bucket name
     */
    @Value(value = "${aws.s3.bucketname}")
    private String awsS3BucketName;

    /**
     * Deletes the specified object in the specified bucket
     *
     * @param key        object key
     * @throws AmazonClientException  If any errors are encountered in the client while making the
     *                                request or handling the response.
     * @throws AmazonServiceException If any errors occurred in Amazon S3 while processing the
     *                                request.
     */
    public void startDelete(String key) throws AmazonClientException, AmazonServiceException {
        super.startDelete(awsS3BucketName, key);
    }

}
