package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Kiuby88 on 17/10/14.
 */
public class MySQLDbPasswordAgnosticProperty extends AgnosticProperty implements MySQLDbConnectionParametersAgnosticProperty {

    private final static String[] ROOT_PASSWORD = {"DBPassword", "databasePassword", "database_Password", "db_Password"};

    public MySQLDbPasswordAgnosticProperty(Map<String, String> properties){
        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return ROOT_PASSWORD;
    }
}
