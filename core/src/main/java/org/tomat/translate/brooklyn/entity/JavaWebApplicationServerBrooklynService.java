package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.HttpAgnosticProperty;
import org.tomat.agnostic.properties.HttpsAgnosticProperty;

import java.util.Map;

/**
 * Created by Kiuby88 on 19/10/14.
 */
//TODO could be a good idea move this class (and other Services to a new package named services
// TODO into .entity
public abstract class JavaWebApplicationServerBrooklynService extends BrooklynServiceEntity {

    private final static String SERVICE_TYPE="brooklyn.entity.webapp.WebApp";


    public JavaWebApplicationServerBrooklynService(AgnosticComponent agnosticComponent) {
        super(agnosticComponent);
        //setServiceType(SERVICE_TYPE);
    }

    @Override
    public Map<Class<? extends AgnosticProperty>, String > getSupportedAgnosticPropertiesAndBrooklynPropertyId(){
        Map<Class<? extends AgnosticProperty>, String > result=
                super.getSupportedAgnosticPropertiesAndBrooklynPropertyId();
        result.put(HttpAgnosticProperty.class, "port.http");
        result.put(HttpsAgnosticProperty.class, "port.https");
        return result;
    }

}
