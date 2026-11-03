package kr.ac.kopo.psjjj.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.kopo.psjjj.bookmarket.domain.*;
import kr.ac.kopo.psjjj.bookmarket.service.BookService;
import kr.ac.kopo.psjjj.bookmarket.service.CartService;
import kr.ac.kopo.psjjj.bookmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    // 1. 장바구니에서 주문서 작성 화면 이동
    @GetMapping("/cart")
    public String requestCartOrderForm(HttpServletRequest request, Model model) {
        String sessionId = request.getSession().getId();
        Cart cart = cartService.read(sessionId);

        if (cart == null || cart.getCartItems().isEmpty()) {
            return "redirect:/cart";
        }

        Order order = new Order();
        Customer customer = new Customer();
        customer.setAddress(new Address());
        order.setCustomer(customer);
        Shipping shipping = new Shipping();
        shipping.setAddress(new Address());
        order.setShipping(shipping);

        model.addAttribute("cart", cart);
        model.addAttribute("order", order);
        return "order/orderForm";
    }

    // 2. 장바구니 주문 저장 및 완료 처리
    @PostMapping("/cart/save")
    public String saveCartOrder(@ModelAttribute("order") Order order, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        Cart cart = cartService.read(sessionId);

        if (cart == null || cart.getCartItems().isEmpty()) {
            return "redirect:/cart";
        }

        Map<String, OrderItem> orderItems = new HashMap<>();
        for (Map.Entry<String, CartItem> entry : cart.getCartItems().entrySet()) {
            CartItem cartItem = entry.getValue();
            OrderItem orderItem = new OrderItem();
            orderItem.setBookid(cartItem.getBook().getBookId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItems.put(entry.getKey(), orderItem);
        }
        order.setOrderItems(orderItems);
        order.setGrandTotal(cart.getGrandTotal());

        Long orderId = orderService.saveOrder(order);

        try {
            cartService.delete(sessionId);
        } catch (Exception ignored) {}

        return "redirect:/order/finished?orderId=" + orderId;
    }

    // 3. 주문 완료 화면
    @GetMapping("/finished")
    public String orderFinished(@RequestParam("orderId") Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "order/orderFinished";
    }
}
