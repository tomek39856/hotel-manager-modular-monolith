package com.github.tomek39856.hotel.manager.payment.event.itops;

import com.github.tomek39856.hotel.manager.payment.dto.PaymentInformationDto;

public class ChargeCancellationFeeEvent extends ChargeCardEvent {
  public ChargeCancellationFeeEvent(PaymentInformationDto payment) {
    super(payment, 10);
  }
}
