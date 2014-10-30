package org.tomat.tosca.parsers;

import org.opentosca.model.tosca.*;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jose on 28/10/14.
 */
public class ToscaParser {
    //TODO it is necessary add a test to check this class
    private List<TNodeTemplate> nodeTemplatesOfTopology = null;
    private List<TRelationshipTemplate> relationshipTemplatesOfTopology = null;
    private TServiceTemplate serviceTemplateOfTopology = null;
    private TDefinitions definitions = null;

    public ToscaParser() {
        nodeTemplatesOfTopology = new LinkedList<>();
        relationshipTemplatesOfTopology = new LinkedList<>();
    }

    public ToscaParser parsingApplicationTopology(String definitionFilePath)
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        return parsingApplicationTopology(new File(definitionFilePath));
    }

    public ToscaParser parsingApplicationTopology(File definitionFilePath)
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        //TODO this could optimiced using TDefinitionTemplate to extract the lists check in a
        //TODO branch
        nodeTemplatesOfTopology = DefinitionUtils.getNodeTemplates(definitionFilePath);
        relationshipTemplatesOfTopology = DefinitionUtils
                .getRelationshipTemplates(definitionFilePath);
        serviceTemplateOfTopology = DefinitionUtils.getServiceTemplate(definitionFilePath);
        definitions = DefinitionUtils.getDefinitions(definitionFilePath);
        return this;
    }

    public List<TNodeTemplate> getNodeTemplatesOfTopology() {
        return nodeTemplatesOfTopology;
    }

    public List<TDeploymentArtifact> getDeploymentArtifact(TNodeTemplate nodeTemplate) {
        return DefinitionUtils.getDeploymentArtifact(definitions, nodeTemplate);
    }

    public TArtifactTemplate getArtifactTemplate(TDeploymentArtifact deploymentArtifact) {
        return DefinitionUtils
                .getArtifactTemplate(definitions, deploymentArtifact);
    }

    public List<TRelationshipTemplate> getRelationshipTemplatesOfTopology(){
        return relationshipTemplatesOfTopology;
    }

    public TServiceTemplate getServiceTemplateOfTopology()
    {
        return serviceTemplateOfTopology;
    }


}