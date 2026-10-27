package kr.ac.kopo.psjjj.bookmarket.service;

import kr.ac.kopo.psjjj.bookmarket.domain.Order;

public interface OrderService {
    void confirmOrder(String bookId, long quantity);
    Long saveOrder(Order order);
}