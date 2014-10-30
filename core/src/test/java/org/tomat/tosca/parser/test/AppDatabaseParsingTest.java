package org.tomat.tosca.parser.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.components.*;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaParser;
import org.tomat.tosca.parsers.ToscaProcessor;
import org.tomat.tosca.parsers.ToscaSupportedTypeProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jose on 06/10/14.
 */

@RunWith(value = Parameterized.class)
public class AppDatabaseParsingTest {

    //TODO rename the methods using the methodology of Google JAva Style
    List<TNodeTemplate> nodeTemplateListAWSDbSample;
    ToscaProcessor toscaProcessor;
    String AWSApplicationDatabaseFile;
    Map<AgnosticComponent, List<AgnosticComponent>> relationMaps;
    String webServerType;
    String webServerAgnosticComponentType;
    private String webServerId;

    public AppDatabaseParsingTest(String name, String  file, String webServerType,
                                  String webServerAgnosticComponentType,
                                  String webserverId)
            throws AgnosticPropertyException, TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException {
        AWSApplicationDatabaseFile=file;
        this.webServerType=webServerType;
        this.webServerAgnosticComponentType=webServerAgnosticComponentType;
        this.webServerId=webserverId;
        setUp();
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppDatabaseParsingTest.class);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<Object[]> data1() {
        return Arrays.asList(new Object[][]{
                {"JBossDatabaseApp", "core/src/test/resources/toscaTopology/AWS-Application-DatabaseSample-JBoss.xml",
                        ToscaSupportedTypeProvider.JBOSS_WEB_SERVER, JBossAgnosticComponent.TYPE,
                        "JBossMainWebServer"},
                {"JettyDatabaseApp", "core/src/test/resources/toscaTopology/AWS-Application-DatabaseSample-Jetty.xml",
                        ToscaSupportedTypeProvider.JETTY_WEB_SERVER, JettyAgnosticComponent.TYPE,
                        "JettyMainWebServer"},
                {"TomcatDatabaseApp", "core/src/test/resources/toscaTopology/AWS-Application-DatabaseSample-Tomcat.xml",
                        ToscaSupportedTypeProvider.TOMCAT_WEB_SERVER, TomcatAgnosticComponent.TYPE,
                        "TomcatMainWebServer"}
        });
    }

    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        ToscaParser toscaParser=new ToscaParser();
        nodeTemplateListAWSDbSample = toscaParser
                .parsingApplicationTopology(AWSApplicationDatabaseFile)
                .getNodeTemplatesOfTopology();
        toscaProcessor = new ToscaProcessor();
        toscaProcessor
                .parsingApplicationTopology(AWSApplicationDatabaseFile)
                .buildAgnostics();
        relationMaps =
                toscaProcessor.getAgnosticRelations();
    }

    @Test
    public void nodeTemplateAWSSampleNumber() {
        int numberOfNodeTemplates = 4;
        Assert.assertEquals(nodeTemplateListAWSDbSample.size(), numberOfNodeTemplates);
    }

    @Test
    public void checkTypesTest() {
        Assert.assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(0)).toLowerCase(),
                webServerType.toLowerCase());
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(1)).toLowerCase(),
                ToscaSupportedTypeProvider.WEB_APPLICATION.toLowerCase());
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(2)).toLowerCase(),
                ToscaSupportedTypeProvider.MySQL_DBMS.toLowerCase());
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(3)).toLowerCase(),
                ToscaSupportedTypeProvider.MySQL_DB.toLowerCase());
    }

    @Test
    public void testMySQLDbProperties(){

        List<AgnosticComponent> agnosticComponents= toscaProcessor.getAgnosticComponents();
        AgnosticComponent mySQLDb= agnosticComponents.get(3);
        assertEquals(mySQLDb.getProperties().size(),4);

        assertEquals(mySQLDb.getProperties().get(0).getValue(),"seaclouds");
        assertEquals(mySQLDb.getProperties().get(1).getValue(),"dbName");
        assertEquals(mySQLDb.getProperties().get(2).getValue(),"dbUser");
        assertEquals(mySQLDb.getProperties().get(3).getValue(),"3306");
    }

    @Test
    public void testDeploymentArtifacts_All(){

        List<AgnosticComponent> agnosticComponents= toscaProcessor.getAgnosticComponents();
        Assert.assertEquals(agnosticComponents.size(), 4);

        testDeploymentArtifacts_WebServer(agnosticComponents);
        testDeploymentArtifacts_WebApp(agnosticComponents);
        testDeploymentArtifacts_MySQL(agnosticComponents);
        testDeploymentArtifacts_MySQLDb(agnosticComponents);
    }

    public void testDeploymentArtifacts_WebServer(List<AgnosticComponent> agnosticComponents){
        assertEquals(agnosticComponents.get(0).getType(), this.webServerAgnosticComponentType);
        Assert.assertNull(agnosticComponents.get(0).getAgnosticDeploymentArtifacts());
    }

    public void testDeploymentArtifacts_WebApp(List<AgnosticComponent> agnosticComponents){
        assertEquals(agnosticComponents.get(1).getType(), WebAppAgnosticComponent.TYPE);
        List<AgnosticDeploymentArtifact> agnosticDeploymentArtifactWebApp=agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        Assert.assertNotNull(agnosticDeploymentArtifactWebApp);
        Assert.assertEquals(agnosticDeploymentArtifactWebApp.size(), 1);
        assertEquals(agnosticDeploymentArtifactWebApp.get(0).getArtifactReferences().size(), 1);
        AgnosticDeploymentArtifact webAppAgnosticDeploymentArtifact = agnosticDeploymentArtifactWebApp.get(0);
        assertEquals(webAppAgnosticDeploymentArtifact.getArtifactReferences().size(), 1);
        assertEquals(webAppAgnosticDeploymentArtifact.getArtifactReferences().get(0), "webAppArtifactImplementation.war");
    }

    public void testDeploymentArtifacts_MySQL(List<AgnosticComponent> agnosticComponents){
        assertEquals(agnosticComponents.get(2).getType(), MySQLAgnosticComponent.TYPE);
        Assert.assertNull(agnosticComponents.get(2).getAgnosticDeploymentArtifacts());
    }

    public void testDeploymentArtifacts_MySQLDb(List<AgnosticComponent> agnosticComponents){
        assertEquals(agnosticComponents.get(3).getType(), MySQLDataBaseAgnosticComponent.TYPE);
        List<AgnosticDeploymentArtifact> agnosticDeploymentArtifactMySQLDb=
                agnosticComponents.get(3).getAgnosticDeploymentArtifacts();
        Assert.assertNotNull(agnosticDeploymentArtifactMySQLDb);
        Assert.assertEquals(agnosticDeploymentArtifactMySQLDb.size(), 1);
        assertEquals(agnosticDeploymentArtifactMySQLDb.get(0).getArtifactReferences().size(), 1);
        AgnosticDeploymentArtifact MySQLDbAgnosticDeploymentArtifact = agnosticDeploymentArtifactMySQLDb.get(0);
        assertEquals(MySQLDbAgnosticDeploymentArtifact.getArtifactReferences().size(), 1);
        assertEquals(MySQLDbAgnosticDeploymentArtifact.getArtifactReferences().get(0), "database.sql");
    }

    @Test
    public void checkRelations()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException {
        int numberOfRelationShipKeys = 2;
        Assert.assertEquals(relationMaps.size(), numberOfRelationShipKeys);

        checkRelation("MainWebApp", webServerId);
        checkRelation("MainWebApp", "MainDB");
        checkRelation("MainDb", "MainMySql");
    }

    public void checkRelation(String sourceId, String targetId){
        AgnosticComponent sourceAgnosticComponent, targetAgnosticComponent;
        List<AgnosticComponent> targets;
        Set<AgnosticComponent> relationsKeyMap = relationMaps.keySet();

        sourceAgnosticComponent= AgnosticComponentUtils
                .findAgnosticComponentById(relationsKeyMap, sourceId);
        Assert.assertNotNull(sourceAgnosticComponent);
        Assert.assertEquals(relationMaps.containsKey(sourceAgnosticComponent), true);

        targets=relationMaps.get(sourceAgnosticComponent);
        targetAgnosticComponent= AgnosticComponentUtils.findAgnosticComponentById(targets, targetId);
        Assert.assertNotNull(targetAgnosticComponent);
    }
}
