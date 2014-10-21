package org.tomat.tosca.parsers;

import org.opentosca.model.tosca.*;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.Agnostic;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.AgnosticElementProvider;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by JoseC on 23/09/2014.
 */
//TODO check the clean code to specifi <variableName>List (e.g.).
//TODO El nombre de esta clase debería ser algo refereico con parsear la topología
//TODO Esta clase contendrá un metodo para parsear el fichero que contiene la topología
//TODO se podria llamar algo como TopologyProcessor o TopologyManager o TopologyAnalizer
public class DefinitionParser {


    private List<TNodeTemplate> nodeTemplatesOfTopology = null;
    private List<TRelationshipTemplate> relationshipTemplatesOfTopology = null;
    private TServiceTemplate serviceTemplateOfTopology = null;
    private TDefinitions definitions = null;
    private List<AgnosticElement> generatedAgnosticElements = null;
    private Map<AgnosticElement, List<AgnosticElement>> agnosticRelations = null;
    private ApplicationAgnosticMetadata applicationAgnosticMetadata;

    public DefinitionParser() {
        nodeTemplatesOfTopology = new LinkedList<>();
        relationshipTemplatesOfTopology = new LinkedList<>();

        generatedAgnosticElements = new LinkedList<>();
        agnosticRelations = new HashMap<>();
        applicationAgnosticMetadata = new ApplicationAgnosticMetadata();

    }

    public DefinitionParser parsingApplicationTopology(String definitionFilePath)
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        return parsingApplicationTopology(new File(definitionFilePath));
    }

    private DefinitionParser parsingApplicationTopology(File definitionFilePath)
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

    public DefinitionParser buildAgnostics()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException,
            AgnosticPropertyException {

        buildAgnosticElementsList();
        buildAgnosticElementsRelations();
        buildApplicationAgnosticMetadata();
        return this;
    }

    private void buildAgnosticElementsList()
            throws NodeTemplateTypeNotSupportedException, AgnosticPropertyException, TopologyTemplateFormatException {
        generatedAgnosticElements = new LinkedList<>();
        for (TNodeTemplate nodeTemplate : nodeTemplatesOfTopology) {
            AgnosticElementProvider.createAgnosticElement(nodeTemplate);
            generatedAgnosticElements
                    .add(buildAgnosticElement(nodeTemplate));
        }
    }

    private AgnosticElement buildAgnosticElement(TNodeTemplate nodeTemplate)
            throws AgnosticPropertyException, NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException {
        AgnosticElement agnosticElement =
                AgnosticElementProvider.createAgnosticElement(nodeTemplate);

        //TODO adding deploymentArtifacts
        //agnosticElement.setAgnosticDeploymentArtifacts(getAgnosticDeploymentArtifacts(nodeTemplate));

        return agnosticElement;
    }

    private List<AgnosticDeploymentArtifact> getAgnosticDeploymentArtifacts(TNodeTemplate nodeTemplate)
            throws TopologyTemplateFormatException {

        List<AgnosticDeploymentArtifact> result;
        List<TDeploymentArtifact> deploymentArtifacts =
                DefinitionUtils.getDeploymentArtifact(definitions, nodeTemplate);

            result = getAgnosticDeploymentArtifacts(deploymentArtifacts);

        return result;
    }

    private List<AgnosticDeploymentArtifact> getAgnosticDeploymentArtifacts(List<TDeploymentArtifact> deploymentArtifacts)
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

    private AgnosticDeploymentArtifact getAgnosticDeploymentArtifact(TDeploymentArtifact deploymentArtifact)
            throws TopologyTemplateFormatException {

        TArtifactTemplate artifactTemplate = DefinitionUtils.getArtifactTemplate(definitions, deploymentArtifact);
        if(artifactTemplate==null){
            getAgnosticDeploymentArtifactWithoutArtifactTemplate(deploymentArtifact);
        }
        return new AgnosticDeploymentArtifact(artifactTemplate);
    }

    //TODO it could be good idea change the name using throwExceptionForNotArtifactTemplatefound or similar
    private AgnosticDeploymentArtifact getAgnosticDeploymentArtifactWithoutArtifactTemplate(TDeploymentArtifact deploymentArtifact) throws TopologyTemplateFormatException {
        //TODO make a test to this case. Where in a topology is not defined the
        //TODO the artifactTemplate from a DeploymentArtifact
        throw new TopologyTemplateFormatException(
                "ArtifactTemplate " + deploymentArtifact.getArtifactRef().getLocalPart()
                        + " declaration was not found for DeploymentArtifact.");
    }

    private void buildAgnosticElementsRelations()
            throws TopologyTemplateFormatException {

        MatchingDictionary capabilityIdsNodeTemplateIsDictionary =
                createCapabilityIdsNodeTemplateIdsDictionary(generatedAgnosticElements);
        MatchingDictionary requirementIdsNodeTemplateIsDictionary =
                createRequirementIdsNodeTemplateIdsDictionary(generatedAgnosticElements);

        agnosticRelations =
                new HashMap<AgnosticElement, List<AgnosticElement>>();
        for (TRelationshipTemplate relationshipTemplate : relationshipTemplatesOfTopology) {
            addRelationTemplateToAgnosticRelation(
                    relationshipTemplate,
                    capabilityIdsNodeTemplateIsDictionary,
                    requirementIdsNodeTemplateIsDictionary);
        }
    }

    private MatchingDictionary createCapabilityIdsNodeTemplateIdsDictionary(List<AgnosticElement> agnosticElements) {
        MatchingDictionary dictionary = new MatchingDictionary();
        for (AgnosticElement agnosticElement : agnosticElements)
            dictionary.addDictionaryEntrys(agnosticElement.getCapabilitiesIds(), agnosticElement);
        return dictionary;
    }

    private MatchingDictionary createRequirementIdsNodeTemplateIdsDictionary(List<AgnosticElement> agnosticElements) {
        MatchingDictionary dictionary = new MatchingDictionary();
        for (AgnosticElement agnosticElement : agnosticElements)
            dictionary.addDictionaryEntrys(agnosticElement.getRequirementsIds(), agnosticElement);
        return dictionary;
    }

    private void addRelationTemplateToAgnosticRelation
            (TRelationshipTemplate relationshipTemplate,
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
            AgnosticElement nodeTemplateSourceId = requirementsIdsNodeTemplateIdsDictionary
                    .get(source.getId());
            AgnosticElement nodeTemplateTargetId = capabilitiesIdsNodeTemplateIdsDictionary
                    .get(target.getId());
            addRelationAgnosticRelation(nodeTemplateSourceId,
                    nodeTemplateTargetId);
        }
    }

    private void addRelationAgnosticRelation(AgnosticElement source,
                                             AgnosticElement target) {
        List<AgnosticElement> targetValues;
        if (agnosticRelations.containsKey(source)) {
            targetValues = agnosticRelations.get(source);
        } else {
            targetValues = new LinkedList<AgnosticElement>();
        }
        targetValues.add(target);
        agnosticRelations.put(source, targetValues);
    }

    private void buildApplicationAgnosticMetadata() {
        if (serviceTemplateOfTopology != null) {
            this.setApplicationAgnosticMetadata(
                    new ApplicationAgnosticMetadata(this.serviceTemplateOfTopology));
        } else {
            this.setApplicationAgnosticMetadata(new ApplicationAgnosticMetadata());
        }
    }

    //<editor-fold desc="Getters and Setters">
    public List<AgnosticElement> getAgnosticElements() {
        return generatedAgnosticElements;
    }

    public Map<AgnosticElement, List<AgnosticElement>> getAgnosticRelations() {
        return agnosticRelations;
    }

    public ApplicationAgnosticMetadata getApplicationAgnosticMetadata() {
        return applicationAgnosticMetadata;
    }

    public void setApplicationAgnosticMetadata(ApplicationAgnosticMetadata applicationAgnosticMetadata) {
        this.applicationAgnosticMetadata = applicationAgnosticMetadata;
    }
    //</editor-fold>

    private class MatchingDictionary {

        Map<String, AgnosticElement> dictionary;

        public MatchingDictionary() {
            dictionary = new HashMap<String, AgnosticElement>();
        }

        public void addDictionaryEntrys(List<String> keys, AgnosticElement value) {
            if ((keys != null) && (value != null)) {
                for (String key : keys) {
                    dictionary.put(key, value);
                }
            }
        }

        public boolean containsKey(String key) {
            return dictionary.containsKey(key);
        }

        public AgnosticElement get(String key) {
            if (containsKey(key)) {
                return dictionary.get(key);
            } else {
                return null;
            }
        }

    }


}


