package org.tomat.translate.brooklyn.exceptions;

import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

/**
 * Created by Kiuby88 on 19/10/14.
 */
public class BrooklynVisitorRelationConfigurationNotSupportedType  extends Exception implements
        BrooklynException{

    public BrooklynVisitorRelationConfigurationNotSupportedType() {
        super();
    }

    public BrooklynVisitorRelationConfigurationNotSupportedType(String msg) {
        super(msg);
    }
}
