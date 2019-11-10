package com.github.tomek39856.hotel.manager.itops;

import com.github.tomek39856.hotel.manager.EventPublishingComponentTest;
import com.github.tomek39856.hotel.manager.common.RoomType;
import com.github.tomek39856.hotel.manager.itops.event.HoldCreatedEvent;
import com.github.tomek39856.hotel.manager.itops.event.HoldFailedEvent;
import com.github.tomek39856.hotel.manager.payment.dto.CardDto;
import com.github.tomek39856.hotel.manager.payment.dto.PaymentInformationDto;
import com.github.tomek39856.hotel.manager.rate.RateProvider;
import com.github.tomek39856.hotel.manager.rate.dto.RoomRateDto;
import com.github.tomek39856.hotel.manager.reservation.ReservationProvider;
import com.github.tomek39856.hotel.manager.reservation.ReservationStatus;
import com.github.tomek39856.hotel.manager.reservation.dto.RoomReservationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateHoldUseCaseTest extends EventPublishingComponentTest {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  @Autowired
  CreateHoldUseCase createHoldUseCase;
  @MockBean
  ExternalCardService externalCardService;
  @MockBean
  RateProvider rateProvider;
  @MockBean
  ReservationProvider reservationProvider;

  @Test
  @DisplayName("should create hold and send event when payment was not proceeded before")
  void shouldCreateHoldAndSendEventWhenPaymentWasNotProceededBefore() {
    // given:
    LocalDate startDate = LocalDate.parse("1988-10-11", formatter);
    LocalDate endDate = LocalDate.parse("1988-10-16", formatter);
    String reservationId = UUID.randomUUID().toString();
    CardDto cardDto = new CardDto("Adam Nowak", "123456", LocalDate.now());
    Instant reservedAt = Instant.now();
    RoomReservationDto roomReservationDto = new RoomReservationDto(
        reservationId,
        startDate,
        endDate,
        reservedAt,
        ReservationStatus.ACTIVE,
        RoomType.STANDARD
    );
    RoomRateDto roomRateDto = new RoomRateDto(BigDecimal.valueOf(100));
    PaymentInformationDto paymentInformationDto = new PaymentInformationDto(UUID.randomUUID().toString(), reservationId, cardDto);
    Mockito.when(reservationProvider.provide(reservationId)).thenReturn(roomReservationDto);
    Mockito.when(rateProvider.findRateAt(RoomType.STANDARD, startDate, endDate, reservedAt)).thenReturn(roomRateDto);

    // when:
    createHoldUseCase.create(paymentInformationDto);

    // then:
    Mockito.verify(eventPublisher).publishEvent(publishEventCaptor.capture());
    assertTrue(publishEventCaptor.getValue() instanceof HoldCreatedEvent);
    assertEquals(paymentInformationDto.getId(), ((HoldCreatedEvent) publishEventCaptor.getValue()).getPaymentId());
  }

  @Test
  @DisplayName("should send event failed when external service failed")
  void shouldSendFailedEventWhenExternalFailed() {
    // given:
    LocalDate startDate = LocalDate.parse("1988-10-11", formatter);
    LocalDate endDate = LocalDate.parse("1988-10-16", formatter);
    String reservationId = UUID.randomUUID().toString();
    CardDto cardDto = new CardDto("Adam Nowak", "123456", LocalDate.now());
    Instant reservedAt = Instant.now();
    RoomReservationDto roomReservationDto = new RoomReservationDto(
        reservationId,
        startDate,
        endDate,
        reservedAt,
        ReservationStatus.ACTIVE,
        RoomType.STANDARD
    );
    RoomRateDto roomRateDto = new RoomRateDto(BigDecimal.valueOf(100));
    PaymentInformationDto paymentInformationDto = new PaymentInformationDto(UUID.randomUUID().toString(), reservationId, cardDto);
    Mockito.when(reservationProvider.provide(reservationId)).thenReturn(roomReservationDto);
    Mockito.when(rateProvider.findRateAt(RoomType.STANDARD, startDate, endDate, reservedAt)).thenReturn(roomRateDto);
    Mockito.doThrow(new RuntimeException()).when(externalCardService).hold(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

    // when:
    createHoldUseCase.create(paymentInformationDto);

    // then:
    Mockito.verify(eventPublisher).publishEvent(publishEventCaptor.capture());
    assertTrue(publishEventCaptor.getValue() instanceof HoldFailedEvent);
    assertEquals(paymentInformationDto.getId(), ((HoldFailedEvent) publishEventCaptor.getValue()).getPaymentId());
  }
}