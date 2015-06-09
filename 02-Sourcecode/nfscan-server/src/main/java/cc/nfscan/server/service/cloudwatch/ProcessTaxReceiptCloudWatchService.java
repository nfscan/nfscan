package cc.nfscan.server.service.cloudwatch;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Class that integrates with AWS CloudWatch service for the tax receipt OCR purposes
 * This service is in charge of send/receive metrics which can be used to take decisions on our infrastructure if needed
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
@Service
public class ProcessTaxReceiptCloudWatchService extends AmazonCloudWatchService {

    /**
     * Default CloudWatch namespace
     */
    private static final String namespace = "Loducca/NFScan";

    /**
     * Metric name for the number of messages sent to a queue
     */
    @Value(value = "${aws.cloudwatch.ocr.process.queue.in}")
    public String messagesSentMetric;

    /**
     * Metric name for the how long the OCR process took to process that image
     */
    @Value(value = "${aws.cloudwatch.ocr.process.queue.elapsedtime}")
    public String elapsedTimeToProcess;

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
     * @param metricName  The name of the metric.
     * @param metricValue The value for the metric.
     * @throws AmazonClientException  If any internal errors are encountered inside the client while
     *                                attempting to make the request or handle the response.  For example
     *                                if a network connection is not available.
     * @throws AmazonServiceException If an error response is returned by AmazonCloudWatch indicating
     *                                either a problem with the data in the request, or a server side issue.
     */
    public void putMetricData(String metricName, Double metricValue) throws AmazonClientException, AmazonServiceException {
        this.putMetricData(namespace, metricName, metricValue);
    }

    public String getMessagesSentMetric() {
        return messagesSentMetric;
    }

    public void setMessagesSentMetric(String messagesSentMetric) {
        this.messagesSentMetric = messagesSentMetric;
    }

    public String getElapsedTimeToProcess() {
        return elapsedTimeToProcess;
    }

    public void setElapsedTimeToProcess(String elapsedTimeToProcess) {
        this.elapsedTimeToProcess = elapsedTimeToProcess;
    }
}
