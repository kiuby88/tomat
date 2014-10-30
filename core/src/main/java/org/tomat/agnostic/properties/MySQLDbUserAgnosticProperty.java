package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class MySQLDbUserAgnosticProperty extends AgnosticProperty implements MySQLDbConnectionParametersAgnosticProperty {

    private final static String[] DB_USER = {"DBUSER", "databaseUser", "database_user", "db_user"};

    public MySQLDbUserAgnosticProperty(Map<String, String> properties){
        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return DB_USER;
    }

}
