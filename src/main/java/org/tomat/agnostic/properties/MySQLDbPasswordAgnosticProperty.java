package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class MySQLDbPasswordAgnosticProperty extends AgnosticProperty {

    private final static String[] ROOT_PASSWORD = {"RootPassword", "PassWordRoot"};

    public MySQLDbPasswordAgnosticProperty(String id, Map<String, String> properties){
        super(id, properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return ROOT_PASSWORD;
    }
}
