package com.phonepe.machinecoding.service;

import com.phonepe.machinecoding.enums.VehicleType;
import com.phonepe.machinecoding.exception.VehicleNotFoundException;
import com.phonepe.machinecoding.model.Vehicle;
import com.phonepe.machinecoding.repository.VehicleRepository;
import com.phonepe.machinecoding.strategy.PricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookingService {
    private final VehicleRepository vehicleRepository;
    private final PricingStrategy pricingStrategy;

    @Autowired
    public BookingService(VehicleRepository vehicleRepository, PricingStrategy pricingStrategy) {
        this.vehicleRepository = vehicleRepository;
        this.pricingStrategy = pricingStrategy;
    }

    public Optional<Vehicle> bookVehicle(final VehicleType vehicleType, final LocalDate bookingStartTime, final LocalDate bookingEndTime) {
        // Find available vehicles
        Map<String, List<Vehicle>> availableVehicles = vehicleRepository.findAvailableVehicles(vehicleType, bookingStartTime, bookingEndTime);
        // Find best priced vehicle
        if(Objects.isNull(availableVehicles) || availableVehicles.isEmpty()){
            throw new VehicleNotFoundException();
        }
        Vehicle bestPricedVehicle = pricingStrategy.findBestPricedVehicle(availableVehicles, vehicleType);
        return vehicleRepository.bookVehicle(bestPricedVehicle, bookingStartTime, bookingEndTime);
    }

}
