package com.phonepe.machinecoding.strategy;

import com.phonepe.machinecoding.enums.VehicleType;
import com.phonepe.machinecoding.model.Vehicle;

import java.util.List;
import java.util.Map;

public interface PricingStrategy {
    Vehicle findBestPricedVehicle(final Map<String, List<Vehicle>> branchVehiclesMap, final VehicleType vehicleType);
}
