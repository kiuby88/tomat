package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class DbConnectionNameAgnosticProperty extends AgnosticProperty {

    //TODO it is necessaty add more names for the name
    private final static String[] DB_CONNECTION_NAME = {"dbConnectionName"};

    public DbConnectionNameAgnosticProperty(Map<String, String> properties){

        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return DB_CONNECTION_NAME;
    }
}
