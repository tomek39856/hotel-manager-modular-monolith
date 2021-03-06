package com.github.tomek39856.hotel.manager.payment;

import com.github.tomek39856.hotel.manager.occupancy.event.UserCheckedInEvent;
import com.github.tomek39856.hotel.manager.reservation.event.NoShowEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
 class GuestEventListener {
  private final ProceedPaymentForReservationUseCase proceedPaymentForReservationUseCase;
  private final ChargeCancellationFeeForReservationUseCase chargeCancellationFeeForReservationUseCase;

   GuestEventListener(ProceedPaymentForReservationUseCase proceedPaymentForReservationUseCase, ChargeCancellationFeeForReservationUseCase chargeCancellationFeeForReservationUseCase) {
    this.proceedPaymentForReservationUseCase = proceedPaymentForReservationUseCase;
    this.chargeCancellationFeeForReservationUseCase = chargeCancellationFeeForReservationUseCase;
  }

  @EventListener
   void handle(UserCheckedInEvent event) {
    proceedPaymentForReservationUseCase.execute(event.getReservationId());
  }

  @EventListener
   void handle(NoShowEvent event) {
    chargeCancellationFeeForReservationUseCase.execute(event.getReservationId());
  }
}
