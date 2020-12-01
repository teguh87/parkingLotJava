package com.app.dkatalis.processor;

import com.app.dkatalis.constants.Prompt;
import com.app.dkatalis.exception.ProcessException;
import com.app.dkatalis.service.IService;

/**
 * @author Teguh Santoso
 */
public interface IProcessor {
    public void setService(IService service);

    public void execute(String action) throws ProcessException;

    public default boolean validate(String inputString)
    {
        // Split the input string to validate command and input value
        boolean valid = true;
        try
        {
            String[] inputs = inputString.split(" ");
            int params = Prompt.getCommandsParameterMap().get(inputs[0]);
            switch (inputs.length)
            {
                case 1:
                    if (params != 0) // e.g status -> inputs = 1
                        valid = false;
                    break;
                case 2:
                    if (params != 1) // create_parking_lot 6 -> inputs = 2
                        valid = false;
                    break;
                case 3:
                    if (params != 2) // leave KA-01-P-333 4 -> inputs = 3
                        valid = false;
                    break;
                default:
                    valid = false;
            }
        }
        catch (Exception e)
        {
            valid = false;
        }
        return valid;
    }
}
