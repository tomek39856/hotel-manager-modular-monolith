package com.github.tomek39856.hotel.manager.itops;

import com.github.tomek39856.hotel.manager.payment.event.itops.CreateHoldEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class CreateHoldEventListener {
  private final CreateHoldUseCase createHoldUseCase;

  CreateHoldEventListener(CreateHoldUseCase createHoldUseCase) {
    this.createHoldUseCase = createHoldUseCase;
  }

  @Async
  @EventListener
  void handle(CreateHoldEvent createHoldEvent) {
    createHoldUseCase.create(createHoldEvent.getPayment());
  }
}
