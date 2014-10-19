package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.JBossAgnosticElement;
import org.tomat.agnostic.elements.MySQLAgnosticElement;
import org.tomat.agnostic.elements.MySQLDataBaseAgnosticElement;
import org.tomat.translate.brooklyn.exceptions.NotSupportedTypeByBrooklynException;

/**
 * Created by Jose on 19/10/14.
 */
//TODO translator rename the class using Translator e.g BrooklynAgnosticElementTranslator
public class BrooklynServiceEntityProvider {


    public static BrooklynServiceEntity createBrooklynServiceEntity(
            AgnosticElement agnosticElement) throws NotSupportedTypeByBrooklynException {

        BrooklynServiceEntity brooklynServiceEntity=null;
        String agnosticElementType= agnosticElement.getType();

        switch (agnosticElementType){
            case JBossAgnosticElement.TYPE:
                brooklynServiceEntity=new JBossBrooklynService(agnosticElement);
                break;

            case MySQLAgnosticElement.TYPE:
                break;

            case MySQLDataBaseAgnosticElement.TYPE:
                break;

            default:
                String exceptionMessage=agnosticElementType+" it is not supported by" +
                        "Brooklyn currently";
                throw new NotSupportedTypeByBrooklynException(exceptionMessage);
        }
        return brooklynServiceEntity;
    }
}
