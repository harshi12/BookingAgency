package com.booking.agency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service_operator")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOperatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String location;
    private String timezone;
    private String status;
}
