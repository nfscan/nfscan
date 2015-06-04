package cc.nfscan.server.service.sqs.listener;

import cc.nfscan.server.dao.OCRTransactionDAO;
import cc.nfscan.server.domain.OCRTransaction;
import cc.nfscan.server.service.cloudwatch.ProcessTaxReceiptCloudWatchService;
import cc.nfscan.server.service.sqs.model.ProcessOutQueueModel;
import cc.nfscan.server.utils.Constants;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * Class that integrates with AWS SQS service using the JMS specification provided in the
 * amazon-sqs-java-messaging-lib library.
 * </p>
 * This service is in charge of receiving to AWS for TaxReceipt purposes
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */

@Component
public class SQSConsumerListener implements MessageListener {

    /**
     * JMS SQS Connection
     */
    @Autowired
    private SQSConnection sqsConnection;

    /**
     * queue out we going to receive a message
     */
    @Value(value = "${aws.sqs.convert.queue.out}")
    private String queueName;

    /**
     * OCRTransaction DAO object
     */
    @Autowired
    protected OCRTransactionDAO ocrTransactionDAO;

    /**
     * ProcessTaxReceiptCloudWatchService object
     */
    @Autowired
    private ProcessTaxReceiptCloudWatchService processTaxReceiptCloudWatchService;


    /**
     * Default logger
     */
    private Logger logger = Logger.getLogger(this.getClass());


    /**
     * Set up needed resources for reading messages out of a SQS queue
     */
    @PostConstruct
    public void setUp() {
        try {
            Session session = sqsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(this);
            // Start receiving incoming messages.
            sqsConnection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called every time we receive a message from SQS
     *
     * @param message a JMS message
     *
     * @see Message
     */
    @Override
    public void onMessage(Message message) {
        try {
            if (message != null) {
                String messageStr = ((TextMessage) message).getText();
                logger.info(String.format("Received: %s", messageStr));

                Gson gson = new GsonBuilder().create();
                ProcessOutQueueModel processOutQueueModel = gson.fromJson(messageStr, ProcessOutQueueModel.class);

                OCRTransaction ocrTransaction = ocrTransactionDAO.findById(new OCRTransaction(processOutQueueModel.getTransactionId()));
                Assert.notNull(ocrTransaction);

                if (processOutQueueModel.getCnpj() != null) {
                    if (processOutQueueModel.getCnpj().length() > 14) {
                        ocrTransaction.setCnpj(processOutQueueModel.getCnpj().substring(0, 14));
                    } else {
                        ocrTransaction.setCnpj(processOutQueueModel.getCnpj());
                    }
                }

                if (processOutQueueModel.getCoo() != null) {
                    if (processOutQueueModel.getCoo().length() > 6) {
                        ocrTransaction.setCoo(processOutQueueModel.getCoo().substring(0, 6));
                    } else {
                        ocrTransaction.setCoo(processOutQueueModel.getCoo());
                    }
                }

                if (processOutQueueModel.getDate() != null) {
                    Date date;
                    SimpleDateFormat df;
                    try {
                        df = new SimpleDateFormat(Constants.DATE_FORMAT);
                        date = df.parse(processOutQueueModel.getDate());
                    } catch (ParseException ex) {
                        logger.info(Constants.DATE_FORMAT + " format wasn't the right one for " + processOutQueueModel.getDate());
                        try {
                            df = new SimpleDateFormat("dd/MMyyyy");
                            date = df.parse(processOutQueueModel.getDate());
                        } catch (ParseException ex2) {
                            logger.info("dd/MMyyyy" + " format wasn't the right one for " + processOutQueueModel.getDate());
                            try {
                                df = new SimpleDateFormat("ddMM/yyyy");
                                date = df.parse(processOutQueueModel.getDate());
                            } catch (ParseException ex3) {
                                logger.info("ddMM/yyyy" + " format wasn't the right one for " + processOutQueueModel.getDate());
                                try {
                                    df = new SimpleDateFormat("ddMMyyyy");
                                    date = df.parse(processOutQueueModel.getDate());
                                } catch (ParseException ex4) {
                                    logger.info("ddMMyyyy" + " format wasn't the right one for " + processOutQueueModel.getDate());
                                    date = null;
                                }
                            }
                        }
                    }
                    ocrTransaction.setDate(date);
                }
                try {
                    ocrTransaction.setTotal(processOutQueueModel.getTotal());
                } catch (Exception ex) {
                    ocrTransaction.setTotal(null);
                }
                ocrTransaction.setProcessed(true);

                processTaxReceiptCloudWatchService.putMetricData(ProcessTaxReceiptCloudWatchService.OCR_PROCESS_OUT_QUEUE_ELAPSED_TIME, processOutQueueModel.getElapsedTime());

                ocrTransactionDAO.save(ocrTransaction);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
