package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.translate.TechnologyVisitorRelationConfiguration;
import org.tomat.translate.brooklyn.entity.JBossBrooklynService;
import org.tomat.translate.brooklyn.entity.MySQLBrooklynService;

/**
 * Created by Jose on 23/10/14.
 */
public abstract class BrooklynVisitorRelationConfiguration
        implements TechnologyVisitorRelationConfiguration{

    public abstract void visit(JBossBrooklynService jBossService,
                               AgnosticComponent agnosticComponent,
                               AgnosticGraph agnosticGraph);

    public abstract void visit(MySQLBrooklynService mySQLService,
                               AgnosticComponent agnosticComponent,
                               AgnosticGraph agnosticGraph);
}
