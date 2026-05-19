package rado.alberto.org.services;

import org.springframework.stereotype.Service;
import rado.alberto.org.dto.OrderDto;
import rado.alberto.org.entities.Order;
import rado.alberto.org.exceptions.OrderNotFoundException;
import rado.alberto.org.mapper.OrderMapper;
import rado.alberto.org.repositories.OrderRepository;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }


    public OrderDto getOrderById(Long id) {
        Optional<Order> search = orderRepository.findById(id);
        if (search.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return orderMapper.toDto(search.get());
    }
}
