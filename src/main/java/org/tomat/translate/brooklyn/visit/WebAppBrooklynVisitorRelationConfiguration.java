package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.AgnosticComponentUtils;
import org.tomat.agnostic.components.MySQLAgnosticComponent;
import org.tomat.agnostic.components.MySQLDataBaseAgnosticComponent;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.agnostic.properties.*;
import org.tomat.translate.brooklyn.entity.JBossBrooklynService;
import org.tomat.translate.brooklyn.entity.JavaWebApplicationServerBrooklynService;
import org.tomat.translate.brooklyn.entity.JettyBrooklynService;
import org.tomat.translate.brooklyn.entity.MySQLBrooklynService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Jose on 23/10/14.
 */
public class WebAppBrooklynVisitorRelationConfiguration
        extends BrooklynVisitorRelationConfiguration {

    @Override
    public void visit(JBossBrooklynService jBossService,
                      AgnosticComponent agnosticComponent,
                      AgnosticGraph agnosticGraph) {
        configJBossDeploymentArtifacts(jBossService, agnosticComponent);
        configureDatabaseConnection(jBossService, agnosticComponent, agnosticGraph);
    }

    private void configJBossDeploymentArtifacts(JavaWebApplicationServerBrooklynService jWebAppServerBrooklynService,
                                                AgnosticComponent webAppAgnostic) {
        List<AgnosticDeploymentArtifact> deploymentArtifacts =
                webAppAgnostic.getAgnosticDeploymentArtifacts();
        if (deploymentArtifacts != null) {

            AgnosticDeploymentArtifact webApplicationDeploymentArtifact = deploymentArtifacts.get(0);
            String webApplicationDeploymentArtifactReference =
                    webApplicationDeploymentArtifact.getArtifactReferences().get(0);
            jWebAppServerBrooklynService.addConfigProperty("wars.root",
                    webApplicationDeploymentArtifactReference);
        }
    }

    private void configureDatabaseConnection(JavaWebApplicationServerBrooklynService jWebAppServerBrooklynService,
                                             AgnosticComponent webAppAgnostic,
                                             AgnosticGraph agnosticGraph) {

        if (AgnosticComponentUtils
                .containsAValidPropertyByType(webAppAgnostic, DbConnectionNameAgnosticProperty.class)
                || agnosticGraph.containOutcomingRelationByType(webAppAgnostic,
                MySQLDataBaseAgnosticComponent.class)) {

            Map<String, String> databaseConnectionConfiguration = configureDatabaseConnectionParameters(webAppAgnostic, agnosticGraph);
            jWebAppServerBrooklynService.addConfigProperty("java.sysprops", databaseConnectionConfiguration);

        }
    }


    private Map<String, String> configureDatabaseConnectionParameters(AgnosticComponent webAppAgnostic,
                                                                      AgnosticGraph agnosticGraph) {

        MySQLDataBaseAgnosticComponent dataBaseAgnosticComponent =
                (MySQLDataBaseAgnosticComponent) agnosticGraph
                        .findAgnosticComponentOutComingByType(
                                webAppAgnostic,
                                MySQLDataBaseAgnosticComponent.class);

        String dbConnectionName = getPropertyValueOrNotCompleteValue(
                webAppAgnostic,
                DbConnectionNameAgnosticProperty.class,
                "&DB_CONNECTION_NAME");

        String dbName = getPropertyValueOrNotCompleteValue(
                dataBaseAgnosticComponent,
                MySQLDbNameAgnosticProperty.class,
                "&DB_NAME");

        String dbUser = getPropertyValueOrNotCompleteValue(
                dataBaseAgnosticComponent,
                MySQLDbUserAgnosticProperty.class,
                "&DB_USER");

        String dbPass = getPropertyValueOrNotCompleteValue(
                dataBaseAgnosticComponent,
                MySQLDbPasswordAgnosticProperty.class,
                "&DB_PASSWORD");

        String mySQLUrl= getDbURL(dataBaseAgnosticComponent, agnosticGraph);

        String connectionConfiguration="$brooklyn:formatString(\"jdbc:%s%s?user=%s\\&password=%s\", "
                + mySQLUrl+", "
                +"\""+dbName+"\", "
                +"\""+dbUser+"\", "
                +"\""+dbPass+"\")";
        Map<String, String> databaseConfiguration=new TreeMap<>();
        databaseConfiguration.put(dbConnectionName, connectionConfiguration);
        return databaseConfiguration;
    }


    private String getPropertyValueOrNotCompleteValue(AgnosticComponent agnosticComponent,
                                               Class<? extends AgnosticProperty>type,
                                               String valueIfNotComplete){
        AgnosticProperty agnosticProperty=
                AgnosticComponentUtils.findPropertyByType(agnosticComponent, type);

        if(agnosticProperty.isCompleted()){
            return agnosticProperty.getValue();
        }
        else{
            return valueIfNotComplete;
        }
    }

    private String getDbURL(MySQLDataBaseAgnosticComponent dataBaseAgnosticComponent,
            AgnosticGraph agnosticGraph) {
        MySQLAgnosticComponent mySQLAgnosticComponent =(MySQLAgnosticComponent)
                agnosticGraph.findAgnosticComponentOutComingByType(dataBaseAgnosticComponent,
                        MySQLAgnosticComponent.class);
        return getDbUrlValue(mySQLAgnosticComponent);
    }

    private String getDbUrlValue(MySQLAgnosticComponent mySQLAgnosticComponent){

        String result=null;
        if((mySQLAgnosticComponent !=null)
                &&(mySQLAgnosticComponent.getId()!=null)){
            return "component(\""+ mySQLAgnosticComponent.getId() +
                    "\").attributeWhenReady(\"datastore.url\")";
        }
        else{
            result= "&URL_DATABASE";
        }
        return result;
    }

    @Override
    public void visit(JettyBrooklynService jettyService, AgnosticComponent agnosticComponent, AgnosticGraph agnosticGraph) {
        configJBossDeploymentArtifacts(jettyService, agnosticComponent);
        configureDatabaseConnection(jettyService, agnosticComponent, agnosticGraph);
    }


    @Override
    public void visit(MySQLBrooklynService mySQLService,
                      AgnosticComponent agnosticComponent,
                      AgnosticGraph agnosticGraph) {
    }
}
