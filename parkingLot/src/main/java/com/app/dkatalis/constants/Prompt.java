package com.app.dkatalis.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Teguh Santoso
 */
public class Prompt {
    private static volatile Map<String, Integer> commandsParameterMap = new HashMap<String, Integer>();

    static
    {
        commandsParameterMap.put(Action.CREATE_PARKING_LOT, 1);
        commandsParameterMap.put(Action.PARK, 1);
        commandsParameterMap.put(Action.LEAVE, 2);
        commandsParameterMap.put(Action.STATUS, 0);
        commandsParameterMap.put(Action.REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
        commandsParameterMap.put(Action.SLOTS_NUMBER_FOR_CARS_WITH_COLOR, 1);
        commandsParameterMap.put(Action.SLOTS_NUMBER_FOR_REG_NUMBER, 1);
    }

    /**
     * @return the commandsParameterMap
     */
    public static Map<String, Integer> getCommandsParameterMap()
    {
        return commandsParameterMap;
    }

    /**
     * @param commandsParameterMap
     *            the commandsParameterMap to set
     */
    public static void addCommand(String command, int parameterCount)
    {
        commandsParameterMap.put(command, parameterCount);
    }
}
