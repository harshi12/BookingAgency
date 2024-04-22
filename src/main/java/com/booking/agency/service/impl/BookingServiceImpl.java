package com.booking.agency.service.impl;

import com.booking.agency.dao.BookingDao;
import com.booking.agency.dto.request.CreateBookingRequestDTO;
import com.booking.agency.dto.request.UpdateBookingRequestDTO;
import com.booking.agency.dto.response.BookingResponseDTO;
import com.booking.agency.dto.response.OperatorAvailableSlotsResponseDTO;
import com.booking.agency.dto.response.OperatorBookedSlotsResponseDTO;
import com.booking.agency.entity.BookingEntity;
import com.booking.agency.entity.CustomerEntity;
import com.booking.agency.entity.ServiceOperatorEntity;
import com.booking.agency.enums.BookingStatus;
import com.booking.agency.models.ServiceOperator;
import com.booking.agency.models.Slot;
import com.booking.agency.service.BookingService;
import com.booking.agency.service.ServiceOperatorService;
import com.booking.agency.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingDao bookingDao;
    private final ServiceOperatorService serviceOperatorService;
    private final SlotService slotService;

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao, ServiceOperatorService serviceOperatorService,
                              SlotService slotService) {
        this.bookingDao = bookingDao;
        this.serviceOperatorService = serviceOperatorService;
        this.slotService = slotService;
    }

    @Override
    public BookingResponseDTO createBooking(CreateBookingRequestDTO requestDTO) {
        Map<Integer, Slot> operatorSlotMap = getAvailableSlotAndOperatorForNewBooking(requestDTO);
        if(operatorSlotMap.isEmpty()) {
            throw new RuntimeException("No available slots for the given date and time");
        }
        BookingEntity newBooking = createBookingEntityForNewBooking(requestDTO, operatorSlotMap);
        BookingEntity savedBooking = bookingDao.saveAndFlush(newBooking);
        return buildBookingResponseDTO(requestDTO, savedBooking);
    }

    @Override
    public BookingResponseDTO rescheduleBooking(UpdateBookingRequestDTO requestDTO) {
        BookingEntity bookingEntity = bookingDao.getReferenceById(requestDTO.getBookingID());
        Map<Integer, Slot> operatorSlotMap = getAvailableSlotsAndOperatorGivenDate(requestDTO.getBookingDate(),
                requestDTO.getBookingStartHour(), requestDTO.getBookingEndHour());
        if (operatorSlotMap.isEmpty()) {
            throw new RuntimeException("No available slots for the given date and time");
        }
        ServiceOperatorEntity serviceOperatorEntity = getServiceOperatorFromAvailableSlotMap(operatorSlotMap);
        Slot availableSlot = operatorSlotMap.get(serviceOperatorEntity.getId());
        int rowsAffected = bookingDao.rescheduleBooking(bookingEntity.getId(),
                requestDTO.getBookingDate(), serviceOperatorEntity, availableSlot.getId());
        if(rowsAffected != 1) {
            throw new RuntimeException("More than one booking affected");
        }
        BookingEntity updatedBookingEntity = bookingDao.getReferenceById(bookingEntity.getId());
        return buildBookingResponseDTO(updatedBookingEntity, availableSlot);
    }

    @Override
    public BookingResponseDTO cancelBooking(int bookingID) {
        BookingEntity bookingEntity = bookingDao.getReferenceById(bookingID);
        bookingEntity.setStatus(BookingStatus.CANCELLED.toString());
        BookingEntity updatedBookingEntity = bookingDao.saveAndFlush(bookingEntity);
        Slot bookedSlot = getSlotDetails(updatedBookingEntity);
        return buildBookingResponseDTO(updatedBookingEntity, bookedSlot);
    }

    @Override
    public BookingResponseDTO getBooking(int bookingID) {
        BookingEntity bookingEntity = bookingDao.getReferenceById(bookingID);
        Slot bookedSlot = getSlotDetails(bookingEntity);
        return buildBookingResponseDTO(bookingEntity, bookedSlot);
    }

    @Override
    public OperatorAvailableSlotsResponseDTO getAvailableSlots(int operatorID, LocalDate bookingDate) {
        ServiceOperator operator = serviceOperatorService.getServiceOperator(operatorID);
        List<Slot> availableSlots = slotService.getAvailableSlotsForOperator(operatorID, bookingDate);
        List<Slot> mergedSlots = mergeAvailableSlots(availableSlots);
        return OperatorAvailableSlotsResponseDTO.builder()
                .operatorID(operatorID)
                .availableSlots(mergedSlots.stream()
                        .map(Slot::mapToSlotDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public OperatorBookedSlotsResponseDTO getBookedSlots(int operatorID, LocalDate bookingDate) {
        List<BookingEntity> bookingEntities = bookingDao.getBookingByOperatorIDAndDate(operatorID, bookingDate);
        List<Slot> bookedSlots = getSlotDetails(bookingEntities);
        return OperatorBookedSlotsResponseDTO.builder()
                .operatorID(operatorID)
                .bookedSlots(bookedSlots.stream()
                        .map(Slot::mapToSlotDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private Map<Integer, Slot> getAvailableSlotAndOperatorForNewBooking(CreateBookingRequestDTO requestDTO) {
        Map<Integer, Slot> operatorSlotMap = new HashMap<>();
        if (requestDTO.getOperatorID().isPresent()) {
            Optional<Slot> slotOptional = getAvailableSlotsForOperatorGivenDate(requestDTO.getOperatorID().get(), requestDTO.getBookingDate(),
                    requestDTO.getBookingStartHour(), requestDTO.getBookingEndHour());
            if(slotOptional.isPresent()) {
                operatorSlotMap.put(requestDTO.getOperatorID().get(), slotOptional.get());
            }
        } else {
            operatorSlotMap = getAvailableSlotsAndOperatorGivenDate(requestDTO.getBookingDate(),
                    requestDTO.getBookingStartHour(), requestDTO.getBookingEndHour());
        }
        return operatorSlotMap;
    }

    private Map<Integer, Slot> getAvailableSlotsAndOperatorGivenDate(LocalDate date, int startHour, int endHour) {
        List<ServiceOperator> serviceOperators = serviceOperatorService.getAllServiceOperators();
        Map<Integer, Slot> operatorSlotMap = new HashMap<>();
        for (ServiceOperator serviceOperator : serviceOperators) {
            List<Slot> availableSlotForOperator = getAvailableSlotForOperator(serviceOperator.getId(), date);
            Optional<Slot> slotOptional = availableSlotForOperator.stream()
                    .filter(slot -> slot.getStartHour() == startHour && slot.getEndHour() == endHour)
                    .findFirst();
            if (slotOptional.isPresent()) {
                operatorSlotMap.put(serviceOperator.getId(), slotOptional.get());
                break;
            }
        }
        return operatorSlotMap;
    }

    private Optional<Slot> getAvailableSlotsForOperatorGivenDate(int operatorID, LocalDate date, int startHour, int endHour) {
        List<Slot> availableSlotForOperator = getAvailableSlotForOperator(operatorID, date);
        return availableSlotForOperator.stream()
                .filter(slot -> slot.getStartHour() == startHour && slot.getEndHour() == endHour)
                .findFirst();
    }

    private List<Slot> mergeAvailableSlots(List<Slot> slots){
        slots.sort(Comparator.comparingInt(Slot::getStartHour));
        List<Slot> mergedSlots = new ArrayList<>();
        Slot mergeSlot = null;
        for(Slot slot: slots){
            if(mergeSlot == null){
                mergeSlot = slot;
            }
            else if(slot.getStartHour() != mergeSlot.getEndHour()){
                mergedSlots.add(mergeSlot);
                mergeSlot = slot;
            } else{
                mergeSlot.mergeSlotHour(slot);
            }
        }
        if(mergeSlot != null){
            mergedSlots.add(mergeSlot);
        }
        return mergedSlots;
    }

    private List<Slot> getAvailableSlotForOperator(int operatorID, LocalDate date) {
        return slotService.getAvailableSlotsForOperator(operatorID, date);
    }

    private List<Slot> getSlotDetails(List<BookingEntity> bookingEntities) {
        return bookingEntities.stream()
                .map(this::getSlotDetails)
                .collect(Collectors.toList());
    }

    private Slot getSlotDetails(BookingEntity bookingEntity) {
        return slotService.getSlotById(bookingEntity.getSlotID());
    }

    private BookingResponseDTO buildBookingResponseDTO(BookingEntity bookingEntity, Slot slot) {
        return BookingResponseDTO.builder()
                .id(bookingEntity.getId())
                .bookingDate(bookingEntity.getBookingDate())
                .status(BookingStatus.fromString(bookingEntity.getStatus()))
                .serviceOperatorID(bookingEntity.getServiceOperator().getId())
                .customerID(bookingEntity.getCustomerEntity().getId())
                .bookingStartHour(slot.getStartHour())
                .bookingEndHour(slot.getEndHour())
                .build();
    }

    private BookingResponseDTO buildBookingResponseDTO(CreateBookingRequestDTO requestDTO, BookingEntity bookingEntity) {
        return BookingResponseDTO.builder()
                .id(bookingEntity.getId())
                .bookingDate(bookingEntity.getBookingDate())
                .status(BookingStatus.fromString(bookingEntity.getStatus()))
                .serviceOperatorID(bookingEntity.getServiceOperator().getId())
                .customerID(bookingEntity.getCustomerEntity().getId())
                .bookingStartHour(requestDTO.getBookingStartHour())
                .bookingEndHour(requestDTO.getBookingEndHour())
                .build();
    }

    private BookingEntity createBookingEntityForNewBooking(CreateBookingRequestDTO requestDTO,
                                                           Map<Integer, Slot> operatorSlotMap) {
        ServiceOperatorEntity serviceOperatorEntity = getServiceOperatorFromAvailableSlotMap(operatorSlotMap);
        Slot slot = operatorSlotMap.get(serviceOperatorEntity.getId());
        return BookingEntity.builder()
                .serviceOperator(serviceOperatorEntity)
                .customerEntity(getCustomerEntity(requestDTO.getCustomerID()))
                .slotID(slot.getId())
                .bookingDate(requestDTO.getBookingDate())
                .status(BookingStatus.BOOKED.toString())
                .bookingDate(requestDTO.getBookingDate())
                .build();
    }

    private ServiceOperatorEntity getServiceOperatorFromAvailableSlotMap(Map<Integer, Slot> operatorSlotMap) {
        Integer operatorID = operatorSlotMap.keySet().iterator().next();
        ServiceOperator serviceOperator = serviceOperatorService.getServiceOperator(operatorID);
        return serviceOperator.mapToEntity();
    }

    private CustomerEntity getCustomerEntity(int customerID) {
        return CustomerEntity.builder()
                .id(customerID)
                .build();
    }
}
