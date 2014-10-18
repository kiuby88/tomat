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

    private final static String[] DB_USER = {"DB_USER", "databaseUser", "database_user", "db_user"};
    private final static String[] DB_PASSWORD = {"DB_PASSWORD", "databasePassword",
            "database_password", "db_password"};
    private final static String[] DB_PORT = {"DB_PORT", "databasePort", "database_port", "db_port"};
}



