package com.github.tomek39856.hotel.manager.payment;

import com.github.tomek39856.hotel.manager.common.NotFoundException;
import com.github.tomek39856.hotel.manager.infrastructure.EventPublisher;
import com.github.tomek39856.hotel.manager.infrastructure.UseCase;
import com.github.tomek39856.hotel.manager.payment.event.itops.ChargeCardWithFullPriceEvent;

@UseCase
class ProceedPaymentForReservationUseCase {
  private final PaymentRepository paymentRepository;
  private final EventPublisher eventPublisher;

  ProceedPaymentForReservationUseCase(PaymentRepository paymentRepository, EventPublisher eventPublisher) {
    this.paymentRepository = paymentRepository;
    this.eventPublisher = eventPublisher;
  }

  void execute(String reservationId) {
    PaymentInformation paymentInformation = paymentRepository.findOneByReservationId(reservationId).orElseThrow(NotFoundException::new);
    eventPublisher.publishEvent(new ChargeCardWithFullPriceEvent(paymentInformation.toDto()));
  }
}
