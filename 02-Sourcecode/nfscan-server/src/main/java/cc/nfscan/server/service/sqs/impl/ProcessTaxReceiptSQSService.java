package cc.nfscan.server.service.sqs.impl;

import cc.nfscan.server.service.sqs.model.ProcessInQueueModel;
import cc.nfscan.server.utils.Constants;
import com.amazonaws.AmazonClientException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Class that integrates with AWS SQS service.
 * This service is in charge of sending to AWS for TaxReceipt purposes
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
@Service
public class ProcessTaxReceiptSQSService extends AmazonSQSService {

    /**
     * queue in we going to send a message
     */
    @Value(value = "${aws.sqs.convert.queue.in}")
    private String queueName;

    /**
     * <p>
     * Delivers a message to the specified queue. With Amazon SQS, you now have the ability to send large payload messages that are up to 256KB (262,144 bytes) in size. To send large payloads, you must use an AWS SDK that supports SigV4 signing. To verify whether SigV4 is supported for an AWS SDK, check the SDK release notes.
     * </p>
     * IMPORTANT: The following list shows the characters (in Unicode) allowed in your message, according to the W3C XML specification. For more information, go to http://www.w3.org/TR/REC-xml/#charsets If you send any characters not included in the list, your request will be rejected. #x9 | #xA | #xD | [#x20 to #xD7FF] | [#xE000 to #xFFFD] | [#x10000 to #x10FFFF]
     *
     * @param message The message to send.
     * @throws com.amazonaws.services.sqs.model.InvalidMessageContentsException
     *
     * @throws UnsupportedOperationException
     * @throws AmazonClientException         If any internal errors are encountered inside the client while attempting to make the request or handle the response. For example if a network connection is not available.
     * @throws com.amazonaws.AmazonServiceException
     *                                       If an error response is returned by AmazonSQS indicating either a problem with the data in the request, or a server side issue.
     * @see ProcessInQueueModel
     */
    public void sendMessage(ProcessInQueueModel message) throws UnsupportedOperationException, AmazonClientException {
        Gson gson = new GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create();
        super.sendMessage(queueName, gson.toJson(message));
    }
}
