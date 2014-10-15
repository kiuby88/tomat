package org.tomat.agnostic.graphs;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.tomat.agnostic.elements.AgnosticElement;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Jose on 15/10/14.
 */
public class AgnosticGraph {

    DirectedGraph<AgnosticElement, DefaultEdge> agnosticGraph;

    public AgnosticGraph(List<AgnosticElement> agnosticElements, Map<AgnosticElement, List<AgnosticElement>> relations) {
        agnosticGraph =
                new DefaultDirectedGraph<AgnosticElement, DefaultEdge>(DefaultEdge.class);
        initVertex(agnosticElements);
        initEdges(relations);

    }

    private void initVertex(List<AgnosticElement> agnosticElements) {
        for (AgnosticElement agnosticElement : agnosticElements)
            addVertex(agnosticElement);
    }

    private void initEdges(Map<AgnosticElement, List<AgnosticElement>> relations) {
        Set<AgnosticElement> agnosticElementKeySet = relations.keySet();
        for (AgnosticElement sourceAgnosticElement : agnosticElementKeySet)
            for (AgnosticElement targetAgnosticElement : relations.get(sourceAgnosticElement))
                agnosticGraph.addEdge(sourceAgnosticElement, targetAgnosticElement);
    }

    public void addVertex(AgnosticElement agnosticElement) {
        agnosticGraph.addVertex(agnosticElement);
    }

}
