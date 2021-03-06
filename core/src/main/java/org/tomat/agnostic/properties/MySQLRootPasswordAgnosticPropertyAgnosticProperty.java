package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Kiuby88 on 17/10/14.
 */
public class MySQLRootPasswordAgnosticPropertyAgnosticProperty extends AgnosticProperty implements MySQLDbConnectionParametersAgnosticProperty {

    private final static String[] DB_PASSWORD = {"DB_PASSWORD", "databasePassword",
            "database_password", "db_password", "rootpassword"};

    public MySQLRootPasswordAgnosticPropertyAgnosticProperty(Map<String, String> properties){
        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return DB_PASSWORD;
    }
}


