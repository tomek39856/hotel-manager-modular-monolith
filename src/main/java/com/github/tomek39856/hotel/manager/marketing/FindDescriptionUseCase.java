package com.github.tomek39856.hotel.manager.marketing;

import com.github.tomek39856.hotel.manager.common.NotFoundException;
import com.github.tomek39856.hotel.manager.common.RoomType;
import com.github.tomek39856.hotel.manager.infrastructure.UseCase;
import com.github.tomek39856.hotel.manager.marketing.dto.RoomDescriptionDto;

@UseCase
class FindDescriptionUseCase {
  private final RoomDescriptionRepository roomDescriptionRepository;

  FindDescriptionUseCase(RoomDescriptionRepository roomDescriptionRepository) {
    this.roomDescriptionRepository = roomDescriptionRepository;
  }

  RoomDescriptionDto findDescription(RoomType roomType) {
    return roomDescriptionRepository.findOne(roomType).orElseThrow(NotFoundException::new).toApi();
  }
}
