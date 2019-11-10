package com.github.tomek39856.hotel.manager.guest;

import com.github.tomek39856.hotel.manager.guest.dto.CreateGuestDto;
import com.github.tomek39856.hotel.manager.infrastructure.UseCase;

import java.util.Optional;

@UseCase
class CreateGuestUseCase {
  private final GuestRepository guestRepository;

  CreateGuestUseCase(GuestRepository guestRepository) {
    this.guestRepository = guestRepository;
  }

  void execute(CreateGuestDto createGuestDto) {
    Optional<Guest> guest = guestRepository.findOneByReservationId(createGuestDto.getReservationId());
    if (!guest.isPresent()) {
      guestRepository.save(Guest.ofDto(createGuestDto));
    }
  }
}
