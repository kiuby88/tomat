package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.MySQLDataBaseAgnosticComponent;
import org.tomat.agnostic.components.WebAppAgnosticComponent;

/**
 * Created by Jose on 23/10/14.
 */

//TODO, ask if using the packages name it enough to caled the class.
public class BrooklynVisitorRelationConfigurationProvider {

    public static BrooklynVisitorRelationConfiguration createVisit(AgnosticComponent agnosticComponent){

        BrooklynVisitorRelationConfiguration result=null;

        String type=agnosticComponent.getType();
        switch (type){
            case WebAppAgnosticComponent.TYPE:
                result = new WebAppBrooklynVisitorRelationConfiguration();
                break;

            case MySQLDataBaseAgnosticComponent.TYPE:
                result= new MySQLDbBrooklynVisitorRelationConfiguration();
                break;
            default:
                //TODO: throws a exception to indicate that this component
                //TODO does not be configured yet.
        }

        return result;

    }




}
