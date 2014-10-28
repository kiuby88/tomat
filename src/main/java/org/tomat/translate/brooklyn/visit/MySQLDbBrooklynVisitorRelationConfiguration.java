package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.translate.brooklyn.entity.JBossBrooklynService;
import org.tomat.translate.brooklyn.entity.JettyBrooklynService;
import org.tomat.translate.brooklyn.entity.MySQLBrooklynService;
import org.tomat.translate.brooklyn.entity.TomcatBrooklynService;

import java.util.List;

/**
 * Created by Jose on 23/10/14.
 */
public class MySQLDbBrooklynVisitorRelationConfiguration extends BrooklynVisitorRelationConfiguration{


    @Override
    public void visit(JBossBrooklynService jBossService,
                      AgnosticComponent agnosticComponent,
                      AgnosticGraph agnosticGraph) {
    }

    @Override
    public void visit(JettyBrooklynService jBossService, AgnosticComponent agnosticComponent, AgnosticGraph agnosticGraph) {

    }

    @Override
    public void visit(TomcatBrooklynService tomcatService, AgnosticComponent agnosticComponent, AgnosticGraph agnosticGraph) {

    }

    @Override
    public void visit(MySQLBrooklynService mySQLService,
                      AgnosticComponent agnosticComponent,
                      AgnosticGraph agnosticGraph) {

        configMySQLDbDeploymentArtifacts(mySQLService,agnosticComponent);
    }

    private void configMySQLDbDeploymentArtifacts(MySQLBrooklynService mySQLBrooklynService,
                                                  AgnosticComponent agnosticComponent) {

        List<AgnosticDeploymentArtifact> deploymentArtifacts =
                agnosticComponent.getAgnosticDeploymentArtifacts();
        if (deploymentArtifacts != null) {

            AgnosticDeploymentArtifact sqlApplicationDeploymentArtifact= deploymentArtifacts.get(0);
            String webApplicationDeploymentArtifactReference=
                    sqlApplicationDeploymentArtifact.getArtifactReferences().get(0);
            mySQLBrooklynService.addConfigProperty("datastore.creation.script.url",
                    webApplicationDeploymentArtifactReference);
        }
    }
}
