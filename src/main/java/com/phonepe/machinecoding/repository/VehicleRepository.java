package com.phonepe.machinecoding.repository;

import com.phonepe.machinecoding.enums.VehicleType;
import com.phonepe.machinecoding.exception.BranchNotFoundException;
import com.phonepe.machinecoding.exception.VehicleNotFoundException;
import com.phonepe.machinecoding.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class VehicleRepository {
    private final Map<VehicleType, Map<String, List<Vehicle>>> vehicles;
    private final BranchRepository branchRepository;

    @Autowired
    public VehicleRepository(final BranchRepository branchRepository) {
        this.vehicles = new HashMap<VehicleType, Map<String, List<Vehicle>>>();
        this.branchRepository = branchRepository;
    }

    public void addVehicle(final String vehicleId, final VehicleType vehicleType, final String branch) {
        Vehicle vehicle = new Vehicle(vehicleId, vehicleType);
        if (!branchRepository.hasBranch(branch)) {
            throw new BranchNotFoundException();
        }
        Map<String, List<Vehicle>> vehicleTypeListMap = vehicles.get(vehicleType);
        if (Objects.nonNull(vehicleTypeListMap)) {
            List<Vehicle> vehicles = vehicleTypeListMap.get(branch);
            if (Objects.isNull(vehicles)) {
                vehicles = new ArrayList<>();
            }
            vehicles.add(vehicle);
            vehicleTypeListMap.put(branch, vehicles);
        } else {
            vehicleTypeListMap = new HashMap<>();
            vehicleTypeListMap.put(branch, Collections.singletonList(vehicle));
        }
        vehicles.put(vehicleType, vehicleTypeListMap);
    }

    public Map<String, List<Vehicle>> findAvailableVehicles(final VehicleType vehicleType, final LocalDate bookingStartTime, final LocalDate bookingEndTime) {
        Map<String, List<Vehicle>> branchVehicleMap = vehicles.get(vehicleType);
        if (Objects.isNull(branchVehicleMap)) {
            throw new VehicleNotFoundException();
        }
        Map<String, List<Vehicle>> map = new HashMap<>();
        for (Map.Entry<String, List<Vehicle>> entry : branchVehicleMap.entrySet()) {
            List<Vehicle> collect = entry.getValue().stream().filter(vehicle -> vehicle.getBookingStartTime() == null).collect(Collectors.toList());
            map.put(entry.getKey(), collect);
        }
        return map;
    }

    public Optional<Vehicle> bookVehicle(final Vehicle vehicle, final LocalDate bookingStartTime, final LocalDate bookingEndTime) {
        Map<String, List<Vehicle>> branchVehicleMap = vehicles.get(vehicle.getType());
        for (Map.Entry<String, List<Vehicle>> entry : branchVehicleMap.entrySet()) {
            Optional<Vehicle> vehicle1 = entry.getValue().stream().filter(v -> StringUtils.pathEquals(v.getId(), vehicle.getId())).findFirst();
            if (vehicle1.isPresent()) {
                vehicle1.get().setBookingStartTime(bookingStartTime);
                vehicle1.get().setBookingEndTime(bookingEndTime);
                return vehicle1;
            }
        }
        return Optional.empty();
    }

    public List<Vehicle> fetchAllVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicles.forEach((key, value) -> value.entrySet().forEach(entry1 -> vehicleList.addAll(entry1.getValue())));
        return vehicleList;
    }

}
