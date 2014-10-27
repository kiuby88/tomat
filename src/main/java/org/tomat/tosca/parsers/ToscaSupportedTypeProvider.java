package org.tomat.tosca.parsers;

public class ToscaSupportedTypeProvider {
    public final static String JBOSS_WEB_SERVER = "JBossWebServer";
    public final static String WEB_APPLICATION = "WebApplication";
    public final static String MySQL_DBMS = "MySQL";
    public final static String MySQL_DB = "MySQLDB";

    //TODO add logic to this method using java reflection
    public static boolean getSupportedType(String type) {
        return true;
    }


}
