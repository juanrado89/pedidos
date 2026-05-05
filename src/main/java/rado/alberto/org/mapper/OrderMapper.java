package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import rado.alberto.org.dto.OrderDto;
import rado.alberto.org.entities.Order;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class , CustomerOrderMapper.class, OrderItemMapper.class})
public interface OrderMapper {

    Order toEntity(OrderDto orderDto);
    OrderDto toDto(Order order);

    List<OrderDto> toDtos(List<Order> orders);
    List<Order> toEntities(List<OrderDto> orderDtos);
}
