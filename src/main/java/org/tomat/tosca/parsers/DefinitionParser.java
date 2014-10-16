package org.tomat.tosca.parsers;

import org.opentosca.model.tosca.TCapability;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.TRelationshipTemplate;
import org.opentosca.model.tosca.TRequirement;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.AgnosticElementProvider;
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
//
public class DefinitionParser {

    //Esta es la que hay que eliminar
    //private List<AgnosticApplicationComponent> agnosticApplicationComponents = null;
    private Map<AgnosticElement, List<AgnosticElement>> agnosticApplicationsComponentRelations = null;
    private List<AgnosticElement> generatedAgnosticElements = null;
    private List<TNodeTemplate> nodeTemplatesOfTopology = null;
    private List<TRelationshipTemplate> relationshipTemplatesOfTopology = null;

    public DefinitionParser() {
        nodeTemplatesOfTopology = new LinkedList<TNodeTemplate>();
        relationshipTemplatesOfTopology = new LinkedList<TRelationshipTemplate>();
    }

    public DefinitionParser parsingApplicationTopology(String definitionFilePath)
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        return parsingApplicationTopology(new File(definitionFilePath));
    }

    private DefinitionParser parsingApplicationTopology(File definitionFilePath)
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        nodeTemplatesOfTopology = DefinitionUtils.getNodeTemplates(definitionFilePath);
        relationshipTemplatesOfTopology = DefinitionUtils
                .getRelationshipTemplates(definitionFilePath);
        return this;
    }

    public DefinitionParser buildAgnosticsElements()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException {
        buildAgnosticApplicationComponentList();
        buildAgnosticApplicationsComponentRelations();
        return this;
    }

    private void buildAgnosticApplicationComponentList()
            throws NodeTemplateTypeNotSupportedException {
        generatedAgnosticElements = new LinkedList<AgnosticElement>();
        for (TNodeTemplate nodeTemplate : nodeTemplatesOfTopology) {
            generatedAgnosticElements
                    .add(AgnosticElementProvider.createAgnosticElement(nodeTemplate));
        }
    }

    private void buildAgnosticApplicationsComponentRelations()
            throws TopologyTemplateFormatException {

        MatchingDictionary capabilityIdsNodeTemplateIsDictionary =
                createCapabilityIdsNodeTemplateIdsDictionary(generatedAgnosticElements);
        MatchingDictionary requirementIdsNodeTemplateIsDictionary =
                createRequirementIdsNodeTemplateIdsDictionary(generatedAgnosticElements);

        agnosticApplicationsComponentRelations =
                new HashMap<AgnosticElement, List<AgnosticElement>>();
        for (TRelationshipTemplate relationshipTemplate : relationshipTemplatesOfTopology) {
            addRelationTemplateToAgnosticApplicationComponentRelation(
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

    private void addRelationTemplateToAgnosticApplicationComponentRelation
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
            addRelationAgnosticApplicationComponentRelation(nodeTemplateSourceId,
                    nodeTemplateTargetId);
        }
    }

    private void addRelationAgnosticApplicationComponentRelation(AgnosticElement source,
                                                                 AgnosticElement target) {
        List<AgnosticElement> targetValues;
        if (agnosticApplicationsComponentRelations.containsKey(source)) {
            targetValues = agnosticApplicationsComponentRelations.get(source);
        } else {
            targetValues = new LinkedList<AgnosticElement>();
        }
        targetValues.add(target);
        agnosticApplicationsComponentRelations.put(source, targetValues);
    }

    //<editor-fold desc="Getters and Setters">
    public List<AgnosticElement> getAgnosticApplicationComponents() {
        return generatedAgnosticElements;
    }

    public Map<AgnosticElement, List<AgnosticElement>> getAgnosticApplicationsComponentRelations() {
        return agnosticApplicationsComponentRelations;
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


