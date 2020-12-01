package com.app.dkatalis.repositories;

import com.app.dkatalis.model.Vehicle;

import java.util.List;

public interface ParkingDataManager<T extends Vehicle> {
    public int parkCar(int level, T vehicle);

    public boolean leaveCar(int level, int slotNumber);

    public List<String> getStatus(int level);

    public int getSlotNoFromRegistrationNo(int level, String registrationNo);

    public int getAvailableSlotsCount(int level);

    public void doCleanup();

    public int leaveParkingLot(int level, String registerNo);
}
