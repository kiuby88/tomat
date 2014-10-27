package org.tomat.agnostic.elements;

import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.HttpAgnosticProperty;
import org.tomat.agnostic.properties.HttpsAgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.TechnologyElementsFactory;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class JBossAgnosticElement extends AgnosticElement {

      public final static String TYPE="JBossWebServer";

    //TODO delete the following declarations
    //Define the properties Of the Element
    //private final static String[] HTTP_PROPERTY = {"http", "httpport"};
    //private final static String[] HTTPS_PROPERTY = {"https", "httpsport"};

    public JBossAgnosticElement(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);

    }

    @Override
    public Map<String, Class<? extends AgnosticProperty>> getExpectedProperties(){
        //TODO delete the super of super.getExpectedProperties and check the test
        //TODO delete id and using a list of Class
        Map<String, Class<? extends AgnosticProperty>> map = super.getExpectedProperties();
        map.put("http", HttpAgnosticProperty.class);
        map.put("https", HttpsAgnosticProperty.class);
        return map;
    }

    @Override
    public TechnologyComponent buildTechnologyComponent(TechnologyElementsFactory factory)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return factory.buildTechnologyComponent(this);
    }

    @Override
    public  String getType() {
        return TYPE;
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
