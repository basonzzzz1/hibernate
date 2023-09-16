package com.be.repository;


import com.be.model.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface IOrderRepo extends CrudRepository<Orders, Integer> {
    Orders findByDatetime (Date datetime);
}
