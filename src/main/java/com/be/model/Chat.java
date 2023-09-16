package com.be.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne
    private Account account1;
    @ManyToOne
    private Account account2;

    public Chat() {
    }

    public Chat(String content, LocalDateTime createdAt, Account account1, Account account2) {
        this.content = content;
        this.createdAt = createdAt;
        this.account1 = account1;
        this.account2 = account2;
    }

    public Chat(int id, String content, LocalDateTime createdAt, Account account1, Account account2) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.account1 = account1;
        this.account2 = account2;
    }
}