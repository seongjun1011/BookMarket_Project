package kr.ac.kopo.psjjj.bookmarket.service;

import kr.ac.kopo.psjjj.bookmarket.domain.Book;
import kr.ac.kopo.psjjj.bookmarket.domain.Order;
import kr.ac.kopo.psjjj.bookmarket.repository.BookRepository;
import kr.ac.kopo.psjjj.bookmarket.repository.OrderProRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderProRepository orderRepository; // JpaRepository를 상속받은 리포지토리

    @Override
    public void confirmOrder(String bookId, long quantity) {
        Book bookById = bookRepository.getBookById(bookId);
        if (bookById.getUnitsInStock() < quantity) {
            throw new IllegalArgumentException("도서 수량이 부족합니다. 구입 가능한 수량: " + bookById.getUnitsInStock());
        }
        bookById.setUnitsInStock(bookById.getUnitsInStock() - quantity);
    }

    @Override
    public Long saveOrder(Order order) {
        // saveOrder 대신 JPA 기본 메서드인 save()를 사용합니다.
        Order savedOrder = orderRepository.save(order);

        // 저장된 Order 객체에서 생성된 orderId를 가져와 반환합니다.
        return savedOrder.getOrderId();
    }
}