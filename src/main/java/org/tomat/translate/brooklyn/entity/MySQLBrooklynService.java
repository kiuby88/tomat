package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.HttpAgnosticProperty;
import org.tomat.agnostic.properties.HttpsAgnosticProperty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jose on 19/10/14.
 */
//TODO could be a good idea move this class (and other Services to a new package named services
// TODO into .entity
public class MySQLBrooklynService extends BrooklynServiceEntity {

    private final static String SERVICE_TYPE="org.jboss";

    public MySQLBrooklynService(AgnosticElement agnosticElement) {
        super(agnosticElement);
    }

    @Override
    public Map<Class<? extends AgnosticProperty>, String > getSupportedAgnosticPropertiesAndBrooklynPropertyId(){

        Map<Class<? extends AgnosticProperty>, String > result=new HashMap<>();
        //result.put(HttpAgnosticProperty.class, "port.http");
        //result.put(HttpsAgnosticProperty.class, "port.https");
        return result;
    }

    @Override
    public String getServiceType() {
        return SERVICE_TYPE;
    }

    @Override
    public void configureRelations() {}

}
