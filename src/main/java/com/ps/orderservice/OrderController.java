package com.ps.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Défaut 7 : accès repository direct depuis le controller
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        // Défaut 5 : logique métier dans le controller
        if (order.getCustomerEmail() == null || order.getCustomerEmail().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (order.getTotalAmount() == null || order.getTotalAmount().doubleValue() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Order created = orderService.createOrder(order);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    // Défaut 7 : accès direct au repository
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        return orderService.doCancel(id);
    }
}
