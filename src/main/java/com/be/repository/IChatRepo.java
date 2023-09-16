package com.be.repository;

import com.be.model.Account;
import com.be.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IChatRepo extends JpaRepository<Chat , Integer> {
    List<Chat> findAllByAccount1(Account account1);
    List<Chat> findAllByAccount2(Account account2);
}
