package com.booking.agency.dao;

import com.booking.agency.entity.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface SlotDao extends JpaRepository<SlotEntity, String> {
    @Query("SELECT s FROM SlotEntity s " +
            "LEFT JOIN BookingEntity b ON s.id = b.slotID " +
            "AND b.serviceOperator.id = :operatorId " +
            "AND b.bookingDate = :bookingDate AND b.status = 'BOOKED' " +
            "WHERE b.id IS NULL")
    List<SlotEntity> findAllAvailableSlotsForOperatorAndDate(
            @Param("operatorId") int operatorId,
            @Param("bookingDate") LocalDate bookingDate);
}
