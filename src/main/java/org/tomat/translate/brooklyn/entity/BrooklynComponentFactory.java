package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.components.*;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.translate.TechnologyComponentFactory;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

/**
 * Created by Jose on 23/10/14.
 */
public class BrooklynComponentFactory implements TechnologyComponentFactory {

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(JBossAgnosticComponent jBossAgnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return new JBossBrooklynService(jBossAgnosticComponent);
    }

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(JettyAgnosticComponent jettyAgnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return new JettyBrooklynService(jettyAgnosticComponent);
    }

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(TomcatAgnosticComponent tomcatAgnosticComponent) throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return new TomcatBrooklynService(tomcatAgnosticComponent);
    }

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(WebAppAgnosticComponent webAppAgnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return null;
    }

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(MySQLAgnosticComponent mySQLagnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return new MySQLBrooklynService(mySQLagnosticComponent);
    }

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(MySQLDataBaseAgnosticComponent MySQLDBagnosticComponent)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return null;
    }


}
