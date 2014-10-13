package org.tomat.tosca.parsers;

import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class MySQLNodeTemplateParser extends NodeTemplateParser {

    //Define the properties Of the Element

    private final static String[] HTTP_PROPERTY={"http", "httpd", "httpdport"};
    private final static String[] HTTPS_PROPERTY={"https", "httpsd", "httpsdport"};


    private String httpPort=null;
    private String httpsPort=null;

    public MySQLNodeTemplateParser(TNodeTemplate nodeTemplateSource){
        super(nodeTemplateSource);
        parsingProperties();
    }

    private void parsingProperties(){
        Map<String, String > propertiesMap= DefinitionUtils.getProperties(this.getNodeTemplate());
        initHttpPort(propertiesMap);
        initHttpsPort(propertiesMap);
    }

    private void initHttpPort(Map<String, String > propertiesMap){
        httpPort=NodeTemplateParser.findValueMapUsingCollection(propertiesMap, Arrays.asList(HTTP_PROPERTY));
    }

    private void initHttpsPort(Map<String, String > propertiesMap){
        httpsPort=NodeTemplateParser.findValueMapUsingCollection(propertiesMap, Arrays.asList(HTTPS_PROPERTY));
    }

    // <editor-fold desc="Getters and Setters">
    public String getHttpsPort() {
        return httpsPort;
    }

    public String getHttpPort() {
        return httpPort;
    }
    // </editor-fold>









}
