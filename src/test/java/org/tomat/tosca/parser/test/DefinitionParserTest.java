package org.tomat.tosca.parser.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.elements.AgnosticElementUtils;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.tosca.parsers.DefinitionParser;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Jose on 04/10/14.
 */

public class DefinitionParserTest {

    //TODO rename the methods using the methodology of Google JAva Style
    DefinitionParser definitionParser;
    String AWSFileMalFormedRelation = "resources/AWS-Location-Sample-MalFormedRelation.xml";
    String AWSFile = "resources/AWS-Location-Sample.xml";
    String jBossMainWebServerId ="JBossMainWebServer".toLowerCase();
    String mainWebAppId="MainWebApp".toLowerCase();

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(DefinitionParserTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException {
        definitionParser=new DefinitionParser();
    }

    @Test(expected = TopologyTemplateFormatException.class)
    public void definitionThrowExceptionByMalformedRelation()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        definitionParser
                .parsingApplicationTopology(AWSFileMalFormedRelation)
                .buildAgnostics();
    }

    @Test
    public void agnosticRelationComponentsGeneration_CorrectTopology()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        definitionParser
                .parsingApplicationTopology(AWSFile)
                .buildAgnostics();
        Map<AgnosticElement, List<AgnosticElement>> agnosticRelations = definitionParser
                .getAgnosticRelations();
        assertEquals(agnosticRelations.size(),1);
        Set<AgnosticElement> keySet=agnosticRelations.keySet();

        AgnosticElement agnosticElementMainWebApp=AgnosticElementUtils
                .findAgnosticElementById(keySet,mainWebAppId);
        assertNotNull(agnosticElementMainWebApp);
        assertEquals(agnosticRelations.containsKey(agnosticElementMainWebApp),true);
        assertEquals(agnosticRelations.get(agnosticElementMainWebApp).size(),1);
        assertEquals(agnosticRelations
                .get(agnosticElementMainWebApp).get(0).getId().toLowerCase(), jBossMainWebServerId);
    }

    @Test
    public void testAgnosticMetadataDefinition() throws NodeTemplateTypeNotSupportedException,
            TopologyTemplateFormatException, AgnosticPropertyException {
        definitionParser
                .parsingApplicationTopology(AWSFile)
                .buildAgnostics();
        ApplicationAgnosticMetadata applicationAgnosticMetadata=definitionParser
                .getApplicationAgnosticMetadata();
        assertNotNull(applicationAgnosticMetadata);
        assertEquals(applicationAgnosticMetadata.getId(), "AppOnlineRetailing");
        assertEquals(applicationAgnosticMetadata.getName(),"OnlineRetailing Template");
    }

    @Test
    public void agnosticComponentsGeneration_CorrectTopology()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        definitionParser
                .parsingApplicationTopology(AWSFile)
                .buildAgnostics();
        List<AgnosticElement> agnosticComponents = definitionParser
                .getAgnosticElements();
        assertEquals(agnosticComponents.size(), 2);
    }

    @Test
    public void testBuildElements_EmptyParsing()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException,
            AgnosticPropertyException {
        definitionParser.buildAgnostics();
        assertEquals(definitionParser.getAgnosticElements().size(), 0);
        assertEquals(definitionParser.getAgnosticRelations().size(), 0);
        assertNotNull(definitionParser.getApplicationAgnosticMetadata());
    }
}
