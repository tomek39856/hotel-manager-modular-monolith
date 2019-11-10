package com.github.tomek39856.hotel.manager.occupancy;

import com.github.tomek39856.hotel.manager.payment.event.ReservationPaymentFailedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class ReservationPaymentFailedEventListener {
  private final CancelCheckInUseCase cancelCheckInUseCase;

  public ReservationPaymentFailedEventListener(CancelCheckInUseCase cancelCheckInUseCase) {
    this.cancelCheckInUseCase = cancelCheckInUseCase;
  }

  @EventListener
  void handle(ReservationPaymentFailedEvent event) {
    cancelCheckInUseCase.execute(event.getReservationId());
  }
}
