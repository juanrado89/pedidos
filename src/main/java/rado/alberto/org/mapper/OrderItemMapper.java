package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import rado.alberto.org.dto.OrderItemDto;
import rado.alberto.org.dto.OrderItemResponseDto;
import rado.alberto.org.entities.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toDto(OrderItem orderItem);
    OrderItemResponseDto toResponseDto(OrderItem orderItem);
    OrderItem toEntity(OrderItemDto orderItemDto);
    OrderItem toEntity(OrderItemResponseDto orderItemResponseDto);

    List<OrderItemResponseDto> toDtos(List<OrderItem> orderItems);
    List<OrderItem> toEntities(List<OrderItemResponseDto> orderItemResponseDtos);
}
