package com.github.tomek39856.hotel.manager.reservation;

import com.github.tomek39856.hotel.manager.common.NotFoundException;
import com.github.tomek39856.hotel.manager.infrastructure.EventPublisher;
import com.github.tomek39856.hotel.manager.infrastructure.UseCase;
import com.github.tomek39856.hotel.manager.reservation.event.NoShowEvent;

@UseCase
class GuestNoShowUseCase {
  private final EventPublisher eventPublisher;
  private final ReservableRoomRepository reservableRoomRepository;

  GuestNoShowUseCase(EventPublisher eventPublisher, ReservableRoomRepository reservableRoomRepository) {
    this.eventPublisher = eventPublisher;
    this.reservableRoomRepository = reservableRoomRepository;
  }

  void execute(String reservationId) {
    RoomReservation roomReservation = reservableRoomRepository.findRoomReservationByReservationId(reservationId).orElseThrow(NotFoundException::new);
    roomReservation.cancel();
    eventPublisher.publishEvent(new NoShowEvent(roomReservation.getId()));
  }
}
