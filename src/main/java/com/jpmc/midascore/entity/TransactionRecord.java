package com.jpmc.midascore.entity;


import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "sender_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction_record_sender_id")
    )
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(
            name = "recipient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction_record_recipient_id")
    )
    private UserRecord recipient;

    @Column(nullable = false)
    private float transactionAmount;

    protected TransactionRecord() {
    }

    public TransactionRecord(UserRecord sender, UserRecord recipient, float transactionAmount) {
        this.sender = sender;
        this.recipient = recipient;
        this.transactionAmount = transactionAmount;
    }

    public UserRecord getSender() { return sender; }
    public UserRecord getRecipient() { return recipient; }
    public float getTransactionAmount() { return transactionAmount; }

    @Override
    public String toString() {
        return String.format("TransactionRecord[sender=%s, recipient=%s, amount=%.2f]",
                sender.getId(), recipient.getId(), transactionAmount);
    }
}
