package com.be.controller;

import com.be.model.Account;
import com.be.model.Chat;
import com.be.service.IAccountService;
import com.be.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    IChatService chatService;
    @Autowired
    IAccountService accountService;
    @GetMapping
    public ResponseEntity<List<Chat>> getAll(){
        return ResponseEntity.ok(chatService.getAll());
    }
    @GetMapping("/new/{idAccount}")
    public ResponseEntity<List<Chat>> getChatByAccount(@PathVariable int idAccount){
        Account account1 = accountService.findById(idAccount);
        List<Chat> chats = chatService.findAllByAccount1(account1);
//        chats.addAll(chatService.findAllByAccount2(account1));
        Map<Pair<Integer, Integer>, Chat> latestChats = new HashMap<>();
        for (Chat chat : chats) {
            int accountId1 = chat.getAccount1().getId();
            int accountId2 = chat.getAccount2().getId();
            Pair<Integer, Integer> accountPair = Pair.of(accountId1, accountId2);
            if (!latestChats.containsKey(accountPair) || chat.getCreatedAt().isAfter(latestChats.get(accountPair).getCreatedAt())) {
                latestChats.put(accountPair, chat);
            }
        }
        List<Chat> latestChatList = new ArrayList<>(latestChats.values());
        return ResponseEntity.ok(latestChatList);
    }
    @GetMapping("/detail/{idAccount1}/{idAccount2}")
    public ResponseEntity<List<Chat>> detailChat(@PathVariable int idAccount1 , @PathVariable int idAccount2){
        List<Chat> chats = chatService.getAll();
        List<Chat> chatList = new ArrayList<>();
        for (int i = 0; i < chats.size(); i++) {
            if(chats.get(i).getAccount1().getId() == idAccount1 && chats.get(i).getAccount2().getId() == idAccount2 || chats.get(i).getAccount1().getId() == idAccount2 && chats.get(i).getAccount2().getId() == idAccount1){
                chatList.add(chats.get(i));
            }
        }
        return ResponseEntity.ok(chatList);
    }
    @PostMapping
    private ResponseEntity<?> save(@RequestBody Chat chat){
        List<Chat> chats = chatService.getAll();
        Account account1 = accountService.findById(chat.getAccount1().getId());
        Account account2 = accountService.findById(chat.getAccount2().getId());
        chatService.save(new Chat(chat.getContent() , LocalDateTime.now() ,account1 , account2 ));
        boolean check = true;
        for (int i = 0; i < chats.size(); i++) {
            Account account3 = chats.get(i).getAccount1();
            Account account4 = chats.get(i).getAccount2();
            if(account3 == account1 && account4 == account2){
                check = false;
            }
        }
        if (check){
            chatService.save(new Chat("xin chào" , LocalDateTime.now() ,account2 , account1 ));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/delete/{id}")
    private ResponseEntity<?> delete(@PathVariable int id){
        chatService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}