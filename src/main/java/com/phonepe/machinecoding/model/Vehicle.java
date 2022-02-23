package com.phonepe.machinecoding.model;

import com.phonepe.machinecoding.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Vehicle {
    private String id;
    private VehicleType type;
    @Setter
    private LocalDate bookingStartTime;
    @Setter
    private LocalDate bookingEndTime;

    public Vehicle(final String id, final VehicleType type){
        this.id = id;
        this.type = type;
        bookingStartTime = null;
        bookingEndTime = null;
    }
}
