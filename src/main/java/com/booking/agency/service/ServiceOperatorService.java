package com.booking.agency.service;

import com.booking.agency.models.ServiceOperator;

import java.util.List;

public interface ServiceOperatorService {
    List<ServiceOperator> getAllServiceOperators();
    ServiceOperator getServiceOperator(int operatorID);
}
