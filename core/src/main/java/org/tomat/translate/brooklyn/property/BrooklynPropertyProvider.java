package org.tomat.translate.brooklyn.property;

import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.PortAgnosticProperty;

/**
 * Created by Kiuby88 on 19/10/14.
 */
public class BrooklynPropertyProvider {

    public static BrooklynProperty createBrooklynProperty(AgnosticProperty agnosticProperty){

        String propertyId=agnosticProperty.getId();
        String propertyValue=agnosticProperty.getValue();
        BrooklynProperty brooklynProperty=null;

        if(agnosticProperty instanceof PortAgnosticProperty){
            brooklynProperty= new PortBrooklynProperty(propertyId, propertyValue);
        }
        return brooklynProperty;
    }

}
