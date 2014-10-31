package org.tomat.translate.brooklyn.exceptions;

import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

/**
 * Created by Kiuby88 on 19/10/14.
 */
public class AgnosticComponentTypeNotSupportedbyBrooklyException extends NotSupportedTypeByTechnologyException {

    public AgnosticComponentTypeNotSupportedbyBrooklyException() {
        super();
    }

    public AgnosticComponentTypeNotSupportedbyBrooklyException(String msg) {
        super(msg);
    }
}
