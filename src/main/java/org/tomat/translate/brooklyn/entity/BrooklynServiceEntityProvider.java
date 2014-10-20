package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.elements.*;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

/**
 * Created by Jose on 19/10/14.
 */
//TODO translator rename the class using Translator e.g BrooklynAgnosticElementTranslator
public class BrooklynServiceEntityProvider {


    public static BrooklynServiceEntity createBrooklynServiceEntity(
            AgnosticElement agnosticElement) throws AgnosticComponentTypeNotSupportedbyBrooklyException {

        BrooklynServiceEntity brooklynServiceEntity=null;
        String agnosticElementType= agnosticElement.getType();

        switch (agnosticElementType){

            case JBossAgnosticElement.TYPE:
                brooklynServiceEntity=new JBossBrooklynService(agnosticElement);
                break;

            case MySQLAgnosticElement.TYPE:
                brooklynServiceEntity=new MySQLBrooklynService(agnosticElement);
                break;

            case MySQLDataBaseAgnosticElement.TYPE:
                break;

            case WebAppAgnosticElement.TYPE:
                break;

            default:
                String exceptionMessage=agnosticElementType+" it is not supported by " +
                        "Brooklyn currently";
                throw new AgnosticComponentTypeNotSupportedbyBrooklyException(exceptionMessage);
        }
        return brooklynServiceEntity;
    }
}
