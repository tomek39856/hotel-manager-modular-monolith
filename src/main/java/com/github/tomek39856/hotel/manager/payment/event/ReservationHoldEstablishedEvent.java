package com.github.tomek39856.hotel.manager.payment.event;

import com.github.tomek39856.hotel.manager.infrastructure.Event;

public class ReservationHoldEstablishedEvent implements Event {
  private final String reservationId;

  public ReservationHoldEstablishedEvent(String reservationId) {
    this.reservationId = reservationId;
  }

  public String getReservationId() {
    return reservationId;
  }
}
