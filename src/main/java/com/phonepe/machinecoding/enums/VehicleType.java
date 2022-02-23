package com.phonepe.machinecoding.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VehicleType {
    SEDAN("Sedan"),
    HATCHBACK("HatchBack"),
    SUV("SUV");

    private String name;
}
