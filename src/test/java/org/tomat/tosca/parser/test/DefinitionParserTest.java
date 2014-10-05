package org.tomat.tosca.parser.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.TRelationshipTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.AgnosticApplicationComponent;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;
import org.tomat.tosca.parsers.NodeTemplateParser;
import org.tomat.tosca.parsers.NodeTemplateParserProvider;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Jose on 04/10/14.
 */

public class DefinitionParserTest {

    List<TNodeTemplate> nodeTemplateListMalformedTopology;
    List<TRelationshipTemplate> relationshipTemplateListMalFormedTopology;
    TNodeTemplate nodeTemplateAWSMAl;
    DefinitionParser definitionParser;
    String AWSFileMalFormedRelation = "resources/AWS-Location-Sample-MalFormedRelation.xml";
    String AWSFile = "resources/AWS-Location-Sample.xml";
    String jBossPayWebServerId ="JBossPayWebServer".toLowerCase();

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DefinitionParserTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException {
        nodeTemplateListMalformedTopology = DefinitionUtils.getNodeTemplates(new File(AWSFileMalFormedRelation));
        relationshipTemplateListMalFormedTopology = DefinitionUtils.getRelationshipTemplates(new File(AWSFileMalFormedRelation));
        nodeTemplateAWSMAl = nodeTemplateListMalformedTopology.get(0);
        definitionParser=new DefinitionParser();
    }

    @Test
    public void jBossFabricTest() {
        assertEquals(nodeTemplateListMalformedTopology.get(0).getType().getLocalPart().toLowerCase(), "jbosswebserver");
        assertEquals(nodeTemplateListMalformedTopology.get(0).getLocation().getLocationId(), "AWS");
        assertTrue(true);
    }

    @Test
    public void nodeTemplateParserTest() {
        NodeTemplateParser nodeTemplateParser = NodeTemplateParserProvider.createNodeTemplateParser(nodeTemplateAWSMAl);
        assertEquals(nodeTemplateParser.getCapabilitiesIds().size(), 2);
    }

    @Test
    public void relationshipTest() {
        TNodeTemplate source = (TNodeTemplate) relationshipTemplateListMalFormedTopology.get(0).getSourceElement().getRef();
        TNodeTemplate target = (TNodeTemplate) relationshipTemplateListMalFormedTopology.get(0).getTargetElement().getRef();
        assertEquals(source.getId().toLowerCase(), jBossPayWebServerId);
        assertNull(target);
    }

    @Test(expected = TopologyTemplateFormatException.class)
    public void definitionThrowExceptionByMalformedRelation() throws TopologyTemplateFormatException {
            definitionParser.parsingApplicationTopology(AWSFileMalFormedRelation);
    }

    @Test
    public void agnosticRelationComponentsGenerationCorrect() throws TopologyTemplateFormatException {
        definitionParser.parsingApplicationTopology(AWSFile);
        Map<String, List<String>> agnosticRelations = definitionParser.getAgnosticApplicationsComponentRelations();
        assertEquals(agnosticRelations.size(),1);
        assertEquals(agnosticRelations.containsKey(jBossPayWebServerId),true);
        assertEquals(agnosticRelations.get(jBossPayWebServerId).size(),1);
        assertEquals(agnosticRelations.get(jBossPayWebServerId).get(0), jBossPayWebServerId);
    }

    @Test
    public void agnosticComponentsGenerationCorrect() throws TopologyTemplateFormatException {
        definitionParser.parsingApplicationTopology(AWSFile);
        List<AgnosticApplicationComponent> agnosticComponents = definitionParser.getAgnosticApplicationComponents();
        assertEquals(agnosticComponents.size(),0);
    }

    @Test
    public void nodeTemplateProperties(){
        Map<String, String> propertyMap=
                DefinitionUtils.getProperties(nodeTemplateListMalformedTopology.get(0));
        assertEquals(propertyMap.keySet().size(),1);
    }
}
