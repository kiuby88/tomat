package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class MySQLDbPortAgnosticProperty extends AgnosticProperty {

    private final static String[] DB_PORT = {"DB_PORT", "databasePort", "database_port", "db_port"};
    public MySQLDbPortAgnosticProperty(String id, Map<String, String> properties){
        super(id, properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return DB_PORT;
    }
}
