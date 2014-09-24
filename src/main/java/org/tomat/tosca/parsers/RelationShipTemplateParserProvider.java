package org.tomat.tosca.parsers;

import org.jdom2.Element;

/**
 * Created by MariaC on 24/09/2014.
 */
public class RelationShipTemplateParserProvider {

    final static String RELATIONSHIP_TEMPLATE_TYPE_ATTRIBUTE="type";
    //Fabric method to describe the element
    public RelationShipTemplateParser createRelationShipTemplateParser(Element relationShipTemplateDomElement){
        //More RelationShipTypes could be added
        return new RelationShipTemplateParser();
    }

    private String getRelationShipTemplateType(Element nodeTemplateDomElement){
        //TODO Element have to be a RelationShipTemplateElement
        String  relationShipType = nodeTemplateDomElement.getAttributeValue(RELATIONSHIP_TEMPLATE_TYPE_ATTRIBUTE);
        return relationShipType;
    }

}
