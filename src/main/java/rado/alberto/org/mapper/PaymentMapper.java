package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import rado.alberto.org.dto.PaymentDto;
import rado.alberto.org.dto.PaymentUpdateDto;
import rado.alberto.org.entities.Payment;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomerOrderMapper.class , OrderMapper.class})
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);
    Payment toEntity(PaymentDto paymentDto);

    List<PaymentDto> toDtos(List<Payment> payments);
    List<Payment> toEntities(List<PaymentDto> paymentDtos);

    Payment toEntity(PaymentUpdateDto o);
    PaymentUpdateDto toUpdateDto(Payment payment);
}
