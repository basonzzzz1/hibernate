package com.be.service;

import com.be.model.Account;
import com.be.model.Chat;

import java.util.List;

public interface IChatService {
    Chat save(Chat chat);
    void delete(int id);
    List<Chat> getAll();
    Chat findById(int id);
    List<Chat> findAllByAccount1(Account account1);
    List<Chat> findAllByAccount2(Account account2);
}
