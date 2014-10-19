package org.tomat.agnostic.elements;

import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.agnostic.properties.*;
import org.tomat.exceptions.AgnosticPropertyException;

import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class MySQLDataBaseAgnosticElement extends AgnosticElement {

    public final static String TYPE = "MySQL";

    //Define the properties Of the Element

    //TODO delete the following comments
//    private final static String[] DB_NAME = {"DBName", "databaseName", "database_name", "db_ame"};
//    private final static String[] DB_USER = {"DB_USER", "databaseUser", "database_user", "db_user"};
//    private final static String[] DB_PASSWORD = {"DB_PASSWORD", "databasePassword",
//            "database_password", "db_password"};
//    private final static String[] DB_PORT = {"DB_PORT", "databasePort", "database_port", "db_port"};
//
//    private String dbName = null;
//    private String dbUser = null;
//    private String dbPassword = null;
//    private String dbPort = null;

    public MySQLDataBaseAgnosticElement(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);
        //parsingProperties();
    }

//    private void parsingProperties() {
//        Map<String, String> propertiesMap = getNodeTemplateProperties();
//        initDbName(propertiesMap);
//        initDbUser(propertiesMap);
//        initDbPassword(propertiesMap);
//        initDbPort(propertiesMap);
//    }

//    private void initDbName(Map<String, String> propertiesMap) {
//        setDbName(AgnosticElementUtils
//                .findValueMapUsingCollection(propertiesMap, Arrays.asList(DB_NAME)));
//    }
//
//    private void initDbUser(Map<String, String> propertiesMap) {
//        setDbName(AgnosticElementUtils
//                .findValueMapUsingCollection(propertiesMap, Arrays.asList(DB_USER)));
//    }
//
//    private void initDbPassword(Map<String, String> propertiesMap) {
//        setDbName(AgnosticElementUtils
//                .findValueMapUsingCollection(propertiesMap, Arrays.asList(DB_PASSWORD)));
//    }
//
//    private void initDbPort(Map<String, String> propertiesMap) {
//        setDbName(AgnosticElementUtils
//                .findValueMapUsingCollection(propertiesMap, Arrays.asList(DB_PORT)));
//    }

    @Override
    public Map<String, Class<? extends AgnosticProperty>> getExpectedProperties() {
        Map<String, Class<? extends AgnosticProperty>> map = super.getExpectedProperties();
        map.put("dbName", MySQLDbNameAgnosticProperty.class);
        map.put("dbUser", MySQLDbUserAgnosticProperty.class);
        map.put("dbPassword", MySQLDbPasswordAgnosticProperty.class);
        map.put("dbPort", MySQLDbPortAgnosticProperty.class);
        return map;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    // <editor-fold desc="Getters and Setters">
//    public String getDbName() {
//        return dbName;
//    }
//
//    public void setDbName(String dbName) {
//        this.dbName = dbName;
//    }
//
//    public String getDbUser() {
//        return dbUser;
//    }
//
//    public void setDbUser(String dbUser) {
//        this.dbUser = dbUser;
//    }
//
//    public String getDbPassword() {
//        return dbPassword;
//    }
//
//    public void setDbPassword(String dbPassword) {
//        this.dbPassword = dbPassword;
//    }
//
//    public String getDbPort() {
//        return dbPort;
//    }
//
//    public void setDbPort(String dbPort) {
//        this.dbPort = dbPort;
//    }
    // </editor-fold>

}
