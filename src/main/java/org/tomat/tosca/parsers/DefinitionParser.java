package org.tomat.tosca.parsers;

import org.jdom2.Document;
import org.jdom2.Element;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.TRelationshipTemplate;
import org.tomat.agnostic.AgnosticApplicationComponent;
import org.tomat.exceptions.TopologyTemplateFormatException;

import java.io.File;
import java.util.*;

import org.opentosca.model.tosca.utils.DefinitionUtils;

/**
 * Created by JoseC on 23/09/2014.
 */
//TODO check the clean code to specifi <variableName>List (e.g.).
//Esta clase contendrá un metodo para parsear el fichero que contiene la topología
public class DefinitionParser {

    private LinkedList<AgnosticApplicationComponent> agnosticApplicationComponents;
    private LinkedHashMap<String, List<String>> agnosticApplicationsComponentRelations;

    public DefinitionParser() {
        agnosticApplicationComponents = new LinkedList<AgnosticApplicationComponent>();
        agnosticApplicationsComponentRelations = new LinkedHashMap<String, List<String>>();
    }

    public DefinitionParser parsingApplicationTopology(String definitionFilePath) throws TopologyTemplateFormatException {
        parsingApplicationTopology(new File(definitionFilePath));
        return this;
    }

    public List<AgnosticApplicationComponent> getAgnosticApplicationComponents(){
        return agnosticApplicationComponents;
    }

    public Map<String, List<String>> getAgnosticApplicationsComponentRelations(){
        return agnosticApplicationsComponentRelations;
    }

    private void parsingApplicationTopology(File definitionFilePath) throws TopologyTemplateFormatException {
        List<TNodeTemplate> nodeTemplates = DefinitionUtils.getNodeTemplates(definitionFilePath);
        List<TRelationshipTemplate> relationshipTemplates = DefinitionUtils.getRelationshipTemplates(definitionFilePath);
        generateAgnosticApplicationComponentList(nodeTemplates);
        generateAgnosticApplicationsComponentRelations(relationshipTemplates);
    }

    private void generateAgnosticApplicationComponentList(List<TNodeTemplate> nodeTemplates) {
        NodeTemplateParser nodeTemplateParser;
        for (TNodeTemplate nodeTemplate : nodeTemplates) {
            nodeTemplateParser = NodeTemplateParserProvider.createNodeTemplateParser(nodeTemplate);
        }
    }

    private void generateAgnosticApplicationsComponentRelations(List<TRelationshipTemplate> relationshipTemplates)
            throws TopologyTemplateFormatException {
        TNodeTemplate source, targer;
        for (TRelationshipTemplate relationshipTemplate : relationshipTemplates) {
            addRelationTemplateToAgnosticApplicationComponentRelation(relationshipTemplate);
        }
    }

    private void addRelationTemplateToAgnosticApplicationComponentRelation(TRelationshipTemplate relationshipTemplate)
            throws TopologyTemplateFormatException {
        TNodeTemplate source = (TNodeTemplate) relationshipTemplate.getSourceElement().getRef();
        TNodeTemplate target = (TNodeTemplate) relationshipTemplate.getTargetElement().getRef();
        if ((source == null) || (target == null))
            throw new TopologyTemplateFormatException("Inconsistent relation " + relationshipTemplate.getId()
                    + " source or target do not defined correctly");
        else
            addRelationAgnosticApplicationComponentRelation(source.getId(), target.getId());
    }

    private void addRelationAgnosticApplicationComponentRelation(String source, String target) {
        List<String> targetValues;
        if (agnosticApplicationsComponentRelations.containsValue(source))
            targetValues = agnosticApplicationsComponentRelations.get(source);
        else
            targetValues = new LinkedList<String>();
        targetValues.add(target.toLowerCase());
        agnosticApplicationsComponentRelations.put(source.toLowerCase(), targetValues);
    }






}


