package com.github.tomek39856.hotel.manager.reservation;

import com.github.tomek39856.hotel.manager.common.RoomType;
import com.github.tomek39856.hotel.manager.reservation.dto.SearchParametersDto;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class FindFreeRoomsUseCase {
    private final ReservableRoomRepository reservableRoomRepository;

    FindFreeRoomsUseCase(ReservableRoomRepository reservableRoomRepository) {
        this.reservableRoomRepository = reservableRoomRepository;
    }

    Set<RoomType> execute(SearchParametersDto searchParameters) {
        return reservableRoomRepository.findAvailableRoomTypes(searchParameters.getFrom(), searchParameters.getTo());
    }
}
