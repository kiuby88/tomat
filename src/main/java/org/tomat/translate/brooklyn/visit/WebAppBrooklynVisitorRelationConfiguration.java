package org.tomat.translate.brooklyn.visit;

import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.elements.AgnosticElement;
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

        if (containsAValidPropertyByType(webAppAgnostic, DbConnectionNameAgnosticProperty.class)
                || containOutcomingRelationByType( webAppAgnostic,
                        agnosticGraph, MySQLDataBaseAgnosticElement.class)) {

            Map<String, String> databaseConnectionConfiguration = configureDatabaseConnectionParameters(jBossService, webAppAgnostic, agnosticGraph);
            jBossService.addConfigProperty("java.sysprops", databaseConnectionConfiguration);

        }
    }


    private Map<String, String> configureDatabaseConnectionParameters(JBossBrooklynService jBossService,
                                                                      AgnosticElement webAppAgnostic,
                                                                      AgnosticGraph agnosticGraph) {

        MySQLDataBaseAgnosticElement dataBaseAgnosticElement =
                (MySQLDataBaseAgnosticElement) findAgnosticElementOutComingByType(
                        webAppAgnostic,
                        agnosticGraph,
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
                findPropertyByType(
                        agnosticElement,
                        type);

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
                findAgnosticElementOutComingByType(dataBaseAgnosticElement,
                        agnosticGraph,
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


    private boolean containsAValidPropertyByType(AgnosticElement webApplication,
                                                 Class<? extends AgnosticProperty> type) {
        AgnosticProperty property =
                findPropertyByType(webApplication, type);
        return property != null && property.isCompleted();
    }

    private AgnosticProperty findPropertyByType(AgnosticElement agnosticElement,
                                                Class<? extends AgnosticProperty> type) {
        List<AgnosticProperty> properties = agnosticElement.getProperties();
        return findPropertyByType(properties, type);
    }

    private AgnosticProperty findPropertyByType(List<AgnosticProperty> properties, Class<? extends AgnosticProperty> type) {
        AgnosticProperty result = null;
        if ((properties != null) && (!properties.isEmpty())) {
            for (AgnosticProperty property : properties) {
                if (type.isAssignableFrom(property.getClass())) {
                    return property;
                }
            }
        }
        return result;
    }

    private boolean containOutcomingRelationByType(AgnosticElement webAppAgnostic,
                                                   AgnosticGraph agnosticGraph,
                                                   Class<? extends AgnosticElement> type) {
        return findAgnosticElementOutComingByType(webAppAgnostic, agnosticGraph, type) != null;
    }

    private AgnosticElement findAgnosticElementOutComingByType(AgnosticElement agnosticElement,
                                                              AgnosticGraph agnosticGraph,
                                                              Class<? extends AgnosticElement> type) {
        List<AgnosticElement> outcomingVertex = agnosticGraph
                .getOutcomigngVertexOf(agnosticElement);
        return findAgnosticElementOutComingByType(outcomingVertex, type);
    }

    private AgnosticElement findAgnosticElementOutComingByType(List<AgnosticElement> outcomigngVertex,
                                                              Class<? extends AgnosticElement> type) {
        AgnosticElement result = null;
        if ((outcomigngVertex != null) && (!outcomigngVertex.isEmpty())) {
            for (AgnosticElement agnosticElement : outcomigngVertex) {
                if (type.isAssignableFrom(agnosticElement.getClass())) {
                    result =  agnosticElement;
                }
            }
        }
        return result;
    }








    @Override
    public void visit(MySQLBrooklynService mySQLService,
                      AgnosticElement agnosticElement,
                      AgnosticGraph agnosticGraph) {
    }
}
