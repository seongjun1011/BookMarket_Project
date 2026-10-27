package kr.ac.kopo.psjjj.bookmarket.service;

import kr.ac.kopo.psjjj.bookmarket.domain.Order;
import kr.ac.kopo.psjjj.bookmarket.repository.OrderProRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OrderProService {
    @Autowired
    private OrderProRepository orderProRepository;

    public void save(Order order){
        orderProRepository.save(order);
    }

    public Order get(Long id){
        return orderProRepository.findById(id).get();
    }

    public void delete(Long id){
        orderProRepository.deleteById(id);
    }
    public void deleteAll(){
        orderProRepository.deleteAll();
    }
    // 페이지 단위로 주문 목록 전체를 보여주기 위한 기능
    public Page<Order> listAll(int pageNum, String sortField, String sortDir){

        return null;
    }
}
