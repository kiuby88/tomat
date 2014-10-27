package org.tomat.tosca.parser.test;

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
    String AWSFileArtifactTemplate = "resources/AWS-ArtifactTemplateDefinition.xml";
    String AWSDeploymentWithoutTemplate = "resources/AWS-DeploymentArtifactWithoutArtifactTemplate.xml";
    String AWSSeveralDeploymentArtifacts = "resources/AWS-SeveralCorrectDeploymentArtifactsDefinition.xml";
    String AWSNotDeploymentArtifactListDefined = "resources/AWS-NotDeploymentArtifactListDefined.xml";
    String AWSNotDeploymentArtifactDefined = "resources/AWS-NotDeploymentArtifactsDefined.xml";
    String AWSNotNodeTypeImplementationDefined = "resources/AWS-NotNodeTypeImplementationDefined.xml";
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
        Map<AgnosticComponent, List<AgnosticComponent>> agnosticRelations = definitionParser
                .getAgnosticRelations();
        assertEquals(agnosticRelations.size(),1);
        Set<AgnosticComponent> keySet=agnosticRelations.keySet();

        AgnosticComponent agnosticComponentMainWebApp= AgnosticComponentUtils
                .findAgnosticComponentById(keySet, mainWebAppId);
        assertNotNull(agnosticComponentMainWebApp);
        assertEquals(agnosticRelations.containsKey(agnosticComponentMainWebApp),true);
        assertEquals(agnosticRelations.get(agnosticComponentMainWebApp).size(),1);
        assertEquals(agnosticRelations
                .get(agnosticComponentMainWebApp).get(0).getId().toLowerCase(), jBossMainWebServerId);
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
        List<AgnosticComponent> agnosticComponents = definitionParser
                .getAgnosticComponents();
        assertEquals(agnosticComponents.size(), 2);
    }

    @Test
    public void testBuildComponents_EmptyParsing()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException,
            AgnosticPropertyException {
        definitionParser.buildAgnostics();
        assertEquals(definitionParser.getAgnosticComponents().size(), 0);
        assertEquals(definitionParser.getAgnosticRelations().size(), 0);
        assertNotNull(definitionParser.getApplicationAgnosticMetadata());
    }

    @Test
    public void testAgnosticComponentGeneration_ArtifactsTemplate()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        definitionParser
                .parsingApplicationTopology(AWSFileArtifactTemplate)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = definitionParser
                .getAgnosticComponents();
        assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        assertEquals(webAppAgnosticDeploymentArtifactList.size(),1);

        AgnosticDeploymentArtifact webAppAgnosticDeploymentArtifact=
                webAppAgnosticDeploymentArtifactList.get(0);
        assertEquals(webAppAgnosticDeploymentArtifact.getType(),"ArchiveArtifact");
        assertEquals(webAppAgnosticDeploymentArtifact.getArtifactReferences().size(), 1);
    }

    @Test
    public void testAgnosticComponent_SeveralArtifactTemplates() throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {
        definitionParser
                .parsingApplicationTopology(AWSSeveralDeploymentArtifacts)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = definitionParser
                .getAgnosticComponents();
        assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        assertEquals(webAppAgnosticDeploymentArtifactList.size(),2);

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
        definitionParser
                .parsingApplicationTopology(AWSDeploymentWithoutTemplate)
                .buildAgnostics();
    }

    @Test
    public void testAgnosticComponent_NotDeploymentArtifactListDeclaration()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {

        definitionParser
                .parsingApplicationTopology(AWSNotDeploymentArtifactListDefined)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = definitionParser
                .getAgnosticComponents();
        assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        assertNull(webAppAgnosticDeploymentArtifactList);
    }

    @Test
    public void testAgnosticComponent_NotDeploymentArtifactDeclaration()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {

        definitionParser
                .parsingApplicationTopology(AWSNotDeploymentArtifactDefined)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = definitionParser
                .getAgnosticComponents();
        assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        assertNull(webAppAgnosticDeploymentArtifactList);
    }

    @Test
    public void testAgnosticComponent_NotNodeTypeImplementationDeclaration()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException, AgnosticPropertyException {

        definitionParser
                .parsingApplicationTopology(AWSNotNodeTypeImplementationDefined)
                .buildAgnostics();
        List<AgnosticComponent> agnosticComponents = definitionParser
                .getAgnosticComponents();
        assertEquals(agnosticComponents.size(), 2);

        assertEquals(agnosticComponents.get(1).getType(), "WebApplication");
        List<AgnosticDeploymentArtifact> webAppAgnosticDeploymentArtifactList=
                agnosticComponents.get(1).getAgnosticDeploymentArtifacts();
        assertNull(webAppAgnosticDeploymentArtifactList);
    }
}
