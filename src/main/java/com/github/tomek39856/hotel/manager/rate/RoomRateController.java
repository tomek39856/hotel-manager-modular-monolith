package com.github.tomek39856.hotel.manager.rate;

import com.github.tomek39856.hotel.manager.rate.dto.AvailableRoomTypeDto;
import com.github.tomek39856.hotel.manager.rate.dto.RoomRateDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/rate")
class RoomRateController {
  private final FindRateUseCase findRateUseCase;

  RoomRateController(FindRateUseCase findRateUseCase) {
    this.findRateUseCase = findRateUseCase;
  }

  // on frontend roomTypeAvaliable event, the answer is rate for this room
  // in angular room search form will have output room types avaliablr and

  /*
  <rromSearch otput=roomTypesAvaliable> - emits roomTypAv
  <for roomTypeAvaliable in roomtypesAvaliable>
  <roomDesc><roomPrices>

   */
  @GetMapping
  RoomRateDto getCurrentAvailableRoomRate(AvailableRoomTypeDto availableRoomTypeDto) {
    return findRateUseCase.execute(availableRoomTypeDto, Instant.now());
  }

  /* historical data only for itops
  @GetMapping
   RoomRateDto getHistoricalAvailableRoomRate(@RequestParam("when") Instant when, AvailableRoomTypeDto type) {
    return findRateUseCase.execute(type);
  }

   */
}
