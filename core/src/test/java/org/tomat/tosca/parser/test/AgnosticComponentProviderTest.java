package org.tomat.tosca.parser.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.ResourcePathResolver;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.AgnosticComponentProvider;
import org.tomat.agnostic.components.JBossAgnosticComponent;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaParser;
import org.tomat.tosca.parsers.ToscaProcessor;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kiuby88 on 06/10/14.
 */
public class AgnosticComponentProviderTest {

    //TODO rename the methods using the methodology of Google JAva Style
    List<TNodeTemplate> nodeTemplateListAWSSample;
    TNodeTemplate nodeTemplateAWS;
    String AWSFile;
    String AWSUnsupportedType;
    ToscaParser toscaParser;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AgnosticComponentProviderTest.class);
    }

    @Before
    public void setUp()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {

        toscaParser=new ToscaParser();
        ResourcePathResolver resourcePathResolver=new ResourcePathResolver();

        AWSFile=resourcePathResolver
                .getPathOfFile(ResourcePathResolver.AWS_LOCATION_SAMPLE);
        AWSUnsupportedType=resourcePathResolver
                .getPathOfFile(ResourcePathResolver.AWS_LOCATION_SAMPLE_UNSUPPORTED_TYPE);
        nodeTemplateListAWSSample =
                toscaParser
                .parsingApplicationTopology(AWSFile)
                .getNodeTemplatesOfTopology();
    }

    @Test
    public void nodeTemplateAWSSampleNumber() {
        Assert.assertEquals(nodeTemplateListAWSSample.size(), 2);
    }

    @Test
    public void nodeTemplateProviderJBossServer() throws NodeTemplateTypeNotSupportedException,
            AgnosticPropertyException {
        AgnosticComponent nodeTemplateParser = AgnosticComponentProvider
                .createAgnosticComponent(nodeTemplateAWS = nodeTemplateListAWSSample.get(0));
        Assert.assertEquals((nodeTemplateParser instanceof JBossAgnosticComponent), true);
    }

    @Test
    public void nodeTemplateProviderJBossServerProperties()
            throws NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        JBossAgnosticComponent jBossAgnosticComponent =
                (JBossAgnosticComponent) AgnosticComponentProvider
                .createAgnosticComponent(nodeTemplateAWS = nodeTemplateListAWSSample.get(0));
        List<AgnosticProperty> jBossProperties= jBossAgnosticComponent.getProperties();
        Assert.assertEquals(jBossProperties.size(), 1);
        assertEquals(jBossProperties.get(0).getValue(), "80");
        Assert.assertNull(jBossAgnosticComponent.getAgnosticDeploymentArtifacts());
    }

    @Test(expected = NodeTemplateTypeNotSupportedException.class)
    public void unsupportedNodeTemplateType()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {
        ToscaProcessor toscaProcessor=new ToscaProcessor();
        toscaProcessor.parsingApplicationTopology(AWSUnsupportedType).buildAgnostics();
    }

}
