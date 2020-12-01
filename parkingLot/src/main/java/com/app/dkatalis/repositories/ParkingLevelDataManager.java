package com.app.dkatalis.repositories;

import com.app.dkatalis.model.Vehicle;

import java.util.List;

public interface ParkingLevelDataManager<T extends Vehicle> {
    public int parkCar(T vehicle);

    public boolean leaveCar(int slotNumber);

    public List<String> getStatus();

    public int getSlotNoFromRegistrationNo(String registrationNo);

    public int getAvailableSlotsCount();

    public void doCleanUp();

    public int unParkCar(String registerNo);
}
