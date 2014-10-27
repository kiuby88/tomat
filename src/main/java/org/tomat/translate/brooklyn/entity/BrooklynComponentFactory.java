package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.elements.*;
import org.tomat.translate.TechnologyElementsFactory;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.entity.JBossBrooklynService;
import org.tomat.translate.brooklyn.entity.MySQLBrooklynService;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

/**
 * Created by Jose on 23/10/14.
 */
public class BrooklynComponentFactory implements TechnologyElementsFactory {

//    @Override
//    public BrooklynServiceEntity buildTechnologyComponent(AgnosticElement agnosticElement)
//            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
//        return BrooklynServiceEntityProvider.createBrooklynServiceEntity(agnosticElement);
//    }

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(JBossAgnosticElement JBagnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return new JBossBrooklynService(JBagnosticElement);
    }

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(WebAppAgnosticElement wAagnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return null;
    }

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(MySQLAgnosticElement mySQLagnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return new MySQLBrooklynService(mySQLagnosticElement);
    }

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(MySQLDataBaseAgnosticElement MySQLDBagnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return null;
    }


}
