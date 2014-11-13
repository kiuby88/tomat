package org.tomat.tosca.parsers;

import org.opentosca.model.tosca.*;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kiuby88 on 28/10/14.
 */
public class ToscaParser {

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

        definitions = DefinitionUtils.getDefinitions(definitionFilePath);
        nodeTemplatesOfTopology = DefinitionUtils.getNodeTemplates(definitions);
        relationshipTemplatesOfTopology = DefinitionUtils
                .getRelationshipTemplates(definitions);
        serviceTemplateOfTopology = DefinitionUtils.getServiceTemplate(definitions);
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