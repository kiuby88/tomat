package org.tomat.exceptions;

/**
 * Created by MariaC on 25/09/2014.
 */
public class ExpectedToscaElementException extends Exception{
    public ExpectedToscaElementException(){
        super();
    }
    public ExpectedToscaElementException(String msg){
        super(msg);
    }
}
