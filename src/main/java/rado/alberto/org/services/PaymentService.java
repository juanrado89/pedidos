package rado.alberto.org.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.PaymentDto;
import rado.alberto.org.dto.PaymentStatusDto;
import rado.alberto.org.dto.PaymentUpdateDto;
import rado.alberto.org.entities.Order;
import rado.alberto.org.entities.OrderItem;
import rado.alberto.org.entities.Payment;
import rado.alberto.org.entities.Product;
import rado.alberto.org.exceptions.InvalidPaymentException;
import rado.alberto.org.exceptions.InvalidPaymentStatusTransitionException;
import rado.alberto.org.exceptions.PaymentNotFoundException;
import rado.alberto.org.mapper.PaymentMapper;
import rado.alberto.org.repositories.PaymentRepository;
import rado.alberto.org.variables.OrderStatus;
import rado.alberto.org.variables.PaymentStatus;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public PaymentDto getPaymentById(Long id, Long idCustomer) {
        Optional<Payment> search = paymentRepository.findById(id);
        if(search.isEmpty()) {
            throw new PaymentNotFoundException();
        }
        if(!search.get().getCustomer().getId().equals(idCustomer)) {
            throw new PaymentNotFoundException();
        }
        return paymentMapper.toDto(search.get());
    }

    public PaymentDto getPaymentById(Long id) {
        Optional<Payment> search = paymentRepository.findById(id);
        if(search.isEmpty()) {
            throw new PaymentNotFoundException();
        }
        return paymentMapper.toDto(search.get());
    }

    public List<PaymentDto> getPaymentsByCustomerId(Long idCustomer) {
        List<Payment> search = paymentRepository.findAllByCustomer_Id(idCustomer);
        return paymentMapper.toDtos(search);
    }

    @Transactional
    public PaymentDto createPayment(@Valid PaymentDto paymentDto, Long id) {
        if(!paymentDto.customer().id().equals(id)) {
            throw new InvalidPaymentException();
        }
        Payment payment = new Payment();
        return paymentMapper.toDto(paymentRepository.save(verifyPayment(paymentDto, payment)));
    }

    @Transactional
    public PaymentUpdateDto updatePayment(@Valid PaymentUpdateDto paymentDto) {
        Optional<Payment> search = paymentRepository.findById(paymentDto.id());
        if(search.isEmpty()) {
            throw new PaymentNotFoundException();
        }
        return paymentMapper.toUpdateDto(paymentRepository.save(verifyPayment(paymentDto,search.get())));
    }

    @Transactional
    public PaymentUpdateDto updatePaymentStatus(Long idPayment, @Valid PaymentStatusDto paymentDto) {
        Optional<Payment> search = paymentRepository.findById(idPayment);
        if(search.isEmpty()) {
            throw new PaymentNotFoundException();
        }
        if(search.get().getPaymentStatus() == PaymentStatus.CANCELED || search.get().getPaymentStatus() == PaymentStatus.PAID) {
            throw new InvalidPaymentStatusTransitionException();
        }

        if (paymentDto.paymentStatus() == PaymentStatus.PAID) {

            Order order = search.get().getOrder();

            for (OrderItem item : order.getItems()) {

                Product product = item.getProduct();

                product.setStock(
                        product.getStock() - item.getQuantity()
                );
            }

            order.setOrderStatus(OrderStatus.EN_PROCESO);
        }
        search.get().setPaymentStatus(paymentDto.paymentStatus());
        return paymentMapper.toUpdateDto(paymentRepository.save(search.get()));
    }

    @Transactional
    public void deletePayment(Long id) {
        Optional<Payment> search = paymentRepository.findById(id);
        if(search.isEmpty()) {
            throw new PaymentNotFoundException();
        }
        paymentRepository.deleteById(id);
    }

    private Payment verifyPayment(Object o, Payment payment){
        Payment newPayment;
        if(o instanceof PaymentDto) {
            newPayment = paymentMapper.toEntity((PaymentDto) o);
            payment.setPaymentStatus(PaymentStatus.PENDING);
        }else if(o instanceof PaymentUpdateDto) {
            newPayment = paymentMapper.toEntity((PaymentUpdateDto) o);
            payment.setPaymentStatus(newPayment.getPaymentStatus());
        }else{
            throw new InvalidPaymentException();
        }
        if(newPayment.getPaymentMethod() != null) {
            payment.setPaymentMethod(newPayment.getPaymentMethod());
        }
        if(newPayment.getCustomer() != null) {
            payment.setCustomer(newPayment.getCustomer());
        }
        if(newPayment.getOrder() != null) {
            payment.setOrder(newPayment.getOrder());
        }
        return payment;
    }

}
