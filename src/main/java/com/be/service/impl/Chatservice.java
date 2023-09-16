package com.be.service.impl;

import com.be.model.Account;
import com.be.model.Chat;
import com.be.repository.IChatRepo;
import com.be.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Chatservice implements IChatService {
@Autowired
    IChatRepo chatRepo;
    @Override
    public Chat save(Chat chat) {
        return chatRepo.save(chat);
    }

    @Override
    public void delete(int id) {
       chatRepo.deleteById(id);
    }

    @Override
    public List<Chat> getAll() {
        return chatRepo.findAll();
    }

    @Override
    public Chat findById(int id) {
        return chatRepo.findById(id).get();
    }
    @Override
    public List<Chat> findAllByAccount1(Account account1){
        return chatRepo.findAllByAccount1(account1);
    }

    @Override
    public List<Chat> findAllByAccount2(Account account2) {
        return chatRepo.findAllByAccount2(account2);
    }
}