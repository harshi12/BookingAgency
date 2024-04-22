package com.booking.agency.dao;

import com.booking.agency.entity.BookingEntity;
import com.booking.agency.entity.ServiceOperatorEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingDao extends JpaRepository<BookingEntity, Integer>{

    @Modifying
    @Query("update BookingEntity b set b.status = ?2 where b.id = ?1")
    BookingEntity updateBookingStatus(int id, String status);

    @Modifying
    @Transactional
    @Query("update BookingEntity b set b.bookingDate = :bookingDate, b.slotID = :slotID, " +
            "b.serviceOperator = :serviceOperator where b.id = :bookingID")
    int rescheduleBooking(@Param("bookingID") int id, @Param("bookingDate") LocalDate date,
                                    @Param("serviceOperator")ServiceOperatorEntity serviceOperatorEntity,
                                    @Param("slotID") String slotID);

    @Query("select b from BookingEntity b where b.serviceOperator.id = :operatorID and b.bookingDate = :bookingDate " +
            "and b.status = 'BOOKED'")
    List<BookingEntity> getBookingByOperatorIDAndDate(@Param("operatorID") int operatorID,
                                                      @Param("bookingDate") LocalDate date);
}
