package org.tomat.tosca.parser.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.AgnosticComponentUtils;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaProcessor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Jose on 04/10/14.
 */

public class ToscaProcessorTest {

    //TODO rename the methods using the methodology of Google JAva Style
    ToscaProcessor toscaProcessor;
    String AWSFileMalFormedRelation = "core/src/test/resources/toscaTopology/AWS-Location-Sample-MalFormedRelation.xml";
    String AWSFile = "core/src/test/resources/toscaTopology/AWS-Location-Sample.xml";
    String AWSFileArtifactTemplate = "core/src/test/resources/toscaTopology/AWS-ArtifactTemplateDefinition.xml";
    String AWSDeploymentWithoutTemplate = "core/src/test/resources/toscaTopology/AWS-DeploymentArtifactWithoutArtifactTemplate.xml";
    String AWSSeveralDeploymentArtifacts = "core/src/test/resources/toscaTopology/AWS-SeveralCorrectDeploymentArtifactsDefinition.xml";
    String AWSNotDeploymentArtifactListDefined = "core/src/test/resources/toscaTopology/AWS-NotDeploymentArtifactListDefined.xml";
    String AWSNotDeploymentArtifactDefined = "core/src/test/resources/toscaTopology/AWS-NotDeploymentArtifactsDefined.xml";
    String AWSNotNodeTypeImplementationDefined = "core/src/test/resources/toscaTopology/AWS-NotNodeTypeImplementationDefined.xml";
    String jBossMainWebServerId ="JBossMainWebServer".toLowerCase();
    String mainWebAppId="MainWebApp".toLowerCase();

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(ToscaProcessorTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException {
        toscaProcessor =new ToscaProcessor();
    }

    @Test(expected = TopologyTemplateFormatException.class)
    public void definitionThrowExceptionByMalformedRelation()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        toscaProcessor
                .parsingApplicationTopology(AWSFileMalFormedRelation)
                .buildAgnostics();
    }

    @Test
    public void agnosticRelationComponentsGeneration_CorrectTopology()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        toscaProcessor
                .parsingApplicationTopology(AWSFile)
                .buildAgnostics();
        Map<AgnosticComponent, List<AgnosticComponent>> agnosticRelations = toscaProcessor
                .getAgnosticRelations();
        Assert.assertEquals(agnosticRelations.size(), 1);
        Set<AgnosticComponent> keySet=agnosticRelations.keySet();

        AgnosticComponent agnosticComponentMainWebApp= AgnosticComponentUtils
                .findAgnosticComponentById(keySet, mainWebAppId);
        Assert.assertNotNull(agnosticComponentMainWebApp);
        Assert.assertEquals(agnosticRelations.containsKey(agnosticComponentMainWebApp), true);
        Assert.assertEquals(agnosticRelations.get(agnosticComponentMainWebApp).size(), 1);
        assertEquals(agnosticRelations
                .get(agnosticComponentMainWebApp).get(0).getId().toLowerCase(), jBossMainWebServerId);
    }

    @Test
    public void testAgnosticMetadataDefinition() throws NodeTemplateTypeNotSupportedException,
            TopologyTemplateFormatException, AgnosticPropertyException {
        toscaProcessor
                .parsingApplicationTopology(AWSFile)
                .buildAgnostics();
        ApplicationAgnosticMetadata applicationAgnosticMetadata= toscaProcessor
                .getApplicationAgnosticMetadata();
        Assert.assertNotNull(applicationAgnosticMetadata);
        assertEquals(applicationAgnosticMetadata.getId(), "AppOnlineRetailing");
        assertEquals(applicationAgnosticMetadata.getName(),"OnlineRetailing Template");
    }

    @Test
    public void agnosticComponentsGeneration_CorrectTopology()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        toscaProcessor
                .parsingApplicationTopology(AWSFile)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = toscaProcessor
                .getAgnosticComponents();
        Assert.assertEquals(agnosticComponents.size(), 2);
    }

    @Test
    public void testBuildComponents_EmptyParsing()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException,
            AgnosticPropertyException {
        toscaProcessor.buildAgnostics();
        assertEquals(toscaProcessor.getAgnosticComponents().size(), 0);
        assertEquals(toscaProcessor.getAgnosticRelations().size(), 0);
        Assert.assertNotNull(toscaProcessor.getApplicationAgnosticMetadata());
    }

    @Test
    public void testAgnosticComponentGeneration_ArtifactsTemplate()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        toscaProcessor
                .parsingApplicationTopology(AWSFileArtifactTemplate)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = toscaProcessor
                .getAgnosticComponents();
        Assert.assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        Assert.assertEquals(webAppAgnosticDeploymentArtifactList.size(), 1);

        AgnosticDeploymentArtifact webAppAgnosticDeploymentArtifact=
                webAppAgnosticDeploymentArtifactList.get(0);
        assertEquals(webAppAgnosticDeploymentArtifact.getType(),"ArchiveArtifact");
        assertEquals(webAppAgnosticDeploymentArtifact.getArtifactReferences().size(), 1);
    }

    @Test
    public void testAgnosticComponent_SeveralArtifactTemplates() throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {
        toscaProcessor
                .parsingApplicationTopology(AWSSeveralDeploymentArtifacts)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = toscaProcessor
                .getAgnosticComponents();
        Assert.assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        Assert.assertEquals(webAppAgnosticDeploymentArtifactList.size(), 2);

        AgnosticDeploymentArtifact webAppAgnosticDeploymentArtifact1=
                webAppAgnosticDeploymentArtifactList.get(0);
        assertEquals(webAppAgnosticDeploymentArtifact1.getType(),"ArchiveArtifact");
        assertEquals(webAppAgnosticDeploymentArtifact1.getArtifactReferences().size(), 1);
        assertEquals(webAppAgnosticDeploymentArtifact1.getArtifactReferences().get(0), "webAppArtifactImplementation1.war");

        AgnosticDeploymentArtifact webAppAgnosticDeploymentArtifact2=
                webAppAgnosticDeploymentArtifactList.get(1);
        assertEquals(webAppAgnosticDeploymentArtifact2.getType(),"ArchiveArtifact");
        assertEquals(webAppAgnosticDeploymentArtifact2.getArtifactReferences().size(), 2);
        assertEquals(webAppAgnosticDeploymentArtifact2.getArtifactReferences().get(0), "webAppArtifactImplementation2-1.war");
        assertEquals(webAppAgnosticDeploymentArtifact2.getArtifactReferences().get(1), "webAppArtifactImplementation2-2.war");

    }

    @Test(expected = TopologyTemplateFormatException.class)
    public void testAgnosticComponent_NotArtifactTemplate()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException,
            AgnosticPropertyException {
        toscaProcessor
                .parsingApplicationTopology(AWSDeploymentWithoutTemplate)
                .buildAgnostics();
    }

    @Test
    public void testAgnosticComponent_NotDeploymentArtifactListDeclaration()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {

        toscaProcessor
                .parsingApplicationTopology(AWSNotDeploymentArtifactListDefined)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = toscaProcessor
                .getAgnosticComponents();
        Assert.assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        Assert.assertNull(webAppAgnosticDeploymentArtifactList);
    }

    @Test
    public void testAgnosticComponent_NotDeploymentArtifactDeclaration()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {

        toscaProcessor
                .parsingApplicationTopology(AWSNotDeploymentArtifactDefined)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = toscaProcessor
                .getAgnosticComponents();
        Assert.assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        Assert.assertNull(webAppAgnosticDeploymentArtifactList);
    }

    @Test
    public void testAgnosticComponent_NotNodeTypeImplementationDeclaration()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {

        toscaProcessor
                .parsingApplicationTopology(AWSNotNodeTypeImplementationDefined)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = toscaProcessor
                .getAgnosticComponents();
        Assert.assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        Assert.assertNull(webAppAgnosticDeploymentArtifactList);
    }
}
