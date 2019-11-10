package com.github.tomek39856.hotel.manager.itops;

import com.github.tomek39856.hotel.manager.infrastructure.EventPublisher;
import com.github.tomek39856.hotel.manager.itops.event.HoldCreatedEvent;
import com.github.tomek39856.hotel.manager.itops.event.HoldFailedEvent;
import com.github.tomek39856.hotel.manager.payment.dto.PaymentInformationDto;
import com.github.tomek39856.hotel.manager.rate.dto.RoomRateDto;
import com.github.tomek39856.hotel.manager.rate.RateProvider;
import com.github.tomek39856.hotel.manager.reservation.dto.RoomReservationDto;
import com.github.tomek39856.hotel.manager.reservation.ReservationProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
 class CreateHoldUseCase {
  private final RateProvider rateProvider;
  private final ReservationProvider reservationProvider;
  private final EventPublisher eventPublisher;
  private final List<String> createdHolds = new ArrayList<>();
  private final ExternalCardService externalCardService;

   CreateHoldUseCase(RateProvider rateProvider, ReservationProvider reservationProvider, EventPublisher eventPublisher, ExternalCardService externalCardService) {
    this.rateProvider = rateProvider;
    this.reservationProvider = reservationProvider;
    this.eventPublisher = eventPublisher;
    this.externalCardService = externalCardService;
   }

   void create(PaymentInformationDto payment) {
    if(createdHolds.contains(payment.getId())) {
      return;
    } else {
      createdHolds.add(payment.getId());
    }
    RoomReservationDto reservation = reservationProvider.provide(payment.getReservationId());
    RoomRateDto rate = rateProvider.findRateAt(reservation.getRoomType(), reservation.getStart(), reservation.getEnd(), reservation.getReservedAt());
    try {
      externalCardService.hold(payment.getCard().getOwner(), payment.getCard().getNumber(), payment.getCard().getValidityDate(), rate.getSum());
      eventPublisher.publishEvent(new HoldCreatedEvent(payment.getId()));
    } catch (Exception e) {
      eventPublisher.publishEvent(new HoldFailedEvent(payment.getId()));
    }
  }
}
