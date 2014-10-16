package org.tomat.tosca.parser.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.AgnosticElementUtils;
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

/**
 * Created by Jose on 06/10/14.
 */
public class AppDatbaseParsingTest {

    //TODO rename the methods using the methodology of Google JAva Style
    List<TNodeTemplate> nodeTemplateListAWSDbSample;
    DefinitionParser definitionParser;
    String AWSApplicationDatabaseFile = "resources/AWS-Application-DatabaseSample.xml";
    Map<AgnosticElement, List<AgnosticElement>> relationMaps;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppDatbaseParsingTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException {
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
