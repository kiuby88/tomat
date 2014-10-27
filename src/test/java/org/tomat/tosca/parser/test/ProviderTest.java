package org.tomat.tosca.parser.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.AgnosticComponentProvider;
import org.tomat.agnostic.components.JBossAgnosticComponent;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Jose on 06/10/14.
 */
public class ProviderTest {

    //TODO rename the methods using the methodology of Google JAva Style
    List<TNodeTemplate> nodeTemplateListAWSSample;
    TNodeTemplate nodeTemplateAWS;
    DefinitionParser definitionParser;
    String AWSFile = "resources/AWS-Location-Sample.xml";
    String AWSUnsupportedType = "resources/AWS-Location-Sample-Unsupported-Type.xml";

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
        assertEquals(nodeTemplateListAWSSample.size(), 2);
    }

    @Test
    public void nodeTemplateProviderJBossServer() throws NodeTemplateTypeNotSupportedException,
            AgnosticPropertyException {
        AgnosticComponent nodeTemplateParser = AgnosticComponentProvider
                .createAgnosticComponent(nodeTemplateAWS = nodeTemplateListAWSSample.get(0));
        assertEquals((nodeTemplateParser instanceof JBossAgnosticComponent), true);
    }

    @Test
    public void nodeTemplateProviderJBossServerProperties()
            throws NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        JBossAgnosticComponent jBossAgnosticComponent =
                (JBossAgnosticComponent) AgnosticComponentProvider
                .createAgnosticComponent(nodeTemplateAWS = nodeTemplateListAWSSample.get(0));
        List<AgnosticProperty> jBossProperties= jBossAgnosticComponent.getProperties();
        assertEquals(jBossProperties.size(), 1);
        assertEquals(jBossProperties.get(0).getValue(), "80");
        assertNull(jBossAgnosticComponent.getAgnosticDeploymentArtifacts());
    }

    @Test(expected = NodeTemplateTypeNotSupportedException.class)
    public void unsupportedNodeTemplateType()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {
        definitionParser.parsingApplicationTopology(AWSUnsupportedType).buildAgnostics();
    }

}
