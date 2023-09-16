package com.be.controller;
import com.be.model.Orders;
import com.be.service.IAccountService;
import com.be.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private IOrderService ordersService;
    @Autowired
    private IAccountService accountService;
    @PostMapping
    public ResponseEntity<Orders> save(@RequestBody Orders orders) {
        int accountId = orders.getAccount().getId();
        orders.setAccount(accountService.findById(accountId));
        ordersService.save(orders);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.getAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Orders> findByIdNext(@PathVariable int id) {
        Orders orders = ordersService.findById(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        boolean deleted = ordersService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}