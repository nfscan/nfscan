package cc.nfscan.server.service.cloudwatch;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Class that integrates with AWS CloudWatch service.
 * This service is in charge of send/receive metrics which can be used to take decisions on our infrastructure if needed
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
class AmazonCloudWatchService {

    /**
     * AWS CloudWatch client instance
     */
    @Autowired
    private AmazonCloudWatchClient amazonCloudWatchClient;

    /**
     * <p>
     * Publishes metric data points to Amazon CloudWatch. Amazon Cloudwatch
     * associates the data points with the specified metric. If the specified
     * metric does not exist, Amazon CloudWatch creates the metric.
     * </p>
     * <p>
     * <b>IMPORTANT:</b> Although the Value parameter accepts numbers of
     * type Double, Amazon CloudWatch truncates values with very large
     * exponents. Values with base-10 exponents greater than 126 (1 x 10^126)
     * are truncated. Likewise, values with base-10 exponents less than -130
     * (1 x 10^-130) are also truncated.
     * </p>
     *
     * @param namespace   The namespace for the metric data.
     * @param metricName  The name of the metric.
     * @param metricValue The value for the metric.
     * @throws AmazonClientException  If any internal errors are encountered inside the client while
     *                                attempting to make the request or handle the response.  For example
     *                                if a network connection is not available.
     * @throws AmazonServiceException If an error response is returned by AmazonCloudWatch indicating
     *                                either a problem with the data in the request, or a server side issue.
     */
    protected void putMetricData(String namespace, String metricName, Double metricValue) throws AmazonClientException, AmazonServiceException {
        PutMetricDataRequest putMetricDataRequest = new PutMetricDataRequest();
        putMetricDataRequest.withNamespace(namespace).
                withMetricData(
                        new MetricDatum().
                                withMetricName(metricName).
                                withValue(metricValue).
                                withTimestamp(new Date())
                );
        amazonCloudWatchClient.putMetricData(putMetricDataRequest);
    }

}
