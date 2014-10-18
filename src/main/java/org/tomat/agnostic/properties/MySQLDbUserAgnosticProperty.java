package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class MySQLDbUserAgnosticProperty extends AgnosticProperty {

    private final static String[] DB_USER = {"DB_USER", "databaseUser", "database_user", "db_user"};

    public MySQLDbUserAgnosticProperty(String id, Map<String, String> properties){
        super(id, properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return DB_USER;
    }

}