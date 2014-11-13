package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.MySQLDataBaseAgnosticComponent;
import org.tomat.agnostic.components.WebAppAgnosticComponent;
import org.tomat.translate.brooklyn.exceptions.BrooklynVisitorRelationConfigurationNotSupportedType;

/**
 * Created by Kiuby88 on 23/10/14.
 */


public class BrooklynVisitorRelationConfigurationProvider {
    final static String EXCEPTION_MESSAGE="The relation can not be configured by this element yet: ";
    public static BrooklynVisitorRelationConfiguration createVisit(AgnosticComponent agnosticComponent)
            throws BrooklynVisitorRelationConfigurationNotSupportedType {

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
                throw new BrooklynVisitorRelationConfigurationNotSupportedType(EXCEPTION_MESSAGE+
                agnosticComponent);
        }
        return result;
    }




}
