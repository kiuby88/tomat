package org.tomat.tosca.parser.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;
import org.tomat.tosca.parsers.JBossNodeTemplateParser;
import org.tomat.tosca.parsers.NodeTemplateParser;
import org.tomat.tosca.parsers.NodeTemplateParserProvider;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Jose on 06/10/14.
 */
public class AppDatbaseParsingTest {



    List<TNodeTemplate> nodeTemplateListAWSSample;
    //List<TRelationshipTemplate> relationshipTemplateListMalFormedTopology;
    TNodeTemplate nodeTemplateAWS;
    DefinitionParser definitionParser;
    String AWSFileMalFormedRelation = "resources/AWS-Location-Sample-MalFormedRelation.xml";
    String AWSFile = "resources/AWS-Location-Sample.xml";
    String AWSUnsupportedType = "resources/AWS-Location-Sample-Unsupported-Type.xml";
    String jBossPayWebServerId = "JBossPayWebServer".toLowerCase();

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ProviderTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException {
        nodeTemplateListAWSSample = DefinitionUtils.getNodeTemplates(new File(AWSFile));
        definitionParser = new DefinitionParser();
    }

    @Test
    public void nodeTemplateAWSSampleNumber() {
        assertEquals(nodeTemplateListAWSSample.size(), 1);
    }

    @Test
    public void nodeTemplateProviderJBossServer() throws NodeTemplateTypeNotSupportedException {
        NodeTemplateParser nodeTemplateParser = NodeTemplateParserProvider
                .createNodeTemplateParser(nodeTemplateAWS = nodeTemplateListAWSSample.get(0));
        assertEquals((nodeTemplateParser instanceof JBossNodeTemplateParser), true);
    }

    @Test
    public void nodeTemplateProviderJBossServerProperties() throws NodeTemplateTypeNotSupportedException {
        JBossNodeTemplateParser jBossNodeTemplateParser = (JBossNodeTemplateParser) NodeTemplateParserProvider
                .createNodeTemplateParser(nodeTemplateAWS = nodeTemplateListAWSSample.get(0));
        assertEquals(jBossNodeTemplateParser.getHttpPort(), "80");
        assertNull(jBossNodeTemplateParser.getHttpsPort());
    }

    @Test(expected = NodeTemplateTypeNotSupportedException.class)
    public void unsupportedNodeTemplateType()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException {
        definitionParser.parsingApplicationTopology(AWSUnsupportedType);
    }

}
