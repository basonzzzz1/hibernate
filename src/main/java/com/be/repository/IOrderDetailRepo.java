package com.be.repository;


import com.be.model.Account;
import com.be.model.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface IOrderDetailRepo extends CrudRepository<OrderDetail, Integer> {
    OrderDetail findById(int id);

}
