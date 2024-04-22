package com.booking.agency.models;

import com.booking.agency.entity.ServiceOperatorEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ServiceOperator{
    private int id;
    private String name;
    private String location;
    private String timezone;
    private String status;

    public static ServiceOperator buildFrom(ServiceOperatorEntity serviceOperatorEntity){
        return ServiceOperator.builder()
            .id(serviceOperatorEntity.getId())
            .name(serviceOperatorEntity.getName())
            .location(serviceOperatorEntity.getLocation())
            .timezone(serviceOperatorEntity.getTimezone())
            .status(serviceOperatorEntity.getStatus())
            .build();
    }

    public ServiceOperatorEntity mapToEntity(){
        return ServiceOperatorEntity.builder()
            .id(this.getId())
            .name(this.getName())
            .location(this.getLocation())
            .timezone(this.getTimezone())
            .status(this.getStatus())
            .build();
    }
}
