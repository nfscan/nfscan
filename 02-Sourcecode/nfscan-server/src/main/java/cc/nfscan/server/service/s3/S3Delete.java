package cc.nfscan.server.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class that integrates with AWS S3 service.
 * This service is in charge of delete objects on a bucket
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
abstract class S3Delete {

    /**
     * BasicAWSCredentials instance
     */
    @Autowired
    private BasicAWSCredentials awsCredentials;

    /**
     * Deletes the specified object in the specified bucket
     *
     * @param bucketName bucket name
     * @param key        object key
     * @throws AmazonClientException  If any errors are encountered in the client while making the
     *                                request or handling the response.
     * @throws AmazonServiceException If any errors occurred in Amazon S3 while processing the
     *                                request.
     */
    protected void startDelete(String bucketName, String key) throws AmazonClientException, AmazonServiceException {
        AmazonS3 s3 = new AmazonS3Client(awsCredentials);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, key);
        s3.deleteObject(deleteObjectRequest);
    }
}
