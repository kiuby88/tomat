package org.tomat.agnostic.elements;

import org.opentosca.model.tosca.TNodeTemplate;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class MySQLDataBaseAgnosticElement extends AgnosticElement {

    //Define the properties Of the Element

    private final static String[] DB_NAME = {"DBName", "databaseName", "database_name", "db_ame"};
    private final static String[] DB_USER = {"DB_USER", "databaseUser", "database_user", "db_user"};
    private final static String[] DB_PASSWORD = {"DB_PASSWORD", "databasePassword",
            "database_password", "db_password"};
    private final static String[] DB_PORT = {"DB_PORT", "databasePort", "database_port", "db_port"};

    private String dbName = null;
    private String dbUser = null;
    private String dbPAssword = null;
    private String dbPort = null;

    public MySQLDataBaseAgnosticElement(TNodeTemplate nodeTemplateSource) {
        super(nodeTemplateSource);
        parsingProperties();
    }

    private void parsingProperties() {
        Map<String, String> propertiesMap = getNodeTemplateProperties();
        initDbName(propertiesMap);
        initDbUser(propertiesMap);
        initDbPassword(propertiesMap);
        initDbPort(propertiesMap);
    }

    private void initDbName(Map<String, String> propertiesMap) {
        setDbName(AgnosticElementUtils.findValueMapUsingCollection(propertiesMap, Arrays.asList(DB_NAME)));
    }

    private void initDbUser(Map<String, String> propertiesMap) {
        setDbName(AgnosticElementUtils
                .findValueMapUsingCollection(propertiesMap, Arrays.asList(DB_USER)));
    }

    private void initDbPassword(Map<String, String> propertiesMap) {
        setDbName(AgnosticElementUtils
                .findValueMapUsingCollection(propertiesMap, Arrays.asList(DB_PASSWORD)));
    }

    private void initDbPort(Map<String, String> propertiesMap) {
        setDbName(AgnosticElementUtils
                .findValueMapUsingCollection(propertiesMap, Arrays.asList(DB_PORT)));
    }

    // <editor-fold desc="Getters and Setters">
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPAssword() {
        return dbPAssword;
    }

    public void setDbPAssword(String dbPAssword) {
        this.dbPAssword = dbPAssword;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }
    // </editor-fold>

}
