package com.phonepe.machinecoding.service;

import com.phonepe.machinecoding.enums.VehicleType;
import com.phonepe.machinecoding.model.Vehicle;
import com.phonepe.machinecoding.repository.BranchRepository;
import com.phonepe.machinecoding.repository.FareRepository;
import com.phonepe.machinecoding.repository.VehicleRepository;
import com.phonepe.machinecoding.strategy.DefaultPricingStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

public class BookingServiceTest {
    private BranchRepository branchRepository = new BranchRepository();
    private FareRepository fareRepository = new FareRepository(branchRepository);
    private VehicleRepository vehicleRepository = new VehicleRepository(branchRepository);
    private DefaultPricingStrategy defaultPricingStrategy = new DefaultPricingStrategy(fareRepository);
    private BookingService bookingService = new BookingService(vehicleRepository, defaultPricingStrategy);

    @Test
    public void bookVehicle_shouldReturnSuccess(){
        branchRepository.addBranch("Vasanth Vihar");
        branchRepository.addBranch("Cyber City");
        vehicleRepository.addVehicle("BR012Z1024", VehicleType.SUV, "Vasanth Vihar");
        vehicleRepository.addVehicle("BR012Z1025", VehicleType.SUV, "Cyber City");
        fareRepository.allocatePrice("Vasanth Vihar", VehicleType.SUV, 150);
        fareRepository.allocatePrice("Cyber City", VehicleType.SUV, 120);
        Optional<Vehicle> vehicle = bookingService.bookVehicle(VehicleType.SUV, LocalDate.now(), LocalDate.now().plusDays(1));
        Assertions.assertTrue(vehicle.isPresent());
        Assertions.assertTrue("BR012Z1025".equals(vehicle.get().getId()));
    }
}
