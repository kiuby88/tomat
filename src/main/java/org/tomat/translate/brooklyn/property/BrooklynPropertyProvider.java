package org.tomat.translate.brooklyn.property;

import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.HttpAgnosticProperty;
import org.tomat.agnostic.properties.HttpsAgnosticProperty;
import org.tomat.agnostic.properties.PortAgnosticProperty;
import org.tomat.translate.TechnologyProperty;

/**
 * Created by Jose on 19/10/14.
 */
public class BrooklynPropertyProvider {

    //TODO rename the class using Translator if the ServiceProvider is renamedToo
    public static BrooklynProperty createBrooklynProperty(AgnosticProperty agnosticProperty){


        String propertyId=agnosticProperty.getId();
        String propertyValue=agnosticProperty.getValue();
        BrooklynProperty brooklynProperty=null;

        if(agnosticProperty instanceof PortAgnosticProperty){
            brooklynProperty= new PortBrooklynProperty(propertyId, propertyValue);
        }
        else{

        }
        return brooklynProperty;
    }

}
