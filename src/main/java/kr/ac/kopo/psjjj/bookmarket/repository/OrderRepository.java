package kr.ac.kopo.psjjj.bookmarket.repository;

import kr.ac.kopo.psjjj.bookmarket.domain.Order;
import org.springframework.stereotype.Repository;


public interface OrderRepository {
    Long saveOrder(Order order);
}
