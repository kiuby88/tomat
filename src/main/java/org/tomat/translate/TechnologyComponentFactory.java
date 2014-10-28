package org.tomat.translate;

import org.tomat.agnostic.components.*;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

/**
 * Created by Jose on 23/10/14.
 */
public interface TechnologyComponentFactory {

    public BrooklynServiceEntity buildTechnologyComponent(JBossAgnosticComponent jBossAgnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException ;

    public BrooklynServiceEntity buildTechnologyComponent(JettyAgnosticComponent jettyAgnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException;

    public BrooklynServiceEntity buildTechnologyComponent(WebAppAgnosticComponent webAppAgnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException;

    public BrooklynServiceEntity buildTechnologyComponent(MySQLAgnosticComponent mySQLagnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException ;

    public BrooklynServiceEntity buildTechnologyComponent(MySQLDataBaseAgnosticComponent MySQLDBagnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException;
}
