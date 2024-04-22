package com.booking.agency.service.impl;

import com.booking.agency.dao.ServiceOperatorDao;
import com.booking.agency.entity.ServiceOperatorEntity;
import com.booking.agency.models.ServiceOperator;
import com.booking.agency.service.ServiceOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceOperatorServiceImpl implements ServiceOperatorService {
    private final ServiceOperatorDao serviceOperatorDao;

    @Autowired
    public ServiceOperatorServiceImpl(ServiceOperatorDao serviceOperatorDao) {
        this.serviceOperatorDao = serviceOperatorDao;
    }

    @Override
    public List<ServiceOperator> getAllServiceOperators() {
        List<ServiceOperatorEntity> serviceOperatorEntities = serviceOperatorDao.findAll();
        return serviceOperatorEntities.stream()
                .map(ServiceOperator::buildFrom)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceOperator getServiceOperator(int operatorID) {
        Optional<ServiceOperatorEntity> serviceOperatorEntity = serviceOperatorDao.findById(operatorID);
        if(!serviceOperatorEntity.isPresent()) {
            throw new RuntimeException("Service Operator not found");
        }
        return ServiceOperator.buildFrom(serviceOperatorEntity.get());
    }
}
