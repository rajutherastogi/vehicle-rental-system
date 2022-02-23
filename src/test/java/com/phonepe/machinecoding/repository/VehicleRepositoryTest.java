package com.phonepe.machinecoding.repository;

import com.mongodb.assertions.Assertions;
import com.phonepe.machinecoding.enums.VehicleType;
import com.phonepe.machinecoding.exception.BranchNotFoundException;
import com.phonepe.machinecoding.model.Vehicle;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class VehicleRepositoryTest {

    private BranchRepository branchRepository = new BranchRepository();
    private VehicleRepository vehicleRepository = new VehicleRepository(branchRepository);

    @Test
    public void addVehicleWithoutExistingBranch_shouldThrowError() {
        try {
            vehicleRepository.addVehicle("BR012Z1024", VehicleType.SUV, "dummy");
        } catch (Exception e) {
            Assertions.assertTrue(e.getClass() == BranchNotFoundException.class);
        }
    }

    @Test
    public void addVehicleWithExistingBranch_shouldReturnSuccess() {
        branchRepository.addBranch("Vasanth Vihar");
        branchRepository.addBranch("Cyber City");
        vehicleRepository.addVehicle("BR012Z1024", VehicleType.SUV, "Vasanth Vihar");
        vehicleRepository.addVehicle("BR012Z1025", VehicleType.SUV, "Cyber City");
        Assertions.assertTrue(vehicleRepository.fetchAllVehicles().size() == 2);
    }

    @Test
    public void bookVehicle() {
        branchRepository.addBranch("Vasanth Vihar");
        branchRepository.addBranch("Cyber City");
        vehicleRepository.addVehicle("BR012Z1024", VehicleType.SUV, "Vasanth Vihar");
        vehicleRepository.addVehicle("BR012Z1025", VehicleType.SUV, "Cyber City");
        vehicleRepository.bookVehicle(new Vehicle("BR012Z1024", VehicleType.SUV), LocalDate.now(), LocalDate.now().plusDays(1));
        List<Vehicle> vehicles = vehicleRepository.fetchAllVehicles();
        boolean b1 = vehicles.get(1).getBookingStartTime() != null;
        boolean b2 = vehicles.get(1).getBookingEndTime() != null;
        Assertions.assertTrue(b1 && b2);
    }
}
