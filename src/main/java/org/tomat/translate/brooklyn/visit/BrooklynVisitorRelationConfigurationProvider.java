package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.Agnostic;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.MySQLAgnosticElement;
import org.tomat.agnostic.elements.MySQLDataBaseAgnosticElement;
import org.tomat.agnostic.elements.WebAppAgnosticElement;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;

/**
 * Created by Jose on 23/10/14.
 */

//TODO, ask if using the packages name it enough to caled the class.
public class BrooklynVisitorRelationConfigurationProvider {

    public static BrooklynVisitorRelationConfiguration createVisit(AgnosticElement agnosticElement){

        BrooklynVisitorRelationConfiguration result=null;

        String type=agnosticElement.getType();
        switch (type){
            case WebAppAgnosticElement.TYPE:
                result = new WebAppBrooklynVisitorRelationConfiguration();
                break;

            case MySQLDataBaseAgnosticElement.TYPE:
                result= new MySQLDbBrooklynVisitorRelationConfiguration();
                break;
            default:
                //TODO: throws a exception to indicate that this element
                //TODO does not be configured yet.
        }

        return result;

    }




}
