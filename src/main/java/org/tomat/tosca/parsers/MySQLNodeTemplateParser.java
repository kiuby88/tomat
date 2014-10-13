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

    private final static String[] ROOT_PASSWORD={"RootPassword", "PassWordRoot"};

    private String rootPassword=null;

    public MySQLNodeTemplateParser(TNodeTemplate nodeTemplateSource){
        super(nodeTemplateSource);
        parsingProperties();
    }

    private void parsingProperties(){
        Map<String, String > propertiesMap= getNodeTemplateProperties();
        initRootPassWord(propertiesMap);
    }

    private void initRootPassWord(Map<String, String > propertiesMap){
        rootPassword=NodeTemplateParserUtils.findValueMapUsingCollection(propertiesMap, Arrays.asList(ROOT_PASSWORD));
    }

    // <editor-fold desc="Getters and Setters">
    public String getRootPassword() {
        return rootPassword;
    }

    public void setRootPassword(String rootPassWord){
        this.rootPassword=rootPassWord;
    }
    // </editor-fold>









}
