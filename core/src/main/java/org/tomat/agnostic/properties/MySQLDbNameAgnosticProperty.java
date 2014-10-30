package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class MySQLDbNameAgnosticProperty extends AgnosticProperty implements MySQLDbConnectionParametersAgnosticProperty {

    private final static String[] DB_NAME = {"DBName", "databaseName", "database_name", "db_name"};

    public MySQLDbNameAgnosticProperty(Map<String, String> properties){
        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return DB_NAME;
    }
}



