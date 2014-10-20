package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class MySQLDbPasswordAgnosticPropertyAgnosticProperty extends AgnosticProperty implements MySQLDbConnectionParametersAgnosticProperty {

    private final static String[] ROOT_PASSWORD = {"RootPassword", "PassWordRoot"};

    public MySQLDbPasswordAgnosticPropertyAgnosticProperty(Map<String, String> properties){
        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return ROOT_PASSWORD;
    }
}
