package org.tomat.agnostic.elements;

import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.MySQLRootPasswordAgnosticPropertyAgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;

import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class MySQLAgnosticElement extends AgnosticElement {

    public static final String  TYPE="MySQLDB";

    //TODO delete this property comment
   // private String rootPassword = null;

    public MySQLAgnosticElement(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);
    }
//TODO delete the following commets
//    private void parsingProperties() {
//        Map<String, String> propertiesMap = getNodeTemplateProperties();
//        initRootPassWord(propertiesMap);
//    }
//
//    private void initRootPassWord(Map<String, String> propertiesMap) {
//        rootPassword = AgnosticElementUtils
//                .findValueMapUsingCollection(propertiesMap, Arrays.asList(ROOT_PASSWORD));
//    }

    @Override
    public Map<String, Class<? extends AgnosticProperty>> getExpectedProperties() {
        Map<String, Class<? extends AgnosticProperty>> map = super.getExpectedProperties();
        map.put("rootPassword", MySQLRootPasswordAgnosticPropertyAgnosticProperty.class);
        return map;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    //TODO delete the following elements
    // <editor-fold desc="Getters and Setters">
//    public String getRootPassword() {
//        return rootPassword;
//    }
//
//    public void setRootPassword(String rootPassWord) {
//        this.rootPassword = rootPassWord;
//    }
    // </editor-fold>


}
