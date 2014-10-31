package org.tomat.tosca.parsers;

import org.opentosca.model.tosca.*;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.AgnosticComponentProvider;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kiuby88 on 23/09/2014.
 */
//TODO check the clean code to specify <variableName>List (e.g.).
//TODO El nombre de esta clase debería ser algo refereico con parsear la topología
//TODO Esta clase contendrá un metodo para parsear el fichero que contiene la topología
//TODO se podria llamar algo como TopologyProcessor o TopologyManager o TopologyAnalizer
public class ToscaProcessor {



    private List<AgnosticComponent> generatedAgnosticComponents = null;
    private Map<AgnosticComponent, List<AgnosticComponent>> agnosticRelations = null;
    private ApplicationAgnosticMetadata applicationAgnosticMetadata;
    private ToscaParser toscaParser;

    public ToscaProcessor() {
        generatedAgnosticComponents = new LinkedList<>();
        agnosticRelations = new HashMap<>();
        applicationAgnosticMetadata = new ApplicationAgnosticMetadata();
        toscaParser=new ToscaParser();
    }

    public ToscaProcessor parsingApplicationTopology(String definitionFilePath)
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        return parsingApplicationTopology(new File(definitionFilePath));
    }

    private ToscaProcessor parsingApplicationTopology(File definitionFilePath)
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        toscaParser.parsingApplicationTopology(definitionFilePath);
        return this;
    }

    public ToscaProcessor buildAgnostics()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException,
            AgnosticPropertyException {

        buildAgnosticComponentList();
        buildAgnosticComponentRelations();
        buildApplicationAgnosticMetadata();
        return this;
    }

    private void buildAgnosticComponentList()
            throws NodeTemplateTypeNotSupportedException, AgnosticPropertyException,
            TopologyTemplateFormatException {
        generatedAgnosticComponents = new LinkedList<>();
        for (TNodeTemplate nodeTemplate : toscaParser.getNodeTemplatesOfTopology()) {
            generatedAgnosticComponents
                    .add(buildAgnosticComponent(nodeTemplate));
        }
    }

    private AgnosticComponent buildAgnosticComponent(TNodeTemplate nodeTemplate)
            throws AgnosticPropertyException,
            NodeTemplateTypeNotSupportedException,
            TopologyTemplateFormatException {
        AgnosticComponent agnosticComponent =
                AgnosticComponentProvider.createAgnosticComponent(nodeTemplate);
        agnosticComponent
                .setAgnosticDeploymentArtifacts(getAgnosticDeploymentArtifacts(nodeTemplate));
        return agnosticComponent;
    }

    private List<AgnosticDeploymentArtifact> getAgnosticDeploymentArtifacts(
            TNodeTemplate nodeTemplate)
            throws TopologyTemplateFormatException {

        List<AgnosticDeploymentArtifact> result;
        List<TDeploymentArtifact> deploymentArtifacts =toscaParser.getDeploymentArtifact(nodeTemplate);
        result = getAgnosticDeploymentArtifacts(deploymentArtifacts);
        return result;
    }

    private List<AgnosticDeploymentArtifact> getAgnosticDeploymentArtifacts(
            List<TDeploymentArtifact> deploymentArtifacts)
            throws TopologyTemplateFormatException {

        List<AgnosticDeploymentArtifact> result = null;
        if (deploymentArtifacts != null) {
            result = new LinkedList<>();
            AgnosticDeploymentArtifact agnosticDeploymentArtifact;
            for (TDeploymentArtifact deploymentArtifact : deploymentArtifacts) {
                agnosticDeploymentArtifact = getAgnosticDeploymentArtifact(deploymentArtifact);
                result.add(agnosticDeploymentArtifact);
            }
        }
        return result;
    }

    private AgnosticDeploymentArtifact getAgnosticDeploymentArtifact(
            TDeploymentArtifact deploymentArtifact)
            throws TopologyTemplateFormatException {

        TArtifactTemplate artifactTemplate =toscaParser.getArtifactTemplate(deploymentArtifact);
        if (artifactTemplate == null) {
            throwExceptionForNotFoundArtifactTemplate(deploymentArtifact);
        }
        return new AgnosticDeploymentArtifact(artifactTemplate);
    }

    private AgnosticDeploymentArtifact throwExceptionForNotFoundArtifactTemplate(
            TDeploymentArtifact deploymentArtifact)
            throws TopologyTemplateFormatException {

        throw new TopologyTemplateFormatException(
                "ArtifactTemplate " + deploymentArtifact.getArtifactRef().getLocalPart()
                        + " declaration was not found for DeploymentArtifact.");
    }

    private void buildAgnosticComponentRelations()
            throws TopologyTemplateFormatException {

        MatchingDictionary capabilityIdsNodeTemplateIsDictionary =
                createCapabilityIdsNodeTemplateIdsDictionary(generatedAgnosticComponents);
        MatchingDictionary requirementIdsNodeTemplateIsDictionary =
                createRequirementIdsNodeTemplateIdsDictionary(generatedAgnosticComponents);
        agnosticRelations =new HashMap<>();
        for (TRelationshipTemplate relationshipTemplate : toscaParser.getRelationshipTemplatesOfTopology()) {
            addRelationTemplateToAgnosticRelation(
                    relationshipTemplate,
                    capabilityIdsNodeTemplateIsDictionary,
                    requirementIdsNodeTemplateIsDictionary);
        }
    }

    private MatchingDictionary createCapabilityIdsNodeTemplateIdsDictionary(
            List<AgnosticComponent> agnosticComponents) {
        MatchingDictionary dictionary = new MatchingDictionary();
        for (AgnosticComponent agnosticComponent : agnosticComponents)
            dictionary
                    .addDictionaryEntrys(agnosticComponent.getCapabilitiesIds(), agnosticComponent);
        return dictionary;
    }

    private MatchingDictionary createRequirementIdsNodeTemplateIdsDictionary(
            List<AgnosticComponent> agnosticComponents) {
        MatchingDictionary dictionary = new MatchingDictionary();
        for (AgnosticComponent agnosticComponent : agnosticComponents)
            dictionary
                    .addDictionaryEntrys(agnosticComponent.getRequirementsIds(), agnosticComponent);
        return dictionary;
    }

    private void addRelationTemplateToAgnosticRelation(
            TRelationshipTemplate relationshipTemplate,
            MatchingDictionary capabilitiesIdsNodeTemplateIdsDictionary,
            MatchingDictionary requirementsIdsNodeTemplateIdsDictionary)
            throws TopologyTemplateFormatException {

        TRequirement source = (TRequirement) relationshipTemplate.getSourceElement().getRef();
        TCapability target = (TCapability) relationshipTemplate.getTargetElement().getRef();

        if ((source == null) || (target == null)) {
            throw new TopologyTemplateFormatException("Inconsistent relation"
                    + relationshipTemplate.getId()
                    + " source or target do not defined correctly");
        } else {
            AgnosticComponent nodeTemplateSourceId = requirementsIdsNodeTemplateIdsDictionary
                    .get(source.getId());
            AgnosticComponent nodeTemplateTargetId = capabilitiesIdsNodeTemplateIdsDictionary
                    .get(target.getId());
            addRelationAgnosticRelation(nodeTemplateSourceId,
                    nodeTemplateTargetId);
        }
    }

    private void addRelationAgnosticRelation(AgnosticComponent source,
                                             AgnosticComponent target) {
        List<AgnosticComponent> targetValues;
        if (agnosticRelations.containsKey(source)) {
            targetValues = agnosticRelations.get(source);
        } else {
            targetValues = new LinkedList<>();
        }
        targetValues.add(target);
        agnosticRelations.put(source, targetValues);
    }

    private void buildApplicationAgnosticMetadata() {


        if (toscaParser.getServiceTemplateOfTopology() != null) {
            this.setApplicationAgnosticMetadata(
                    new ApplicationAgnosticMetadata(toscaParser.getServiceTemplateOfTopology()));
        } else {
            this.setApplicationAgnosticMetadata(new ApplicationAgnosticMetadata());
        }
    }

    //TODO using this nomenclature in every file or delete
    //<editor-fold desc="Getters and Setters">
    public List<AgnosticComponent> getAgnosticComponents() {
        return generatedAgnosticComponents;
    }

    public Map<AgnosticComponent, List<AgnosticComponent>> getAgnosticRelations() {
        return agnosticRelations;
    }

    public ApplicationAgnosticMetadata getApplicationAgnosticMetadata() {
        return applicationAgnosticMetadata;
    }

    public void setApplicationAgnosticMetadata(
            ApplicationAgnosticMetadata applicationAgnosticMetadata) {
        this.applicationAgnosticMetadata = applicationAgnosticMetadata;
    }
    //</editor-fold>

    private class MatchingDictionary {

        Map<String, AgnosticComponent> dictionary;

        public MatchingDictionary() {
            dictionary = new HashMap<>();
        }

        public void addDictionaryEntrys(List<String> keys, AgnosticComponent value) {
            if ((keys != null) && (value != null)) {
                for (String key : keys) {
                    dictionary.put(key, value);
                }
            }
        }

        public boolean containsKey(String key) {
            return dictionary.containsKey(key);
        }

        public AgnosticComponent get(String key) {
            if (containsKey(key)) {
                return dictionary.get(key);
            } else {
                return null;
            }
        }
    }


}


