package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {
    private static final Logger logger = LoggerFactory.getLogger(TransactionListener.class);

    @Autowired
    private DatabaseConduit databaseConduit;

    @Value("${general.kafka-topic}")
    private String topic;

    public TransactionListener() {
        logger.info("TransactionListener created - topic: {}", topic);
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleTransaction(Transaction transaction){
        logger.info("*** RECEIVED TRANSACTION ***");
        logger.info("Received transaction: {}", transaction);
        logger.info("Transaction amount: {}", transaction.getAmount());

        //Process Transaction through Database conduit
        boolean success = databaseConduit.processTransaction(
                transaction.getSenderId(),
                transaction.getRecipientId(),
                transaction.getAmount());
        if(success) logger.info("Transaction has been saved to DB successfully.");
        else{
            logger.info("Failed to save Transaction to DB");
        }

        logger.info("*** END TRANSACTION ***");
    }
}