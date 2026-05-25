package com.ps.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    // Défaut 1 : field injection
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setStatus("NEW");
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Défaut 3 : méthode trop longue
    // Défaut 2 : chaînes de statut dupliquées ("CANCELLED", "SHIPPED" en dur)
    // Défaut 6 : retourne ResponseEntity depuis le service
    // Défaut 8 (bug) : un order SHIPPED peut être annulé à tort
    public ResponseEntity<Order> doCancel(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        // Défaut 4 : nom de variable peu explicite
        String s = order.getStatus();

        if (s == null) {
            return ResponseEntity.badRequest().build();
        }

        if (s.equals("CANCELLED")) {
            return ResponseEntity.badRequest().build();
        }

        // BUG : manque la vérification de "SHIPPED"
        // Un order SHIPPED ne devrait pas pouvoir être annulé,
        // mais ici on ne bloque pas ce cas.

        if (s.equals("DELIVERED")) {
            return ResponseEntity.badRequest().build();
        }

        order.setStatus("CANCELLED");
        Order saved = orderRepository.save(order);
        return ResponseEntity.ok(saved);
    }
}
