package rado.alberto.org.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.OrderItemDto;
import rado.alberto.org.dto.OrderItemResponseDto;
import rado.alberto.org.entities.OrderItem;
import rado.alberto.org.exceptions.InvalidOrderItemException;
import rado.alberto.org.exceptions.OrderItemNotFoundException;
import rado.alberto.org.mapper.OrderItemMapper;
import rado.alberto.org.pricing.PricingCalculator;
import rado.alberto.org.repositories.OrderItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    public OrderItemResponseDto getOrderItemById(long id) {
        Optional<OrderItem> result = orderItemRepository.findById(id);
        if(result.isEmpty()) {
            throw new OrderItemNotFoundException();
        }
        return orderItemMapper.toResponseDto(result.get());
    }

    public List<OrderItemResponseDto> getOrderItemsByOrderId(long orderId) {
        List<OrderItem> search = orderItemRepository.findByOrder_Id(orderId);
        return orderItemMapper.toDtos(search);
    }

    @Transactional
    public OrderItemResponseDto createOrderItem(@Valid OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        return orderItemMapper.toResponseDto(orderItemRepository.save(verifyOrder(orderItem, orderItemDto)));
    }

    @Transactional
    public OrderItemResponseDto updateOrderItem(@Valid OrderItemResponseDto orderItemResponseDto) {
        Optional<OrderItem> search = orderItemRepository.findById(orderItemResponseDto.id());
        if(search.isEmpty()) {
            throw new OrderItemNotFoundException();
        }
        return orderItemMapper.toResponseDto(orderItemRepository.save(verifyOrder(search.get(), orderItemResponseDto)));
    }

    @Transactional
    public void deleteOrderItem(long id) {
        Optional<OrderItem> search = orderItemRepository.findById(id);
        if(search.isEmpty()) {
            throw new OrderItemNotFoundException();
        }
        orderItemRepository.deleteById(id);
    }
    
    private OrderItem verifyOrder(OrderItem orderItem, Object o) {
        OrderItem newOrderItem;
        if(o instanceof OrderItemResponseDto) {
            newOrderItem = orderItemMapper.toEntity((OrderItemResponseDto) o);
        }else if(o instanceof OrderItemDto) {
            newOrderItem = orderItemMapper.toEntity((OrderItemDto) o);
        }else{
            throw new InvalidOrderItemException();
        }
        if(newOrderItem.getProduct() != null){
            orderItem.setProduct(newOrderItem.getProduct());
        }
        orderItem.setQuantity(newOrderItem.getQuantity());
        orderItem.setDiscount(newOrderItem.getProduct().getDiscount());
        orderItem.setPrice(newOrderItem.getProduct().getPrice());
        orderItem.setTax(newOrderItem.getProduct().getCategory().getTax());
        orderItem.setTotalPrice(PricingCalculator.calculateOrderItemTotal(orderItem));
        return orderItem;
    }

}
