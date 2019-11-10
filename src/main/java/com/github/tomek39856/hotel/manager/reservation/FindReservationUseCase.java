package com.github.tomek39856.hotel.manager.reservation;

import com.github.tomek39856.hotel.manager.common.NotFoundException;
import com.github.tomek39856.hotel.manager.infrastructure.UseCase;
import com.github.tomek39856.hotel.manager.reservation.dto.RoomReservationDto;

@UseCase
class FindReservationUseCase {
  private final ReservableRoomRepository reservableRoomRepository;

  public FindReservationUseCase(ReservableRoomRepository reservableRoomRepository) {
    this.reservableRoomRepository = reservableRoomRepository;
  }

  public RoomReservationDto execute(String id) {
    return reservableRoomRepository.findRoomReservationByReservationId(id).orElseThrow(NotFoundException::new).toDto();
  }
}
