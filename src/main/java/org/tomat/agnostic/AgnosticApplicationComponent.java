package org.tomat.agnostic;

import org.tomat.tosca.parsers.NodeTemplateParser;

/**
 * Created by MariaC on 23/09/2014.
 */
public abstract class AgnosticApplicationComponent {



    private final String name;
    private final String id;
    private final String type;

    public AgnosticApplicationComponent(NodeTemplateParser nodeTemplateParser){
        name=nodeTemplateParser.getName();
        id=nodeTemplateParser.getId();
        type=nodeTemplateParser.getType();
    }





}
