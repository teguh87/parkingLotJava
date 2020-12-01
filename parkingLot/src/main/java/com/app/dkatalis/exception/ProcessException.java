package com.app.dkatalis.exception;

/**
 * @author Teguh Santoso
 */
public class ProcessException extends Exception{

    private static final long serialVersionUID = -3370433670072632215L;

    private String		errorCode		= null;	// this will hold system defined error code
    private Object[]	errorParameters	= null;	// this will hold parameters for error code/message

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(String message, String errorCode, Object[] errorParameters) {
        super(message);
        this.errorCode = errorCode;
        this.errorParameters = errorParameters;
    }

    public ProcessException(String message, Throwable cause, String errorCode, Object[] errorParameters) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorParameters = errorParameters;
    }

    public ProcessException(Throwable cause, String errorCode, Object[] errorParameters) {
        super(cause);
        this.errorCode = errorCode;
        this.errorParameters = errorParameters;
    }

    public ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, Object[] errorParameters) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorParameters = errorParameters;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public Object[] getErrorParameters()
    {
        return errorParameters;
    }

    public void setErrorParameters(Object[] errorParameters)
    {
        this.errorParameters = errorParameters;
    }
}
