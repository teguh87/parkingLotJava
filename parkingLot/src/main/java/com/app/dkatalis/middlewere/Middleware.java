package com.app.dkatalis.middlewere;

import com.app.dkatalis.exception.Code;
import com.app.dkatalis.exception.ProcessException;
import com.app.dkatalis.processor.IProcessor;
import com.app.dkatalis.processor.RequestProcessor;
import com.app.dkatalis.service.impl.ParkingServiceImpl;

import java.io.*;

/**
 * @author Teguh Santoso
 */
public class Middleware {

    public static void process(String[] args){
        IProcessor processor = new RequestProcessor();
        processor.setService(new ParkingServiceImpl());
        BufferedReader bufferReader = null;
        String input = null;
        try
        {
            printUsage();
            switch (args.length)
            {
                case 0: // Interactive: command-line input/output
                {
                    System.out.println("Please Enter 'exit' to end Execution");
                    System.out.println("Input:");
                    while (true)
                    {
                        try
                        {
                            bufferReader = new BufferedReader(new InputStreamReader(System.in));
                            input = bufferReader.readLine().trim();
                            if (input.equalsIgnoreCase("exit"))
                            {
                                break;
                            }
                            else
                            {
                                if (processor.validate(input))
                                {
                                    try
                                    {
                                        processor.execute(input.trim());
                                    }
                                    catch (Exception e)
                                    {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                else
                                {
                                    printUsage();
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            throw new ProcessException(Code.INVALID_REQUEST.getMessage(), e);
                        }
                    }
                    break;
                }
                case 1:// File input/output
                {
                    File inputFile = new File(args[0]);
                    try
                    {
                        bufferReader = new BufferedReader(new FileReader(inputFile));
                        int lineNo = 1;
                        while ((input = bufferReader.readLine()) != null)
                        {
                            input = input.trim();
                            if (processor.validate(input))
                            {
                                try
                                {
                                    processor.execute(input);
                                }
                                catch (Exception e)
                                {
                                    System.out.println(e.getMessage());
                                }
                            }
                            else
                                System.out.println("Incorrect Command Found at line: " + lineNo + " ,Input: " + input);
                            lineNo++;
                        }
                    }
                    catch (Exception e)
                    {
                        throw new ProcessException(Code.INVALID_FILE.getMessage(), e);
                    }
                    break;
                }
                default:
                    System.out.println("Invalid input. Usage Style: java -jar <jar_file_path> <input_file_path>");
            }
        }
        catch (ProcessException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                if (bufferReader != null)
                    bufferReader.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    private static void printUsage() {
        StringBuffer buffer = new StringBuffer();
        buffer = buffer.append(
                "--------------Please Enter one of the below commands. {variable} to be replaced -----------------------")
                .append("\n");
        buffer = buffer.append("A) For creating parking lot of size n               ---> create_parking_lot {slots}")
                .append("\n");
        buffer = buffer
                .append("B) To park a car                                    ---> park <<car_number>> ")
                .append("\n");
        buffer = buffer.append("C) Remove(Unpark) car from parking                  ---> leave <<car_number>>  {hours}")
                .append("\n");
        buffer = buffer.append("D) Print status of parking slot                     ---> status").append("\n");

        buffer = buffer.append(
                "G) Get slot number for the given car number         ---> slot_number_for_registration_number {car_number}")
                .append("\n");
        System.out.println(buffer.toString());
    }
}
