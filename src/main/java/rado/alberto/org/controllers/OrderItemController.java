package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rado.alberto.org.dto.OrderItemDto;
import rado.alberto.org.dto.OrderItemResponseDto;
import rado.alberto.org.services.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/orderitem")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDto> getOrderItem(@PathVariable long id) {
        OrderItemResponseDto result = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/items/{orderId}")
    public ResponseEntity<List<OrderItemResponseDto>> getOrderItemsByOrderId(@PathVariable long orderId) {
        List<OrderItemResponseDto> result = orderItemService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/")
    public ResponseEntity<OrderItemResponseDto> createOrderItem(@RequestBody @Valid OrderItemDto orderItemDto) {
        OrderItemResponseDto result = orderItemService.createOrderItem(orderItemDto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(@RequestBody @Valid OrderItemResponseDto orderItemResponseDto) {
        OrderItemResponseDto result = orderItemService.updateOrderItem(orderItemResponseDto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrderItem(@PathVariable long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
