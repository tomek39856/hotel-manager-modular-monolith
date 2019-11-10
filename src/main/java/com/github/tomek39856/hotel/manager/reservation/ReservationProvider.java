package com.github.tomek39856.hotel.manager.reservation;

import com.github.tomek39856.hotel.manager.reservation.dto.RoomReservationDto;
import org.springframework.stereotype.Service;

@Service
public class ReservationProvider {
  private final FindReservationUseCase findReservationUseCase;

  public ReservationProvider(FindReservationUseCase findReservationUseCase) {
    this.findReservationUseCase = findReservationUseCase;
  }

  public RoomReservationDto provide(String id) {
    return findReservationUseCase.execute(id);
  }
}
