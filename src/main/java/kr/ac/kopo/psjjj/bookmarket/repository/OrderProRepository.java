package kr.ac.kopo.psjjj.bookmarket.repository;



import kr.ac.kopo.psjjj.bookmarket.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProRepository extends JpaRepository<Order, Long> {
}

