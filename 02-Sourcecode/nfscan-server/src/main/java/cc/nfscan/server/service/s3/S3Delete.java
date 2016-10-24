package cc.nfscan.server.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
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
     * AmazonS3 instance
     */
    @Autowired
    private AmazonS3 amazonS3;

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
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, key);
        amazonS3.deleteObject(deleteObjectRequest);
    }
}
