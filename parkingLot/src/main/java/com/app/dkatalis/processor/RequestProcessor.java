package com.app.dkatalis.processor;

import com.app.dkatalis.constants.Action;
import com.app.dkatalis.exception.Code;
import com.app.dkatalis.exception.ProcessException;
import com.app.dkatalis.model.Car;
import com.app.dkatalis.service.IService;
import com.app.dkatalis.service.ParkingService;

/**
 * @author Teguh Santoso
 */
public class RequestProcessor implements IProcessor {

    private ParkingService parkingService;

    public void setParkingService(ParkingService parkingService) throws ProcessException
    {
        this.parkingService = parkingService;
    }

    @Override
    public void execute(String input) throws ProcessException {
        int level = 1;
        String[] inputs = input.split(" ");
        String key = inputs[0];
        switch (key)
        {
            case Action.CREATE_PARKING_LOT:
                try
                {
                    int capacity = Integer.parseInt(inputs[1]);
                    parkingService.createParkingLot(level, capacity);
                }
                catch (NumberFormatException e)
                {
                    throw new ProcessException(Code.INVALID_VALUE.getMessage().replace("{variable}", "capacity"));
                }
                break;
            case Action.PARK:
                parkingService.park(level, new Car(inputs[1]));
                break;
            case Action.LEAVE:
                try
                {
                    String registerNo = (String)inputs[1];
                    int hours = Integer.valueOf(inputs[2]);
                    parkingService.leavePark(level, registerNo, hours);
                }
                catch (NumberFormatException e)
                {
                    throw new ProcessException(
                            Code.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"));
                }
                break;
            case Action.STATUS:
                parkingService.getStatus(level);
                break;
            case Action.SLOTS_NUMBER_FOR_REG_NUMBER:
                parkingService.getSlotNoFromRegistrationNo(level, inputs[1]);
                break;
            default:
                break;
        }
    }

    @Override
    public void setService(IService service)
    {
        this.parkingService = (ParkingService) service;
    }
}
