package com.phonepe.machinecoding.repository;

import com.mongodb.assertions.Assertions;
import com.phonepe.machinecoding.enums.VehicleType;
import com.phonepe.machinecoding.model.BranchPricePair;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FareRepositoryTest {
    private BranchRepository branchRepository = new BranchRepository();
    private VehicleRepository vehicleRepository = new VehicleRepository(branchRepository);
    private FareRepository fareRepository = new FareRepository(branchRepository);

    @Test
    public void allocateNewFare_shouldReturnSuccess(){
        branchRepository.addBranch("Vasanth Vihar");
        branchRepository.addBranch("Cyber City");
        vehicleRepository.addVehicle("BR012Z1024", VehicleType.SUV, "Vasanth Vihar");
        vehicleRepository.addVehicle("BR012Z1025", VehicleType.SUV, "Cyber City");
        fareRepository.allocatePrice("Vasanth Vihar", VehicleType.SUV, 150);
        fareRepository.allocatePrice("Cyber City", VehicleType.SUV, 120);
        List<BranchPricePair> branchPricePairs = fareRepository.orderBranchByPricing(VehicleType.SUV);
        Assertions.assertTrue(branchPricePairs.size() == 2);
        Assertions.assertTrue(branchPricePairs.get(0).getPrice() == 120);
    }
}
