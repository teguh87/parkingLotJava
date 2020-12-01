package com.app.dkatalis.exception;

/**
 * @author Teguh Santoso
 */
public enum Code {

    PARKING_ALREADY_EXIST("Sorry Parking Already Created, It CAN NOT be again recreated."), PARKING_NOT_EXIST_ERROR(
            "Sorry, Car Parking Does not Exist"), INVALID_VALUE("{variable} value is incorrect"), INVALID_FILE(
            "Invalid File"), PROCESSING_ERROR("Processing Error "), INVALID_REQUEST("Invalid Request");

    private String message = "";

    /**
     * @param value
     */
    private Code(String message)
    {
        this.message = message;
    }

    /**
     * @return String
     */
    public String getMessage()
    {
        return message;
    }
}
