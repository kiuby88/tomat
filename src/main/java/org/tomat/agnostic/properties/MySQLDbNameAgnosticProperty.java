package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class MySQLDbNameAgnosticProperty extends AgnosticProperty {

    private final static String[] DB_NAME = {"DBName", "databaseName", "database_name", "db_ame"};

    public MySQLDbNameAgnosticProperty(String id, Map<String, String> properties){
        super(id, properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return DB_NAME;
    }
}



