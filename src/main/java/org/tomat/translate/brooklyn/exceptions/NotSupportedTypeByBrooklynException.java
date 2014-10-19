package org.tomat.translate.brooklyn.exceptions;

import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

/**
 * Created by Jose on 19/10/14.
 */
public class NotSupportedTypeByBrooklynException extends NotSupportedTypeByTechnologyException {

    public NotSupportedTypeByBrooklynException() {
        super();
    }

    public NotSupportedTypeByBrooklynException(String msg) {
        super(msg);
    }
}
