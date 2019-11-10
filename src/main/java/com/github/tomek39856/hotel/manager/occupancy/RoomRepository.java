package com.github.tomek39856.hotel.manager.occupancy;

import com.github.tomek39856.hotel.manager.common.RoomType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
class RoomRepository {
  private final Map<String, Room> rooms = new ConcurrentHashMap<>();

  Set<Room> findAvailableRoomsByTypeAndAvailableBetween(RoomType roomType, LocalDate from, LocalDate to) {
    return rooms.values().stream()
        .filter(room -> roomType == room.getRoomType())
        .filter(room -> room.isAvailable(from, to))
        .collect(Collectors.toSet());
  }

  Optional<Room> findById(String id) {
    return Optional.ofNullable(rooms.get(id));
  }

  public void save(Room room) {
    rooms.put(room.getId(), room);
  }

  void clear() {
    this.rooms.clear();
  }

  Optional<Room> findByOccupancyReservationId(String reservationId) {
    return rooms.values().stream()
        .flatMap(room -> room.getOccupancies().stream())
        .filter(occupancy -> reservationId.equals(occupancy.getReservationId()))
        .map(occupancy -> occupancy.getRoom())
        .findAny();
  }
}
