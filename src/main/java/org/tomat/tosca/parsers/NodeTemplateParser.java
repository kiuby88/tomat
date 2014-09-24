package org.tomat.tosca.parsers;

import org.jdom2.Element;

/**
 * Created by MariaC on 24/09/2014.
 */
public class NodeTemplateParser {


    private final static String NODE_TEMPLATE_ATTRIBUTE_ID="id";
    private final static String NODE_TEMPLATE_ATTRIBUTE_NAME="name";
    private final static String NODE_TEMPLATE_ATTRIBUTE_TYPE="type";
    private final String DESCRIPTION="description";

    private String id;
    private String name;
    private String type;
    private String description;
    private Element ntJdomElement;

    public NodeTemplateParser(Element ntJdomElement){
        this.ntJdomElement=ntJdomElement;
        //TODO es necesario comprobar si el tipo es el esperado para lo que se puede usar ExpectedToscaElementException
        initGenericValues();
    }

    public void initGenericValues(){
        id=getAttributeValue(NODE_TEMPLATE_ATTRIBUTE_ID);
        name=getAttributeValue(NODE_TEMPLATE_ATTRIBUTE_NAME);
        type=getAttributeValue(NODE_TEMPLATE_ATTRIBUTE_TYPE);
        description=null;//TODO
    }

    private String  getAttributeValue(String attributeName){
        return (ntJdomElement.getAttributeValue(attributeName)!=null) ?
                ntJdomElement.getAttributeValue(attributeName): "";
    }
}
