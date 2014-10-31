package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Kiuby88 on 17/10/14.
 */
public class MySQLDbPortAgnosticPropertyAgnosticProperty extends AgnosticProperty implements MySQLDbConnectionParametersAgnosticProperty {

    private final static String[] DB_PORT = {"DBPORT", "databasePort", "database_port", "db_port"};
    public MySQLDbPortAgnosticPropertyAgnosticProperty(Map<String, String> properties){
        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return DB_PORT;
    }
}
