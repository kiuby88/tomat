package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.AgnosticElementUtils;
import org.tomat.agnostic.elements.MySQLAgnosticElement;
import org.tomat.agnostic.elements.MySQLDataBaseAgnosticElement;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.agnostic.properties.*;
import org.tomat.translate.brooklyn.entity.JBossBrooklynService;
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
                      AgnosticElement agnosticElement,
                      AgnosticGraph agnosticGraph) {
        configJBossDeploymentArtifacts(jBossService, agnosticElement);
        configureDatabaseConnection(jBossService, agnosticElement, agnosticGraph);
    }

    private void configJBossDeploymentArtifacts(JBossBrooklynService jBossBrooklynService,
                                                AgnosticElement webAppAgnostic) {
        List<AgnosticDeploymentArtifact> deploymentArtifacts =
                webAppAgnostic.getAgnosticDeploymentArtifacts();
        if (deploymentArtifacts != null) {

            AgnosticDeploymentArtifact webApplicationDeploymentArtifact = deploymentArtifacts.get(0);
            String webApplicationDeploymentArtifactReference =
                    webApplicationDeploymentArtifact.getArtifactReferences().get(0);
            jBossBrooklynService.addConfigProperty("wars.root",
                    webApplicationDeploymentArtifactReference);
        }
    }




    private void configureDatabaseConnection(JBossBrooklynService jBossService,
                                             AgnosticElement webAppAgnostic,
                                             AgnosticGraph agnosticGraph) {

        if (AgnosticElementUtils
                .containsAValidPropertyByType(webAppAgnostic, DbConnectionNameAgnosticProperty.class)
                || agnosticGraph.containOutcomingRelationByType( webAppAgnostic,
                        MySQLDataBaseAgnosticElement.class)) {

            Map<String, String> databaseConnectionConfiguration = configureDatabaseConnectionParameters(jBossService, webAppAgnostic, agnosticGraph);
            jBossService.addConfigProperty("java.sysprops", databaseConnectionConfiguration);

        }
    }


    private Map<String, String> configureDatabaseConnectionParameters(JBossBrooklynService jBossService,
                                                                      AgnosticElement webAppAgnostic,
                                                                      AgnosticGraph agnosticGraph) {

        MySQLDataBaseAgnosticElement dataBaseAgnosticElement =
                (MySQLDataBaseAgnosticElement) agnosticGraph
                        .findAgnosticElementOutComingByType(
                        webAppAgnostic,
                        MySQLDataBaseAgnosticElement.class);

        String dbConnectionName = getPropertyValueOrNotCompleteValue(
                webAppAgnostic,
                DbConnectionNameAgnosticProperty.class,
                "&DB_CONNECTION_NAME");

        String dbName = getPropertyValueOrNotCompleteValue(
                dataBaseAgnosticElement,
                MySQLDbNameAgnosticProperty.class,
                "&DB_NAME");

        String dbUser = getPropertyValueOrNotCompleteValue(
                dataBaseAgnosticElement,
                MySQLDbUserAgnosticProperty.class,
                "&DB_USER");

        String dbPass = getPropertyValueOrNotCompleteValue(
                dataBaseAgnosticElement,
                MySQLDbPasswordAgnosticProperty.class,
                "&DB_PASSWORD");

        String mySQLUrl= getDbURL(dataBaseAgnosticElement, agnosticGraph);

        String connectionConfiguration="$brooklyn:formatString(\"jdbc:%s%s?user=%s\\&password=%s\", "
                + mySQLUrl+", "
                +"\""+dbName+"\", "
                +"\""+dbUser+"\", "
                +"\""+dbPass+"\")";
        Map<String, String> databaseConfiguration=new TreeMap<>();
        databaseConfiguration.put(dbConnectionName, connectionConfiguration);
        return databaseConfiguration;

    }


    private String getPropertyValueOrNotCompleteValue(AgnosticElement agnosticElement,
                                               Class<? extends AgnosticProperty>type,
                                               String valueIfNotComplete){
        AgnosticProperty agnosticProperty=
                AgnosticElementUtils.findPropertyByType(agnosticElement,type);

        if(agnosticProperty.isCompleted()){
            return agnosticProperty.getValue();
        }
        else{
            return valueIfNotComplete;
        }
    }

    private String getDbURL(MySQLDataBaseAgnosticElement dataBaseAgnosticElement,
            AgnosticGraph agnosticGraph) {
        MySQLAgnosticElement mySQLAgnosticElement=(MySQLAgnosticElement)
                agnosticGraph.findAgnosticElementOutComingByType(dataBaseAgnosticElement,
                        MySQLAgnosticElement.class);
        return getDbUrlValue(mySQLAgnosticElement);
    }

    private String getDbUrlValue(MySQLAgnosticElement mySQLAgnosticElement){

        String result=null;
        if((mySQLAgnosticElement!=null)
                &&(mySQLAgnosticElement.getId()!=null)){
            return "component(\""+ mySQLAgnosticElement.getId() +"\").attributeWhenReady(\"datastore.url\")";
        }
        else{
            result= "&URL_DATABASE";
        }
        return result;

    }










    @Override
    public void visit(MySQLBrooklynService mySQLService,
                      AgnosticElement agnosticElement,
                      AgnosticGraph agnosticGraph) {
    }
}
