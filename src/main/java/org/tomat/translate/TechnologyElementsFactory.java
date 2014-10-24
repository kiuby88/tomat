package org.tomat.translate;

import org.tomat.agnostic.elements.*;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

/**
 * Created by Jose on 23/10/14.
 */
public interface TechnologyElementsFactory {

    public BrooklynServiceEntity buildTechnologyComponent(JBossAgnosticElement JBagnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException ;

    public BrooklynServiceEntity buildTechnologyComponent(WebAppAgnosticElement wAagnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException;

    public BrooklynServiceEntity buildTechnologyComponent(MySQLAgnosticElement mySQLagnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException ;

    public BrooklynServiceEntity buildTechnologyComponent(MySQLDataBaseAgnosticElement MySQLDBagnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException;
}
