package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {
    private static final Logger logger = LoggerFactory.getLogger(TransactionListener.class);

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
        logger.info("*** END TRANSACTION ***");
    }
}