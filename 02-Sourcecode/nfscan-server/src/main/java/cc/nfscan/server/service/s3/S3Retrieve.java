package cc.nfscan.server.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedInputStream;

/**
 * Class that integrates with AWS S3 service.
 * This service is in charge of retrieve objects from a bucket
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
abstract class S3Retrieve {

    /**
     * AmazonS3 instance
     */
    @Autowired
    private AmazonS3 amazonS3;

    /**
     * Gets the input stream containing the contents of this object.
     * <p/>
     * <p>
     * <b>Note</b>: The method is a simple getter and does not actually create a
     * stream. If you retrieve an S3Object, you should close this input stream
     * as soon as possible, because the object contents aren't buffered in
     * memory and stream directly from Amazon S3. Further, failure to close this
     * stream can cause the request pool to become blocked.
     * </p>
     *
     * @param bucketName bucket name
     * @param key        object key
     * @return An input stream containing the contents of this object.
     */
    protected BufferedInputStream startDownload(String bucketName, String key) {
        S3Object object = amazonS3.getObject(bucketName, key);
        S3ObjectInputStream inputStream = object.getObjectContent();
        return new BufferedInputStream(inputStream);
    }
}
