package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.JBossAgnosticElement;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.translate.brooklyn.entity.JBossBrooklynService;
import org.tomat.translate.brooklyn.entity.MySQLBrooklynService;

import java.util.List;

/**
 * Created by Jose on 23/10/14.
 */
public class WebAppBrooklynVisitorRelationConfiguration
        extends BrooklynVisitorRelationConfiguration {

    @Override
    public void visit(JBossBrooklynService jBossService,
                      AgnosticElement agnosticElement,
                      AgnosticGraph agnosticGraph) {
        configJBossDeploymentArtifacts(jBossService,agnosticElement);
    }

    private void configJBossDeploymentArtifacts(JBossBrooklynService jBossBrooklynService,
                                                AgnosticElement agnosticElement) {

        List<AgnosticDeploymentArtifact> deploymentArtifacts =
                agnosticElement.getAgnosticDeploymentArtifacts();
        if (deploymentArtifacts != null) {

            AgnosticDeploymentArtifact webApplicationDeploymentArtifact= deploymentArtifacts.get(0);
            String webApplicationDeploymentArtifactReference=
                    webApplicationDeploymentArtifact.getArtifactReferences().get(0);
            jBossBrooklynService.addConfigProperty("wars.root",
                    webApplicationDeploymentArtifactReference);
        }
    }

    @Override
    public void visit(MySQLBrooklynService mySQLService,
                      AgnosticElement agnosticElement,
                      AgnosticGraph agnosticGraph) {

    }
}
