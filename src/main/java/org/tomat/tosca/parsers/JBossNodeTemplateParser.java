package org.tomat.tosca.parsers;

import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;

import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class JBossNodeTemplateParser extends NodeTemplateParser {

    //Define the properties Of the Element

    private final static String HTTP_PROPERTY="http";
    private final static String HTTPS_PROPERTY="https";
    private String httpPort=null;
    private String httpsPort=null;

    public JBossNodeTemplateParser(TNodeTemplate nodeTemplateSource){
        super(nodeTemplateSource);
    }

    private void parsingProperties(){
        Map<String, String > propertiesMap= DefinitionUtils.getProperties(this.getNodeTemplate());
        initHttpPort(propertiesMap);
        initHttpsPort(propertiesMap);
    }

    private void initHttpPort(Map<String, String > propertiesMap){
        if(propertiesMap.containsKey(HTTP_PROPERTY)){
            httpPort=propertiesMap.get(HTTP_PROPERTY);
        }
    }

    private void initHttpsPort(Map<String, String > propertiesMap){
        if(propertiesMap.containsKey(HTTPS_PROPERTY)){
            httpsPort=propertiesMap.get(HTTPS_PROPERTY);
        }
    }









}
