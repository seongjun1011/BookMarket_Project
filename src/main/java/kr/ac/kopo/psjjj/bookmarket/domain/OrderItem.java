package kr.ac.kopo.psjjj.bookmarket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private String bookid;
    private int quantity;
    private BigDecimal totalPrice;
}
