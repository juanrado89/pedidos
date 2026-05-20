package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto) {
        OrderDto result = orderService.createOrder(orderDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody @Valid OrderDto orderDto) {
        OrderDto result = orderService.updateOrder(orderDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
