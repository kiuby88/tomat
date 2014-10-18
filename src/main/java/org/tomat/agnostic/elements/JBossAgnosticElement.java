package org.tomat.agnostic.elements;

import org.opentosca.model.tosca.TNodeTemplate;

import java.util.Map;

import org.tomat.agnostic.properties.*;
import org.tomat.exceptions.AgnosticPropertyException;

/**
 * Created by MariaC on 24/09/2014.
 */
public class JBossAgnosticElement extends AgnosticElement {

    //TODO delete the following declarations
    //Define the properties Of the Element
    //private final static String[] HTTP_PROPERTY = {"http", "httpport"};
    //private final static String[] HTTPS_PROPERTY = {"https", "httpsport"};

    public JBossAgnosticElement(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);
        //parsingProperties();
    }

//    private void parsingProperties() {
//        Map<String, String> propertiesMap = getNodeTemplateProperties();
//    }
//TODO delete the following comments
//    private void initHttpPort(Map<String, String> propertiesMap) {
//        httpPort = AgnosticElementUtils
//                .findValueMapUsingCollection(propertiesMap, Arrays.asList(HTTP_PROPERTY));
//    }
//
//    private void initHttpsPort(Map<String, String> propertiesMap) {
//        httpsPort = AgnosticElementUtils
//                .findValueMapUsingCollection(propertiesMap, Arrays.asList(HTTPS_PROPERTY));
//
//    }

    @Override
    public Map<String, Class<?>> getExpectedProperties(){
        Map<String, Class<?>> map = super.getExpectedProperties();
        map.put("http", HttpAgnosticProperty.class);
        map.put("https", HttpsAgnosticProperty.class);
        return map;
    }
//TODO delete the following commets
    // <editor-fold desc="Getters and Setters">
//    public String getHttpsPort() {
//        return httpsPort;
//    }
//
//    public void setHttpPort(String port) {
//        httpPort = port;
//    }
//
//    public void setHttpsPort(String port) {
//        httpsPort = port;
//    }
//
//    public String getHttpPort() {
//        return httpPort;
//    }
    // </editor-fold>


}
