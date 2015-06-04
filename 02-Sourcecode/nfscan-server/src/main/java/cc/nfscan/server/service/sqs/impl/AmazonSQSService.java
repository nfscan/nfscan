package cc.nfscan.server.service.sqs.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.InvalidMessageContentsException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that integrates with AWS SQS service.
 * This service is in charge of sending messages to AWS.
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
class AmazonSQSService {

    /**
     * AmazonSQSClient instance
     */
    @Autowired
    private AmazonSQSClient amazonSQSClient;

    /**
     * A Map&lt;String,String&gt; used for caching queue Urls
     */
    private Map<String, String> queueUrlMap;

    /**
     * Default constructor
     */
    public AmazonSQSService() {
        queueUrlMap = new HashMap<>();
    }

    /**
     * <p>
     * Delivers a message to the specified queue. With Amazon SQS, you now have the ability to send large payload messages that are up to 256KB (262,144 bytes) in size. To send large payloads, you must use an AWS SDK that supports SigV4 signing. To verify whether SigV4 is supported for an AWS SDK, check the SDK release notes.
     * </p>
     * IMPORTANT: The following list shows the characters (in Unicode) allowed in your message, according to the W3C XML specification. For more information, go to http://www.w3.org/TR/REC-xml/#charsets If you send any characters not included in the list, your request will be rejected. #x9 | #xA | #xD | [#x20 to #xD7FF] | [#xE000 to #xFFFD] | [#x10000 to #x10FFFF]
     *
     * @param queue   The name of the Amazon SQS queue to take action on.
     * @param message The message to send. String maximum 256 KB in size. For a list of allowed characters, see the preceding important note.
     * @throws InvalidMessageContentsException
     *
     * @throws UnsupportedOperationException
     * @throws AmazonClientException         If any internal errors are encountered inside the client while attempting to make the request or handle the response. For example if a network connection is not available.
     * @throws AmazonServiceException        If an error response is returned by AmazonSQS indicating either a problem with the data in the request, or a server side issue.
     */
    public void sendMessage(String queue, String message) throws UnsupportedOperationException, AmazonClientException, AmazonServiceException {
        /*
        *  Check whether we already have the queue URL or not.
        *  I've decided to cache the queueUrl on application level instead of having the URL hard-coded on properties file to make it easier to migrate it if needed
        */

        if (!queueUrlMap.containsKey(queue)) {
            queueUrlMap.put(queue, amazonSQSClient.getQueueUrl(queue).getQueueUrl());
        }
        amazonSQSClient.sendMessage(queueUrlMap.get(queue), message);
    }
}