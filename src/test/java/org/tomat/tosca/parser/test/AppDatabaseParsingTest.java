package org.tomat.tosca.parser.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.elements.*;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;
import org.tomat.tosca.parsers.ToscaSupportedTypeProvider;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Jose on 06/10/14.
 */
public class AppDatabaseParsingTest {

    //TODO rename the methods using the methodology of Google JAva Style
    List<TNodeTemplate> nodeTemplateListAWSDbSample;
    DefinitionParser definitionParser;
    String AWSApplicationDatabaseFile = "resources/AWS-Application-DatabaseSample.xml";
    Map<AgnosticElement, List<AgnosticElement>> relationMaps;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppDatabaseParsingTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        nodeTemplateListAWSDbSample = DefinitionUtils
                .getNodeTemplates(new File(AWSApplicationDatabaseFile));
        definitionParser = new DefinitionParser();
        definitionParser
                .parsingApplicationTopology(AWSApplicationDatabaseFile)
                .buildAgnostics();
        relationMaps =
                definitionParser.getAgnosticRelations();
    }

    @Test
    public void nodeTemplateAWSSampleNumber() {
        int numberOfNodeTemplates = 4;
        assertEquals(nodeTemplateListAWSDbSample.size(), numberOfNodeTemplates);
    }

    @Test
    public void checkTypesTest() {
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(0)).toLowerCase(),
                ToscaSupportedTypeProvider.JBOSS_WEB_SERVER.toLowerCase());
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(1)).toLowerCase(),
                ToscaSupportedTypeProvider.WEB_APPLICATION.toLowerCase());
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(2)).toLowerCase(),
                ToscaSupportedTypeProvider.MySQL_DBMS.toLowerCase());
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(3)).toLowerCase(),
                ToscaSupportedTypeProvider.MySQL_DB.toLowerCase());
    }

    @Test
    public void testDeploymentArtifacts_All(){

        List<AgnosticElement> agnosticElements=definitionParser.getAgnosticElements();
        assertEquals(agnosticElements.size(),4);

        testDeploymentArtifacts_JBoss(agnosticElements);
        testDeploymentArtifacts_WebApp(agnosticElements);
        testDeploymentArtifacts_MySQL(agnosticElements);
        testDeploymentArtifacts_MySQLDb(agnosticElements);
    }

    public void testDeploymentArtifacts_JBoss(List<AgnosticElement> agnosticElements){
        assertEquals(agnosticElements.get(0).getType(), JBossAgnosticElement.TYPE);
        assertNull(agnosticElements.get(0).getAgnosticDeploymentArtifacts());
    }

    public void testDeploymentArtifacts_WebApp(List<AgnosticElement> agnosticElements){
        assertEquals(agnosticElements.get(1).getType(), WebAppAgnosticElement.TYPE);
        List<AgnosticDeploymentArtifact> agnosticDeploymentArtifactWebApp=agnosticElements.get(1).getAgnosticDeploymentArtifacts();
        assertNotNull(agnosticDeploymentArtifactWebApp);
        assertEquals(agnosticDeploymentArtifactWebApp.size(), 1);
        assertEquals(agnosticDeploymentArtifactWebApp.get(0).getArtifactReferences().size(), 1);
        AgnosticDeploymentArtifact webAppAgnosticDeploymentArtifact = agnosticDeploymentArtifactWebApp.get(0);
        assertEquals(webAppAgnosticDeploymentArtifact.getArtifactReferences().size(), 1);
        assertEquals(webAppAgnosticDeploymentArtifact.getArtifactReferences().get(0), "webAppArtifactImplementation.war");
    }

    public void testDeploymentArtifacts_MySQL(List<AgnosticElement> agnosticElements){
        assertEquals(agnosticElements.get(2).getType(), MySQLAgnosticElement.TYPE);
        assertNull(agnosticElements.get(2).getAgnosticDeploymentArtifacts());
    }

    public void testDeploymentArtifacts_MySQLDb(List<AgnosticElement> agnosticElements){
        assertEquals(agnosticElements.get(3).getType(), MySQLDataBaseAgnosticElement.TYPE);
        List<AgnosticDeploymentArtifact> agnosticDeploymentArtifactMySQLDb=agnosticElements.get(3).getAgnosticDeploymentArtifacts();
        assertNotNull(agnosticDeploymentArtifactMySQLDb);
        assertEquals(agnosticDeploymentArtifactMySQLDb.size(), 1);
        assertEquals(agnosticDeploymentArtifactMySQLDb.get(0).getArtifactReferences().size(), 1);
        AgnosticDeploymentArtifact MySQLDbAgnosticDeploymentArtifact = agnosticDeploymentArtifactMySQLDb.get(0);
        assertEquals(MySQLDbAgnosticDeploymentArtifact.getArtifactReferences().size(), 1);
        assertEquals(MySQLDbAgnosticDeploymentArtifact.getArtifactReferences().get(0), "database.sql");

    }

    @Test
    public void checkRelations()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException {
        int numberOfRelationShipKeys = 2;
        assertEquals(relationMaps.size(), numberOfRelationShipKeys);

        checkRelation("MainWebApp", "JBossMainWebServer");
        checkRelation("MainWebApp", "MainDB");
        checkRelation("MainDb", "MainMySql");
    }

    public void checkRelation(String sourceId, String targetId){
        AgnosticElement sourceAgnosticElement, targetAgnosticElement;
        List<AgnosticElement> targets;
        Set<AgnosticElement> relationsKeyMap = relationMaps.keySet();

        sourceAgnosticElement= AgnosticElementUtils
                .findAgnosticElementById(relationsKeyMap, sourceId);
        assertNotNull(sourceAgnosticElement);
        assertEquals(relationMaps.containsKey(sourceAgnosticElement), true);

        targets=relationMaps.get(sourceAgnosticElement);
        targetAgnosticElement=AgnosticElementUtils.findAgnosticElementById(targets, targetId);
        assertNotNull(targetAgnosticElement);
    }
}
