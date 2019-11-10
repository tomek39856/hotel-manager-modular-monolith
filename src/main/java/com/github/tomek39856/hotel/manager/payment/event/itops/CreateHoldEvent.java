package com.github.tomek39856.hotel.manager.payment.event.itops;

import com.github.tomek39856.hotel.manager.infrastructure.Event;
import com.github.tomek39856.hotel.manager.payment.dto.PaymentInformationDto;

public class CreateHoldEvent implements Event {
  private final PaymentInformationDto payment;

  public CreateHoldEvent(PaymentInformationDto payment) {
    this.payment = payment;
  }

  public PaymentInformationDto getPayment() {
    return payment;
  }
}
