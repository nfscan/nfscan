package cc.nfscan.server.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Class that integrates with AWS S3 service.
 * This service is in charge of upload objects to a bucket
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
@Component
public class TaxReceiptS3Upload extends S3Upload {

    /**
     * AWS S3 bucket name
     */
    @Value(value = "${aws.s3.bucketname}")
    private String awsS3BucketName;

    /**
     * Uploads a new object to the specified Amazon S3 bucket. This file won't be accessible publicly
     *
     * @param key  object key
     * @param file file you want to upload
     * @throws AmazonClientException  If any errors are encountered in the client while making the
     *                                request or handling the response.
     * @throws AmazonServiceException If any errors occurred in Amazon S3 while processing the
     *                                request.
     */
    public void startUpload(String key, File file) throws AmazonClientException, AmazonServiceException {
        super.startUpload(awsS3BucketName, key, file);
    }

    /**
     * Uploads a new object to the specified Amazon S3 bucket.
     *
     * @param key          object key
     * @param file         file you want to upload
     * @param willBePublic whether or not this file should be accessible publicly
     * @throws AmazonClientException  If any errors are encountered in the client while making the
     *                                request or handling the response.
     * @throws AmazonServiceException If any errors occurred in Amazon S3 while processing the
     *                                request.
     */
    public void startUpload(String key, File file, boolean willBePublic) throws AmazonClientException, AmazonServiceException {
        super.startUpload(awsS3BucketName, key, file, willBePublic);
    }
}
