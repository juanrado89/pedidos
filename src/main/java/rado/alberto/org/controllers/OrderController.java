package rado.alberto.org.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rado.alberto.org.dto.OrderDto;
import rado.alberto.org.services.OrderService;

import java.util.List;

@RestController()
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto result = orderService.getOrderById(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderDto>> getOrdersByIdCustomer(@PathVariable Long id) {
        List<OrderDto> result = orderService.getOrderByIdCustomer(id);
        return ResponseEntity.ok(result);
    }
}
