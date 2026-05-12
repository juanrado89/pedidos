package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import rado.alberto.org.dto.OrderItemDto;
import rado.alberto.org.entities.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toDto(OrderItem orderItem);
    OrderItem toEntity(OrderItemDto orderItemDto);

    List<OrderItemDto> toDtos(List<OrderItem> orderItems);
    List<OrderItem> toEntities(List<OrderItemDto> orderItemDtos);
}
