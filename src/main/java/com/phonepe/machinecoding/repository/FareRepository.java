package com.phonepe.machinecoding.repository;

import com.phonepe.machinecoding.enums.VehicleType;
import com.phonepe.machinecoding.exception.BranchNotFoundException;
import com.phonepe.machinecoding.exception.PriceAlreadyAllocated;
import com.phonepe.machinecoding.model.BranchPricePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FareRepository {
    private final Map<String, Map<VehicleType, Integer>> prices;
    private final BranchRepository branchRepository;

    @Autowired
    public FareRepository(final BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
        this.prices = new HashMap<>();
    }

    public void allocatePrice(final String branch, final VehicleType vehicleType, final int price){
        if(!branchRepository.hasBranch(branch)){
            throw new BranchNotFoundException();
        }
        Map<VehicleType, Integer> vehicleTypeIntegerMap = prices.get(branch);
        if(Objects.nonNull(vehicleTypeIntegerMap)){
            throw new PriceAlreadyAllocated();
        }
        vehicleTypeIntegerMap = new HashMap<>();
        vehicleTypeIntegerMap.put(vehicleType, price);
        prices.put(branch, vehicleTypeIntegerMap);
    }

    public List<BranchPricePair> orderBranchByPricing(final VehicleType vehicleType){
        List<BranchPricePair> list = new ArrayList<>();
        for(Map.Entry<String, Map<VehicleType, Integer>> entry: prices.entrySet()){
            list.add(new BranchPricePair(entry.getKey(), entry.getValue().get(vehicleType)));
        }

        list.sort(Comparator.comparingInt(BranchPricePair::getPrice));
        return list;
    }
}
