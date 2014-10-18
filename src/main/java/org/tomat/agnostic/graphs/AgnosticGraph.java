package org.tomat.agnostic.graphs;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.tomat.agnostic.Agnostic;
import org.tomat.agnostic.elements.AgnosticElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Jose on 15/10/14.
 */
public class AgnosticGraph implements Agnostic {

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

    private void addVertex(AgnosticElement agnosticElement) {
        agnosticGraph.addVertex(agnosticElement);
    }

    public Set<AgnosticElement> getVertexSet() {
        return agnosticGraph.vertexSet();
    }

    public int getNumberOfEdges() {
        return agnosticGraph.edgeSet().size();
    }

    /**
     * Returns a {@link List} view of the independent mappings contained in this map.
     * The returned list contains the elements which do not have out-going edges.
     *
     * @return The returned list contains the elements which do not have out-going edges.
     */
    public List<AgnosticElement> getIndependentVertex() {
        List<AgnosticElement> agnosticElementsWhioutOupPutDependences;
        agnosticElementsWhioutOupPutDependences = new LinkedList<AgnosticElement>();
        for (AgnosticElement agnosticElementVertex : this.getVertexSet()) {
            if (!hasOutgoingEdges(agnosticElementVertex)) {
                agnosticElementsWhioutOupPutDependences.add(agnosticElementVertex);
            }
        }
        return agnosticElementsWhioutOupPutDependences;
    }

    private boolean hasOutgoingEdges(AgnosticElement agnosticElementVertex) {
        //TODO check if element is contained in the graph
        Set<DefaultEdge> outgoingEdges = agnosticGraph.outgoingEdgesOf(agnosticElementVertex);
        return ((outgoingEdges != null) && (!outgoingEdges.isEmpty()));
    }

}
