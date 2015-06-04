package cc.nfscan.server.service.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;

/**
 * Class that integrates with AWS S3 service.
 * This service is in charge of retrieve objects from a bucket for TaxReceipt purposes
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */

@Component
public class TaxReceiptS3Retrieve extends S3Retrieve {

    /**
     * AWS S3 bucket name
     */
    @Value(value = "${aws.s3.bucketname}")
    private String awsS3BucketName;

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
     * @param key object key
     * @return An input stream containing the contents of this object.
     */
    public BufferedInputStream startDownload(String key) {
        return super.startDownload(awsS3BucketName, key);
    }
}
