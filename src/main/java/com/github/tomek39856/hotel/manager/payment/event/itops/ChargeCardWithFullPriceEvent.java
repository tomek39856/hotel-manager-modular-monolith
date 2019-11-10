package com.github.tomek39856.hotel.manager.payment.event.itops;

import com.github.tomek39856.hotel.manager.payment.dto.PaymentInformationDto;

public class ChargeCardWithFullPriceEvent extends ChargeCardEvent {
  public ChargeCardWithFullPriceEvent(PaymentInformationDto payment) {
    super(payment, 100);
  }
}
