package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;


    //We did Constructor Injection here instead of Autowiring
    public DatabaseConduit(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }
    public UserRecord findUserById(long id){
        return userRepository.findById(id);
    }

    @Transactional
    public boolean processTransaction(long senderId, long recipientId, float amount){
        UserRecord sender = userRepository.findById(senderId);
        UserRecord recipient = userRepository.findById(recipientId);

        //Validate
        if(sender == null || recipient == null) return false;

        if(sender.getBalance() < amount) return false;

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        //Save updated balance
        userRepository.save(sender);
        userRepository.save(recipient);

        TransactionRecord transaction = new TransactionRecord(sender, recipient, amount);
        transactionRepository.save(transaction);

        return true;



    }

}
