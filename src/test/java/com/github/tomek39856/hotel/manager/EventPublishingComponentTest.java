package com.github.tomek39856.hotel.manager;

import com.github.tomek39856.hotel.manager.infrastructure.Event;
import com.github.tomek39856.hotel.manager.infrastructure.EventPublisher;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;

public class EventPublishingComponentTest extends ComponentTest {
  @MockBean
  protected EventPublisher eventPublisher;
  @Captor
  protected ArgumentCaptor<Event> publishEventCaptor;
}
