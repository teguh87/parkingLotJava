package com.app.dkatalis.service;

import com.app.dkatalis.exception.ProcessException;
import com.app.dkatalis.model.Vehicle;

import java.util.Optional;

/**
 * @author Teguh Santoso
 */
public interface ParkingService extends IService {

    public void createParkingLot(int level, int capacity) throws ProcessException;

    public Optional<Integer> park(int level, Vehicle vehicle) throws ProcessException;

    public void unPark(int level, int slotNumber) throws ProcessException;

    public void getStatus(int level) throws ProcessException;

    public Optional<Integer> getAvailableSlotsCount(int level) throws ProcessException;

    public int getSlotNoFromRegistrationNo(int level, String registrationNo) throws ProcessException;

    public void doCleanup();

    public void leavePark(int level, String registerNo, int hours) throws ProcessException;
}
