package com.booking.agency.service.impl;

import com.booking.agency.dao.SlotDao;
import com.booking.agency.entity.SlotEntity;
import com.booking.agency.models.Slot;
import com.booking.agency.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SlotServiceImpl implements SlotService {
    private final SlotDao slotDao;

    @Autowired
    public SlotServiceImpl(SlotDao slotService) {
        this.slotDao = slotService;
    }

    @Override
    public Slot getSlotById(String slotID) {
        Optional<SlotEntity> slotEntity = slotDao.findById(slotID);
        if(!slotEntity.isPresent()) {
            throw new RuntimeException("Slot not found");
        }
        return Slot.buildFrom(slotEntity.get());
    }

    @Override
    public List<Slot> getAvailableSlotsForOperator(int operatorID, LocalDate date) {
        List<SlotEntity> slotEntities = slotDao.findAllAvailableSlotsForOperatorAndDate(operatorID, date);
        return slotEntities.stream()
                .map(Slot::buildFrom)
                .collect(Collectors.toList());
    }
}
