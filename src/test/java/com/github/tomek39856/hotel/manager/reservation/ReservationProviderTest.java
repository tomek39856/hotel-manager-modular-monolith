package com.github.tomek39856.hotel.manager.reservation;

import com.github.tomek39856.hotel.manager.ComponentTest;
import com.github.tomek39856.hotel.manager.common.NotFoundException;
import com.github.tomek39856.hotel.manager.common.RoomType;
import com.github.tomek39856.hotel.manager.reservation.dto.RoomReservationDto;
import com.github.tomek39856.hotel.manager.resolver.DateTimeFormatterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DateTimeFormatterResolver.class)
class ReservationProviderTest extends ComponentTest {
  @Autowired
  ReservableRoomRepository reservableRoomRepository;
  @Autowired
  ReservationProvider reservationProvider;

  @BeforeEach
  void setUp() {
    reservableRoomRepository.clear();
  }

  @Test
  @DisplayName("should find reservation")
  void shouldFindReservation(DateTimeFormatter formatter) {
    // given:
    LocalDate startDate = LocalDate.parse("1988-10-11", formatter);
    LocalDate endDate = LocalDate.parse("1988-10-16", formatter);
    ReservableRoom reservedRoom = new ReservableRoom(RoomType.KING);
    RoomReservation reservation = reservedRoom.reserve(startDate, endDate);
    reservableRoomRepository.save(reservedRoom);

    // when:
    RoomReservationDto result = reservationProvider.provide(reservation.getId());

    assertEquals(reservation.getId(), result.getId());
    assertEquals(RoomType.KING, result.getRoomType());
    assertEquals(startDate, result.getStart());
    assertEquals(endDate, result.getEnd());
  }
}