package com.app.dkatalis.service.impl;

import com.app.dkatalis.constants.Action;
import com.app.dkatalis.exception.Code;
import com.app.dkatalis.exception.ProcessException;
import com.app.dkatalis.model.Vehicle;
import com.app.dkatalis.repositories.ParkingDataManager;
import com.app.dkatalis.repositories.ParkingStrategy;
import com.app.dkatalis.repositories.impl.MemoryParkingManager;
import com.app.dkatalis.repositories.impl.NearestFirstParkingStrategy;
import com.app.dkatalis.service.ParkingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class has to be made singleton and used as service to be injected in
 * RequestProcessor
 *
 *
 * @author Teguh Santoso
 */
public class ParkingServiceImpl implements ParkingService {
    private ParkingDataManager<Vehicle> dataManager = null;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void createParkingLot(int level, int capacity) throws ProcessException
    {
        if (dataManager != null)
            throw new ProcessException(Code.PARKING_ALREADY_EXIST.getMessage());
        List<Integer> parkingLevels = new ArrayList<>();
        List<Integer> capacityList = new ArrayList<>();
        List<ParkingStrategy> parkingStrategies = new ArrayList<>();
        parkingLevels.add(level);
        capacityList.add(capacity);
        parkingStrategies.add(new NearestFirstParkingStrategy());
        this.dataManager = MemoryParkingManager.getInstance(parkingLevels, capacityList, parkingStrategies);
        System.out.println("Created parking lot with " + capacity + " slots");
    }

    @Override
    public Optional<Integer> park(int level, Vehicle vehicle) throws ProcessException
    {
        Optional<Integer> value = Optional.empty();
        lock.writeLock().lock();
        validateParkingLot();
        try
        {
            value = Optional.of(dataManager.parkCar(level, vehicle));
            if (value.get() == Action.NOT_AVAILABLE)
                System.out.println("Sorry, parking lot is full");
            else if (value.get() == Action.VEHICLE_ALREADY_EXIST)
                System.out.println("Sorry, vehicle is already parked.");
            else
                System.out.println("Allocated slot number: " + value.get());
        }
        catch (Exception e)
        {
            throw new ProcessException(Code.PROCESSING_ERROR.getMessage(), e);
        }
        finally
        {
            lock.writeLock().unlock();
        }
        return value;
    }

    /**
     * @throws ProcessException
     */
    private void validateParkingLot() throws ProcessException
    {
        if (dataManager == null)
        {
            throw new ProcessException(Code.PARKING_NOT_EXIST_ERROR.getMessage());
        }
    }

    @Override
    public void unPark(int level, int slotNumber) throws ProcessException
    {
        lock.writeLock().lock();
        validateParkingLot();
        try
        {

            if (dataManager.leaveCar(level, slotNumber))
                System.out.println("Slot number " + slotNumber + " is free");
            else
                System.out.println("Slot number is Empty Already.");
        }
        catch (Exception e)
        {
            throw new ProcessException(Code.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"), e);
        }
        finally
        {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void getStatus(int level) throws ProcessException
    {
        lock.readLock().lock();
        validateParkingLot();
        try
        {
            System.out.println("Slot No.\tRegistration No.");
            List<String> statusList = dataManager.getStatus(level);
            if (statusList.size() == 0)
                System.out.println("Sorry, parking lot is empty.");
            else
            {
                for (String statusSting : statusList)
                {
                    System.out.println(statusSting);
                }
            }
        }
        catch (Exception e)
        {
            throw new ProcessException(Code.PROCESSING_ERROR.getMessage(), e);
        }
        finally
        {
            lock.readLock().unlock();
        }
    }

    public Optional<Integer> getAvailableSlotsCount(int level) throws ProcessException
    {
        lock.readLock().lock();
        Optional<Integer> value = Optional.empty();
        validateParkingLot();
        try
        {
            value = Optional.of(dataManager.getAvailableSlotsCount(level));
        }
        catch (Exception e)
        {
            throw new ProcessException(Code.PROCESSING_ERROR.getMessage(), e);
        }
        finally
        {
            lock.readLock().unlock();
        }
        return value;
    }

    @Override
    public int getSlotNoFromRegistrationNo(int level, String registrationNo) throws ProcessException
    {
        int value = -1;
        lock.readLock().lock();
        validateParkingLot();
        try
        {
            value = dataManager.getSlotNoFromRegistrationNo(level, registrationNo);
            System.out.println(value != -1 ? value : "Not Found");
        }
        catch (Exception e)
        {
            throw new ProcessException(Code.PROCESSING_ERROR.getMessage(), e);
        }
        finally
        {
            lock.readLock().unlock();
        }
        return value;
    }

    @Override
    public void doCleanup()
    {
        if (dataManager != null)
            dataManager.doCleanup();
    }

    @Override
    public void leavePark(int level, String registerNo, int hours) throws ProcessException {
        lock.writeLock().lock();
        validateParkingLot();
        try
        {
            int slot = dataManager.leaveParkingLot(level, registerNo);
            if (slot > 0)
                System.out.println("Registration number " + registerNo + " with Slot Number " + slot + " with charge " + getCharge(hours));
            else
                System.out.println("Registration number " + registerNo + " Not found");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ProcessException(Code.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"), e);
        }
        finally
        {
            lock.writeLock().unlock();
        }
    }

    private int getCharge(int hours) {
        if (hours <= 2) return 10;
        else return 10 + (hours - 2) * 10;
    }
}
