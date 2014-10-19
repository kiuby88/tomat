package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class MySQLDbPasswordAgnosticProperty extends AgnosticProperty {

    private final static String[] ROOT_PASSWORD = {"RootPassword", "PassWordRoot"};

    public MySQLDbPasswordAgnosticProperty( Map<String, String > properties){
        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return ROOT_PASSWORD;
    }
}
