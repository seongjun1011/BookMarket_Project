package kr.ac.kopo.psjjj.bookmarket.controller;

import kr.ac.kopo.psjjj.bookmarket.domain.Order;
import kr.ac.kopo.psjjj.bookmarket.service.BookService;
import kr.ac.kopo.psjjj.bookmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookService bookService;

    // 1. 주문서 작성 화면 이동
    @GetMapping
    public String requestOrderForm(@RequestParam("bookId") String bookId, Model model) {
        model.addAttribute("book", bookService.getBookById(bookId));
        model.addAttribute("order", new Order());
        return "order/orderForm";
    }

    // 2. 주문 확인 및 수량 검증
    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam("bookId") String bookId,
                               @RequestParam("quantity") long quantity,
                               @ModelAttribute("order") Order order,
                               Model model) {
        try {
            // 재고 수량 확인 및 차감
            orderService.confirmOrder(bookId, quantity);

            model.addAttribute("book", bookService.getBookById(bookId));
            model.addAttribute("quantity", quantity);
            model.addAttribute("order", order);

            return "order/orderConfirm";
        } catch (IllegalArgumentException e) {
            // 재고 부족 시 에러 메시지와 함께 도서 상세 페이지로 이동
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("book", bookService.getBookById(bookId));
            return "book/bookDetail";
        }
    }

    // 3. 최종 주문 저장 및 완료 처리
    @PostMapping("/save")
    public String processSaveOrder(@ModelAttribute("order") Order order, Model model) {
        Long orderId = orderService.saveOrder(order);
        model.addAttribute("orderId", orderId);
        return "redirect:/order/finished?orderId=" + orderId;
    }

    // 4. 주문 완료 화면
    @GetMapping("/finished")
    public String orderFinished(@RequestParam("orderId") Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "order/orderFinished";
    }
}