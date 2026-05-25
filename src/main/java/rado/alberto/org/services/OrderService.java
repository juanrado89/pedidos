package rado.alberto.org.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.OrderDto;
import rado.alberto.org.dto.OrderStatusDto;
import rado.alberto.org.entities.Order;
import rado.alberto.org.entities.OrderItem;
import rado.alberto.org.exceptions.InvalidOrderException;
import rado.alberto.org.exceptions.InvalidOrderStatusTransitionException;
import rado.alberto.org.exceptions.OrderNotFoundException;
import rado.alberto.org.kafka.events.OrderCreatedEvent;
import rado.alberto.org.kafka.producers.OrderEventProducer;
import rado.alberto.org.mapper.AddressMapper;
import rado.alberto.org.mapper.CustomerMapper;
import rado.alberto.org.mapper.OrderItemMapper;
import rado.alberto.org.mapper.OrderMapper;
import rado.alberto.org.pricing.PricingCalculator;
import rado.alberto.org.repositories.OrderRepository;
import rado.alberto.org.variables.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CustomerMapper customerMapper, AddressMapper addressMapper, OrderItemMapper orderItemMapper, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
        this.orderItemMapper = orderItemMapper;
        this.orderEventProducer = orderEventProducer;
    }


    public OrderDto getOrderById(Long id, Long idCustomer) {
        Optional<Order> search = orderRepository.findById(id);
        if (search.isEmpty()) {
            throw new OrderNotFoundException();
        }
        if(!search.get().getCustomer().getId().equals(idCustomer)) {
            throw new OrderNotFoundException();
        }
        return orderMapper.toDto(search.get());
    }
    public OrderDto getOrderById(Long id) {
        Optional<Order> search = orderRepository.findById(id);
        if (search.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return orderMapper.toDto(search.get());
    }

    public List<OrderDto> getOrderByIdCustomer(Long id) {
        List<Order> result = orderRepository.findAllByCustomer_Id(id);
        return orderMapper.toDtos(result);
    }

    @Transactional
    public OrderDto updateOrderStatus(Long id, @Valid OrderStatusDto orderStatus) {
        Optional<Order> search = orderRepository.findById(id);
        if (search.isEmpty()) {
            throw new OrderNotFoundException();
        }
        if(search.get().getOrderStatus() == OrderStatus.CANCELADO || search.get().getOrderStatus() == OrderStatus.ENVIADO) {
            throw new InvalidOrderStatusTransitionException();
        }
        search.get().setOrderStatus(orderStatus.orderStatus());
        return orderMapper.toDto(orderRepository.save(search.get()));
    }

    @Transactional
    public OrderDto createOrder(@Valid OrderDto orderDto, Long idCustomer) {
        if(!orderDto.customer().id().equals(idCustomer)){
            throw new InvalidOrderException();
        }
        Order order = new Order();
        Order resultOrder = orderRepository.save(verifyOrder(order, orderDto, true));

        orderEventProducer.publishOrderCreated(new OrderCreatedEvent(
                resultOrder.getId(),
                resultOrder.getCustomer().getId(),
                resultOrder.getTotalAmount(),
                resultOrder.getOrderDate()
        ));

        return orderMapper.toDto(resultOrder);
    }

    @Transactional
    public OrderDto updateOrder(@Valid OrderDto orderDto, Long id) {
        Optional<Order> search = orderRepository.findById(orderDto.id());
        if (search.isEmpty()) {
            throw new OrderNotFoundException();
        }
        if(!search.get().getCustomer().getId().equals(id)){
            throw new OrderNotFoundException();
        }
        Order order = search.get();
        return orderMapper.toDto(orderRepository.save(verifyOrder(order, orderDto, false)));
    }

    @Transactional
    public void deleteOrder(Long id, Long idCustomer) {
        Optional<Order> search = orderRepository.findById(id);
        if (search.isEmpty() || !search.get().getCustomer().getId().equals(idCustomer)) {
            throw new OrderNotFoundException();
        }
        orderRepository.deleteById(id);
    }

    private Order verifyOrder(Order order, OrderDto orderDto, boolean create) {
        if(create) {
            order.setOrderStatus(OrderStatus.EN_PROCESO);
            order.setOrderDate(LocalDateTime.now());
        }else{
            order.setId(orderDto.id());
            order.setOrderStatus(orderDto.orderStatus());
            order.setOrderDate(orderDto.orderDate());
        }
        if(orderDto.customer() != null) {
            order.setCustomer(customerMapper.toEntity(orderDto.customer()));
        }
        if(orderDto.shippingAddress() != null) {
            order.setShippingAddress(addressMapper.toEntity(orderDto.shippingAddress()));
        }
        if(orderDto.billingAddress() != null) {
            order.setBillingAddress(addressMapper.toEntity(orderDto.billingAddress()));
        }

        if(orderDto.items() != null) {
            List<OrderItem> orderItems = orderItemMapper.toEntities(orderDto.items());
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
            }
            order.setItems(orderItems);
            if(orderDto.totalAmount() != null) {
                order.setTotalAmount(PricingCalculator.calculateTotalPrice(order.getItems()));
            }
        }
        return order;
    }

}
