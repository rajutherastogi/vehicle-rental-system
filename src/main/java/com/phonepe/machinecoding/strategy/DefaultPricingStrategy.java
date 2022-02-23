package com.phonepe.machinecoding.strategy;

import com.phonepe.machinecoding.enums.VehicleType;
import com.phonepe.machinecoding.model.BranchPricePair;
import com.phonepe.machinecoding.model.Vehicle;
import com.phonepe.machinecoding.repository.FareRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DefaultPricingStrategy implements PricingStrategy {
    private final FareRepository fareRepository;

    public DefaultPricingStrategy(FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }


    @Override
    public Vehicle findBestPricedVehicle(final Map<String, List<Vehicle>> branchVehiclesMap, final VehicleType vehicleType) {
        List<BranchPricePair> branchPricePairs = fareRepository.orderBranchByPricing(vehicleType);
        List<Vehicle> bestPricedVehicles = new ArrayList<>();
        for(BranchPricePair branchPricePair: branchPricePairs){
            if(branchVehiclesMap.containsKey(branchPricePair.getBranch())){
                bestPricedVehicles.addAll(branchVehiclesMap.get(branchPricePair.getBranch()));
                break;
            }
        }
        return bestPricedVehicles.get(0);
    }
}
