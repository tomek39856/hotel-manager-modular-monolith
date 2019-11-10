package com.github.tomek39856.hotel.manager.rate;

import com.github.tomek39856.hotel.manager.common.RoomType;
import com.github.tomek39856.hotel.manager.rate.dto.AvailableRoomTypeDto;
import com.github.tomek39856.hotel.manager.rate.dto.RoomRateDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
public class RateProvider {
  private final FindRateUseCase findRateUseCase;

  public RateProvider(FindRateUseCase findRateUseCase) {
    this.findRateUseCase = findRateUseCase;
  }

  public RoomRateDto findRateAt(RoomType roomType, LocalDate from, LocalDate to, Instant when) {
    return findRateUseCase.execute(new AvailableRoomTypeDto(roomType, from, to), when);
  }
}
