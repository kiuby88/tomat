package org.tomat.agnostic.graphs;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.tomat.agnostic.elements.AgnosticElement;

import java.util.List;
import java.util.Map;


/**
 * Created by Jose on 15/10/14.
 */
public class AgnosticGraph {

    DirectedGraph<AgnosticElement, DefaultEdge> agnosticGraph;

    public AgnosticGraph(List<AgnosticElement> agnosticElements, Map<String, List<String>> relations) {

        agnosticGraph =
                new DefaultDirectedGraph<AgnosticElement, DefaultEdge>(DefaultEdge.class);
        initVertex(agnosticElements, relations);

    }

    private void initVertex(List<AgnosticElement> agnosticElements, Map<String, List<String>> relations) {
        for (AgnosticElement agnosticElement : agnosticElements)
            addVertex(agnosticElement);
    }

    private void initEdges(List<AgnosticElement> agnosticElements, Map<String , List<String>> relations){
        //es necesario encontrar las relationes entre componentes


    }

    public void addVertex(AgnosticElement agnosticElement) {
        agnosticGraph.addVertex(agnosticElement);
    }






}
