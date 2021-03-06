package com.github.tomek39856.hotel.manager.itops;

import com.github.tomek39856.hotel.manager.infrastructure.EventPublisher;
import com.github.tomek39856.hotel.manager.infrastructure.UseCase;
import com.github.tomek39856.hotel.manager.itops.event.CardChargedEvent;
import com.github.tomek39856.hotel.manager.itops.event.ChargeCardFailedEvent;
import com.github.tomek39856.hotel.manager.payment.dto.CardDto;
import com.github.tomek39856.hotel.manager.payment.dto.PaymentInformationDto;
import com.github.tomek39856.hotel.manager.rate.RateProvider;
import com.github.tomek39856.hotel.manager.rate.dto.RoomRateDto;
import com.github.tomek39856.hotel.manager.reservation.ReservationProvider;
import com.github.tomek39856.hotel.manager.reservation.dto.RoomReservationDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@UseCase
class ChargeCardUseCase {
  private final RateProvider rateProvider;
  private final ReservationProvider reservationProvider;
  private final EventPublisher eventPublisher;
  private final ExternalCardService externalCardService;
  private final List<String> proceededPayments = new ArrayList<>();

  ChargeCardUseCase(RateProvider rateProvider, ReservationProvider reservationProvider, EventPublisher eventPublisher, ExternalCardService externalCardService) {
    this.rateProvider = rateProvider;
    this.reservationProvider = reservationProvider;
    this.eventPublisher = eventPublisher;
    this.externalCardService = externalCardService;
  }

  void charge(PaymentInformationDto payment, long amountPartPercentage) {
    if (proceededPayments.contains(payment.getId())) {
      return;
    } else {
      proceededPayments.add(payment.getId());
    }
    RoomReservationDto reservation = reservationProvider.provide(payment.getReservationId());
    RoomRateDto rate = rateProvider.findRateAt(reservation.getRoomType(), reservation.getStart(), reservation.getEnd(), reservation.getReservedAt());
    try {
      CardDto card = payment.getCard();
      externalCardService.charge(card.getOwner(), card.getNumber(), card.getValidityDate(), calculateAmount(amountPartPercentage, rate));
      eventPublisher.publishEvent(new CardChargedEvent(payment.getId()));
    } catch (Exception e) {
      eventPublisher.publishEvent(new ChargeCardFailedEvent(payment.getId()));
    }
  }

  private BigDecimal calculateAmount(long amountPartPercentage, RoomRateDto rate) {
    return rate.getSum().multiply(BigDecimal.valueOf(amountPartPercentage)).divide(BigDecimal.valueOf(100));
  }
}
