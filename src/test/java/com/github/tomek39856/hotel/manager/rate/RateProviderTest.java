package com.github.tomek39856.hotel.manager.rate;

import com.github.tomek39856.hotel.manager.ComponentTest;
import com.github.tomek39856.hotel.manager.common.RoomType;
import com.github.tomek39856.hotel.manager.rate.dto.RoomRateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class RateProviderTest extends ComponentTest {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  @Autowired
  RoomRateRepository roomRateRepository;
  @Autowired
  RateProvider rateProvider;

  @BeforeEach
  void setUp() {
    roomRateRepository.clear();
  }

  @Test
  @DisplayName("should return rate")
  void shouldReturnRate() {
    // given:
    RoomType roomType = RoomType.STANDARD;
    LocalDate startDate = LocalDate.parse("1988-10-11", formatter);
    LocalDate endDate = LocalDate.parse("1988-10-15", formatter);
    roomRateRepository.save(new Rate(
            RoomType.STANDARD,
            LocalDate.parse("1988-10-11", formatter),
            LocalDate.parse("1988-10-15", formatter),
            BigDecimal.valueOf(100),
            getFixedClock("1998-10-11")
        )
    );

    // when:
    RoomRateDto result = rateProvider.findRateAt(roomType, startDate, endDate, Instant.now());

    // then:
    assertEquals(BigDecimal.valueOf(500), result.getSum());
  }

  private Clock getFixedClock(String s) {
    return Clock.fixed(LocalDate.parse(s, formatter).atTime(15, 00).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
  }
}