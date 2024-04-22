package com.booking.agency.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "service_operator_id", referencedColumnName = "id", nullable = false)
    private ServiceOperatorEntity serviceOperator;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private CustomerEntity customerEntity;

    //Note: Not creating a foreign key reference for slotID as we only need slot information till a slot of existing in db
    // Slot can be deleted later to free up db space
    @Column(nullable = false)
    private String slotID;

    @Setter
    @Column(nullable = false)
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate bookingDate;
}
