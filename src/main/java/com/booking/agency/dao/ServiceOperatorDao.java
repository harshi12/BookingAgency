package com.booking.agency.dao;

import com.booking.agency.entity.ServiceOperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOperatorDao extends JpaRepository<ServiceOperatorEntity, Integer> {
}
