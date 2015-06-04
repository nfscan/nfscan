package cc.nfscan.server.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Class that integrates with AWS S3 service.
 * This service is in charge of upload objects to a bucket
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
abstract class S3Upload {

    /**
     * BasicAWSCredentials instance
     */
    @Autowired
    private BasicAWSCredentials awsCredentials;

    /**
     * Uploads a new object to the specified Amazon S3 bucket.
     *
     * @param bucketName   bucket name
     * @param key          object key
     * @param file         file you want to upload
     * @param willBePublic whether or not this file should be accessible publicly
     * @throws AmazonClientException  If any errors are encountered in the client while making the
     *                                request or handling the response.
     * @throws AmazonServiceException If any errors occurred in Amazon S3 while processing the
     *                                request.
     */
    protected void startUpload(String bucketName, String key, File file, boolean willBePublic) throws AmazonClientException, AmazonServiceException {
        AmazonS3 s3 = new AmazonS3Client(awsCredentials);
        PutObjectRequest putObj = new PutObjectRequest(bucketName, key, file);
        if (willBePublic) {
            putObj.setCannedAcl(CannedAccessControlList.PublicRead);
        }
        s3.putObject(putObj);
    }

    /**
     * Uploads a new object to the specified Amazon S3 bucket. This file won't be accessible publicly
     *
     * @param bucketName bucket name
     * @param key        object key
     * @param file       file you want to upload
     * @throws AmazonClientException  If any errors are encountered in the client while making the
     *                                request or handling the response.
     * @throws AmazonServiceException If any errors occurred in Amazon S3 while processing the
     *                                request.
     */
    protected void startUpload(String bucketName, String key, File file) throws AmazonClientException, AmazonServiceException {
        this.startUpload(bucketName, key, file, false);
    }
}
