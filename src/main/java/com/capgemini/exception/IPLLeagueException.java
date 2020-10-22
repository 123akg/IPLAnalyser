package com.capgemini.exception;


public class IPLLeagueException extends RuntimeException {

    public enum ExceptionType
    {
    	IPL_FILE_PROBLEM,IPL_DATA_NOT_FOUND;
    }
    public ExceptionType eType;

    public IPLLeagueException(String message,ExceptionType eType) {
        super(message);
        this.eType=eType;
    }

}
