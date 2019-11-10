package com.github.tomek39856.hotel.manager.payment.event.itops;

import com.github.tomek39856.hotel.manager.infrastructure.Event;
import com.github.tomek39856.hotel.manager.payment.dto.PaymentInformationDto;

public abstract class ChargeCardEvent implements Event {
  private final PaymentInformationDto payment;
  private final long amountChargePercentage;

  public ChargeCardEvent(PaymentInformationDto payment, long amountChargePercentage) {
    this.payment = payment;
    this.amountChargePercentage = amountChargePercentage;
  }

  public PaymentInformationDto getPayment() {
    return payment;
  }

  public long getAmountChargePercentage() {
    return amountChargePercentage;
  }
}
