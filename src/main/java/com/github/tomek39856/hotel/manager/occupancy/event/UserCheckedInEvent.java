package com.github.tomek39856.hotel.manager.occupancy.event;

import com.github.tomek39856.hotel.manager.infrastructure.Event;

public class UserCheckedInEvent implements Event {
  private final String reservationId;

  public UserCheckedInEvent(String reservationId) {
    this.reservationId = reservationId;
  }

  public String getReservationId() {
    return reservationId;
  }
}
