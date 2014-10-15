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
//Esta clase contendrá un metodo para parsear el fichero que contiene la topología
public class DefinitionParser {

    //Esta es la que hay que eliminar
    //private List<AgnosticApplicationComponent> agnosticApplicationComponents = null;
    private Map<String, List<String>> agnosticApplicationsComponentRelations = null;
    private List<AgnosticElement> generatedAgnosticElements = null;
    private List<TNodeTemplate> nodeTemplatesOfTopology = null;
    private List<TRelationshipTemplate> relationshipTemplatesOfTopology = null;

    public DefinitionParser() {

    }

    public DefinitionParser parsingApplicationTopology(String definitionFilePath)
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        return parsingApplicationTopology(new File(definitionFilePath));
    }

    private DefinitionParser parsingApplicationTopology(File definitionFilePath)
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {

        nodeTemplatesOfTopology = DefinitionUtils.getNodeTemplates(definitionFilePath);
        relationshipTemplatesOfTopology = DefinitionUtils.getRelationshipTemplates(definitionFilePath);
        //generatedAgnosticElements = getNodeTemplateParsers(nodeTemplatesOfTopology);
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
        MatchingDictionary capabilityIdsNodeTempateIsDictionary =
                createCapabilityIdsNodeTemplateIdsDictionary(generatedAgnosticElements);
        MatchingDictionary requirementIdsNodeTempateIsDictionary =
                createRequirementIdsNodeTemplateIdsDictionary(generatedAgnosticElements);

        agnosticApplicationsComponentRelations = new HashMap<String, List<String>>();
        for (TRelationshipTemplate relationshipTemplate : relationshipTemplatesOfTopology) {
            addRelationTemplateToAgnosticApplicationComponentRelation(
                    relationshipTemplate,
                    capabilityIdsNodeTempateIsDictionary,
                    requirementIdsNodeTempateIsDictionary);
        }
    }

    private MatchingDictionary createCapabilityIdsNodeTemplateIdsDictionary(List<AgnosticElement> agnosticElements) {
        MatchingDictionary dictionary = new MatchingDictionary();
        for (AgnosticElement agnosticElement : agnosticElements)
            dictionary.addDictionaryEntrys(agnosticElement.getCapabilitiesIds(), agnosticElement.getId());
        return dictionary;
    }

    private MatchingDictionary createRequirementIdsNodeTemplateIdsDictionary(List<AgnosticElement> agnosticElements) {
        MatchingDictionary dictionary = new MatchingDictionary();
        for (AgnosticElement agnosticElement : agnosticElements)
            dictionary.addDictionaryEntrys(agnosticElement.getRequirementsIds(), agnosticElement.getId());
        return dictionary;
    }

    private void addRelationTemplateToAgnosticApplicationComponentRelation
            (TRelationshipTemplate relationshipTemplate,
             MatchingDictionary capabilitiesIdsNodeTemplateIdsDictionary,
             MatchingDictionary requirementsIdsNodeTemplateIdsDictionary) throws TopologyTemplateFormatException {

        TRequirement source = (TRequirement) relationshipTemplate.getSourceElement().getRef();
        TCapability target = (TCapability) relationshipTemplate.getTargetElement().getRef();

        if ((source == null) || (target == null))
            throw new TopologyTemplateFormatException("Inconsistent relation " + relationshipTemplate.getId()
                    + " source or target do not defined correctly");
        else {
            String nodeTemplateSourceId = requirementsIdsNodeTemplateIdsDictionary.get(source.getId()).toLowerCase();
            String nodeTemplateTargetId = capabilitiesIdsNodeTemplateIdsDictionary.get(target.getId()).toLowerCase();
            addRelationAgnosticApplicationComponentRelation(nodeTemplateSourceId, nodeTemplateTargetId);
        }
    }

    private void addRelationAgnosticApplicationComponentRelation(String source, String target) {
        List<String> targetValues;
        if (agnosticApplicationsComponentRelations.containsKey(source))
            targetValues = agnosticApplicationsComponentRelations.get(source);
        else
            targetValues = new LinkedList<String>();
        targetValues.add(target.toLowerCase());
        agnosticApplicationsComponentRelations.put(source.toLowerCase(), targetValues);
    }

    //<editor-fold desc="Getters and Setters">
    public List<AgnosticElement> getAgnosticApplicationComponents() {
        return generatedAgnosticElements;
    }

    public Map<String, List<String>> getAgnosticApplicationsComponentRelations() {
        return agnosticApplicationsComponentRelations;
    }
    //</editor-fold>

    private class MatchingDictionary {

        Map<String, String> dictionary;

        public MatchingDictionary() {
            dictionary = new HashMap<String, String>();
        }

        /**
         * Add the key list using the same value.
         * If keys or values are null, any element will be added.
         *
         * @param keys
         * @param value
         */
        public void addDictionaryEntrys(List<String> keys, String value) {
            if ((keys != null) && (value != null))
                for (String key : keys)
                    dictionary.put(key, value);
        }

        public boolean containsKey(String key) {
            return dictionary.containsKey(key);
        }

        public String get(String key) {
            if (containsKey(key))
                return dictionary.get(key);
            else
                return null;
        }

    }


}


