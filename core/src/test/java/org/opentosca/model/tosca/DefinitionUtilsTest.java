package org.opentosca.model.tosca;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.ResourcePathResolver;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class DefinitionUtilsTest {

    TNodeTemplate nodeTemplateAWSMAl;
    List<TNodeTemplate> nodeTemplatesAWSSample;
    List<TRelationshipTemplate> relationshipTemplateListMalFormedTopology, relationshipTemplatesAWSSample;

    String AWSLocationSimpleTopology;
    String AWSFileMalFormedRelation;
    String AWSNotArtifactTemplate;
    String AWSNotDeploymentArtifact;
    String AWSNotDeploymentArtifactsSpecification;
    String AWSNotNodeTypeImplementation;
    String AWSNotNodeTypeDefinition;

    String jBossPayWebServerId = "JBossPayWebServer".toLowerCase();

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DefinitionUtilsTest.class);
    }

    @Before
    public void setUp() {
        initFiles();
        nodeTemplatesAWSSample = DefinitionUtils.getNodeTemplates(new File(AWSLocationSimpleTopology));
        relationshipTemplateListMalFormedTopology = DefinitionUtils.getRelationshipTemplates(new File(AWSFileMalFormedRelation));
        relationshipTemplatesAWSSample = DefinitionUtils.getRelationshipTemplates(new File(AWSLocationSimpleTopology));
        nodeTemplateAWSMAl = nodeTemplatesAWSSample.get(0);
    }

    private void initFiles() {
        ResourcePathResolver resourcePathResolver=new ResourcePathResolver();
        AWSLocationSimpleTopology =resourcePathResolver
                .getPathFile(ResourcePathResolver.AWS_LOCATION_SIMPLE_TOPOLOGY);
        AWSFileMalFormedRelation=resourcePathResolver
                .getPathFile(ResourcePathResolver.AWS_LOCATION_SAMPLE_MALFORMED_RELATION_NODES);
        AWSNotArtifactTemplate = resourcePathResolver
                .getPathFile(ResourcePathResolver.AWS_NOT_ARTIFACT_TEMPLATE);
        AWSNotDeploymentArtifact = resourcePathResolver
                .getPathFile(ResourcePathResolver.AWS_NOT_DEPLOYMENT_ARTIFACT);
        AWSNotDeploymentArtifactsSpecification = resourcePathResolver
                .getPathFile(ResourcePathResolver.AWS_NOT_DEPLOYMENT_ARTIFACT_SPECIFICATION);
        AWSNotNodeTypeImplementation = resourcePathResolver
                .getPathFile(ResourcePathResolver.AWS_NOT_NODETYPE_IMPLEMENTATION);
        AWSNotNodeTypeDefinition = resourcePathResolver
                .getPathFile(ResourcePathResolver.AWS_NOT_NODETYPE_DEFINITION);
        AWSNotNodeTypeDefinition = resourcePathResolver
                .getPathFile(ResourcePathResolver.AWS_NOT_NODETYPE_DEFINITION);
    }

    @Test
    public void nodeTemplateIdTest() {
        assertEquals(nodeTemplatesAWSSample.size(), 1);
        assertEquals(nodeTemplatesAWSSample.get(0).getType().getLocalPart().toLowerCase(), "jbosswebserver");
    }

    @Test
    public void locationTest() {
        assertEquals(nodeTemplatesAWSSample.get(0).getLocation().getLocationId(), "AWS");
    }

    @Test
    public void relationshipNullTest() {
        TNodeTemplate source = (TNodeTemplate) relationshipTemplateListMalFormedTopology.get(0).getSourceElement().getRef();
        TNodeTemplate target = (TNodeTemplate) relationshipTemplateListMalFormedTopology.get(0).getTargetElement().getRef();
        assertEquals(source.getId().toLowerCase(), jBossPayWebServerId);
        assertNull(target);
    }

    @Test
    public void relationshipNotNullTest() {
        assertEquals(relationshipTemplatesAWSSample.size(), 1);
        TNodeTemplate source = (TNodeTemplate) relationshipTemplatesAWSSample.get(0).getSourceElement().getRef();
        TNodeTemplate target = (TNodeTemplate) relationshipTemplatesAWSSample.get(0).getTargetElement().getRef();
        assertEquals(source.getId().toLowerCase(), jBossPayWebServerId);
        assertEquals(target.getId().toLowerCase(), jBossPayWebServerId);
    }

    @Test
    public void nodeTemplateProperties() {
        Map<String, String> propertyMap =
                DefinitionUtils.getProperties(nodeTemplatesAWSSample.get(0));
        assertEquals(propertyMap.keySet().size(), 1);
        assertEquals(propertyMap.containsKey("httpdport"), true);
        assertEquals(propertyMap.get("httpdport"), "80");
    }

    @Test
    public void testGetArtifactTemplate_NotNodeTypeImplementationDefinition(){
        TDefinitions definitions1= DefinitionUtils.getDefinitions(new File(AWSNotNodeTypeImplementation));
        List<TNodeTemplate> nodeTemplates= DefinitionUtils.getNodeTemplates(new File(AWSNotNodeTypeImplementation));
        assertEquals(nodeTemplates.size(), 1);
        List<TDeploymentArtifact> deploymentArtifacts=
                DefinitionUtils.getDeploymentArtifact(definitions1, nodeTemplates.get(0));
        assertNull(deploymentArtifacts);
    }

    @Test
    public void testGetArtifactTemplate_NotDeploymentArtifactsTemplateDefinition(){

        TDefinitions definitions1= DefinitionUtils.getDefinitions(new File(AWSNotDeploymentArtifactsSpecification));
        List<TNodeTemplate> nodeTemplates= DefinitionUtils.getNodeTemplates(new File(AWSNotDeploymentArtifactsSpecification));
        assertEquals(nodeTemplates.size(), 1);
        List<TDeploymentArtifact> deploymentArtifacts=
                DefinitionUtils.getDeploymentArtifact(definitions1, nodeTemplates.get(0));
        assertNull(deploymentArtifacts);
    }

    @Test
    public void testGetArtifactTemplate_NotDeploymentArtifactTemplateListDefinition(){
        TDefinitions definitions1= DefinitionUtils.getDefinitions(new File(AWSNotDeploymentArtifact));
        List<TNodeTemplate> nodeTemplates= DefinitionUtils.getNodeTemplates(new File(AWSNotDeploymentArtifact));
        assertEquals(nodeTemplates.size(), 1);
        List<TDeploymentArtifact> deploymentArtifacts=
                DefinitionUtils.getDeploymentArtifact(definitions1, nodeTemplates.get(0));
        assertNull(deploymentArtifacts);
    }

    @Test
    public void testGetArtifactTemplate_NotArtifactTemplateDefinition(){
        TDefinitions definitions= DefinitionUtils.getDefinitions(new File(AWSNotArtifactTemplate));
        List<TNodeTemplate> nodeTemplates= DefinitionUtils.getNodeTemplates(new File(AWSNotArtifactTemplate));
        assertEquals(nodeTemplates.size(), 1);
        List<TDeploymentArtifact> deploymentArtifacts=
                DefinitionUtils.getDeploymentArtifact(definitions, nodeTemplates.get(0));
        assertEquals(deploymentArtifacts.size(),1);
        TArtifactTemplate artifactTemplates = DefinitionUtils.getArtifactTemplate(definitions, deploymentArtifacts.get(0));
        assertNull(artifactTemplates);
    }

    @Test
    public void testNodeTypeImplementation(){
        TDefinitions definitions= DefinitionUtils.getDefinitions(new File(AWSLocationSimpleTopology));
        List<TNodeTypeImplementation> nodeTypeImplementations=
                DefinitionUtils.getTNodeTypeImplementations(definitions);
        assertEquals(nodeTypeImplementations.size(),1);
        assertEquals(nodeTypeImplementations.get(0).getNodeType().getLocalPart(), "JBossWebServer");
    }

    @Test
    public void testArtifactTemplate(){
        TDefinitions definitions= DefinitionUtils.getDefinitions(new File(AWSLocationSimpleTopology));
        List<TNodeTemplate> nodeTemplates= DefinitionUtils.getNodeTemplates(new File(AWSLocationSimpleTopology));
        assertEquals(nodeTemplates.size(), 1);
        List<TDeploymentArtifact> deploymentArtifacts=
                DefinitionUtils.getDeploymentArtifact(definitions, nodeTemplates.get(0));
        assertEquals(deploymentArtifacts.size(),1);
        TArtifactTemplate artifactTemplates = DefinitionUtils.getArtifactTemplate(definitions, deploymentArtifacts.get(0));
        assertNotNull(artifactTemplates);
    }


    @Test
    public void testArtifactTemplate_NotNodeTypeDefinition(){
        TDefinitions definitions= DefinitionUtils.getDefinitions(new File(AWSNotNodeTypeDefinition));
        List<TNodeTemplate> nodeTemplates= DefinitionUtils.getNodeTemplates(new File(AWSNotNodeTypeDefinition));
        assertEquals(nodeTemplates.size(), 1);
        List<TDeploymentArtifact> deploymentArtifacts=
                DefinitionUtils.getDeploymentArtifact(definitions, nodeTemplates.get(0));
        assertEquals(deploymentArtifacts.size(),1);
        TArtifactTemplate artifactTemplates = DefinitionUtils.getArtifactTemplate(definitions, deploymentArtifacts.get(0));
        assertNotNull(artifactTemplates);
    }
}
