package com.github.tomek39856.hotel.manager.reservation;

import com.github.tomek39856.hotel.manager.common.NotFoundException;
import com.github.tomek39856.hotel.manager.infrastructure.UseCase;
import com.github.tomek39856.hotel.manager.reservation.dto.CreateRoomReservationDto;
import com.github.tomek39856.hotel.manager.reservation.dto.RoomReservationDto;

@UseCase
class ReserveRoomUseCase {
  private final ReservableRoomRepository reservableRoomRepository;

  ReserveRoomUseCase(ReservableRoomRepository reservableRoomRepository) {
    this.reservableRoomRepository = reservableRoomRepository;
  }

  RoomReservationDto execute(CreateRoomReservationDto createRoomReservation) {
    ReservableRoom reservableRoom = reservableRoomRepository.findAvailableRoomByTypeAndAvailableBetween(
        createRoomReservation.getRoomType(), createRoomReservation.getFrom(), createRoomReservation.getTo())
        .orElseThrow(NotFoundException::new);
    return reservableRoom.reserve(createRoomReservation.getFrom(), createRoomReservation.getTo()).toDto();
  }
}
