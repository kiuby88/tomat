package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.translate.brooklyn.entity.JBossBrooklynService;
import org.tomat.translate.brooklyn.entity.MySQLBrooklynService;

import java.util.List;

/**
 * Created by Jose on 23/10/14.
 */
public class MySQLDbBrooklynVisitorRelationConfiguration extends BrooklynVisitorRelationConfiguration{


    @Override
    public void visit(JBossBrooklynService jBossService,
                      AgnosticElement agnosticElement,
                      AgnosticGraph agnosticGraph) {



    }

    @Override
    public void visit(MySQLBrooklynService mySQLService,
                      AgnosticElement agnosticElement,
                      AgnosticGraph agnosticGraph) {

        configMySQLDbDeploymentArtifacts(mySQLService,agnosticElement);
    }

    private void configMySQLDbDeploymentArtifacts(MySQLBrooklynService mySQLBrooklynService,
                                                AgnosticElement agnosticElement) {

        List<AgnosticDeploymentArtifact> deploymentArtifacts =
                agnosticElement.getAgnosticDeploymentArtifacts();
        if (deploymentArtifacts != null) {

            AgnosticDeploymentArtifact sqlApplicationDeploymentArtifact= deploymentArtifacts.get(0);
            String webApplicationDeploymentArtifactReference=
                    sqlApplicationDeploymentArtifact.getArtifactReferences().get(0);
            mySQLBrooklynService.addConfigProperty("datastore.creation.script.url",
                    webApplicationDeploymentArtifactReference);
        }
    }
}
