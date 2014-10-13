package org.tomat.tosca.parsers;

import org.opentosca.model.tosca.TCapability;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.TRelationshipTemplate;
import org.opentosca.model.tosca.TRequirement;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.AgnosticApplicationComponent;
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

    private List<AgnosticApplicationComponent> agnosticApplicationComponents = null;
    private Map<String, List<String>> agnosticApplicationsComponentRelations = null;
    private List<NodeTemplateParser> generatedNodeTemplateParsers = null;
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
        generatedNodeTemplateParsers = getNodeTemplateParsers(nodeTemplatesOfTopology);

        //buildAgnosticApplicationComponentList(nodeTemplateParsers);
        //buildAgnosticApplicationsComponentRelations(relationshipTemplates, nodeTemplateParsers);
        return this;
    }


    public List<NodeTemplateParser> getNodeTemplateParsers(List<TNodeTemplate> nodeTemplates)
            throws NodeTemplateTypeNotSupportedException {
        LinkedList<NodeTemplateParser> nodeTemplateParserList = new LinkedList<NodeTemplateParser>();
        for (TNodeTemplate nodeTemplate : nodeTemplates) {
            nodeTemplateParserList
                    .add(NodeTemplateParserProvider.createNodeTemplateParser(nodeTemplate));
        }
        return nodeTemplateParserList;
    }

    public DefinitionParser buildAgnosticsElements()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException {
        buildAgnosticApplicationComponentList();
        buildAgnosticApplicationsComponentRelations();
        return this;
    }

    private void buildAgnosticApplicationComponentList()
            throws NodeTemplateTypeNotSupportedException {
        agnosticApplicationComponents = new LinkedList<AgnosticApplicationComponent>();
        for (NodeTemplateParser nodeTemplateParser : generatedNodeTemplateParsers) {
            //agnosticApplicationComponents.add(nodeTemplateParser.getAgnosticApplicationComponent());
        }
    }

    private void buildAgnosticApplicationsComponentRelations()
            throws TopologyTemplateFormatException {
        MatchingDictionary capabilityIdsNodeTempateIsDictionary =
                createCapabilityIdsNodeTemplateIsDictionary(generatedNodeTemplateParsers);
        MatchingDictionary requirementIdsNodeTempateIsDictionary =
                createRequirementIdsNodeTemplateIsDictionary(generatedNodeTemplateParsers);

        agnosticApplicationsComponentRelations = new HashMap<String, List<String>>();
        for (TRelationshipTemplate relationshipTemplate : relationshipTemplatesOfTopology) {
            addRelationTemplateToAgnosticApplicationComponentRelation(
                    relationshipTemplate,
                    capabilityIdsNodeTempateIsDictionary,
                    requirementIdsNodeTempateIsDictionary);
        }
    }

    private MatchingDictionary createCapabilityIdsNodeTemplateIsDictionary(List<NodeTemplateParser> nodeTemplateParsers) {
        MatchingDictionary dictionary = new MatchingDictionary();
        for (NodeTemplateParser nodeTemplateParser : nodeTemplateParsers)
            dictionary.addDictionaryEntrys(nodeTemplateParser.getCapabilitiesIds(), nodeTemplateParser.getId());
        return dictionary;
    }

    private MatchingDictionary createRequirementIdsNodeTemplateIsDictionary(List<NodeTemplateParser> nodeTemplateParsers) {
        MatchingDictionary dictionary = new MatchingDictionary();
        for (NodeTemplateParser nodeTemplateParser : nodeTemplateParsers)
            dictionary.addDictionaryEntrys(nodeTemplateParser.getRequirementsIds(), nodeTemplateParser.getId());
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
    public List<AgnosticApplicationComponent> getAgnosticApplicationComponents() {
        return agnosticApplicationComponents;
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


