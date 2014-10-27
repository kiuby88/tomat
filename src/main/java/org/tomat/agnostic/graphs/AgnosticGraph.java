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

    //TODO refactor name of agnosticElement to vertex in the parameter of the method
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
        List<AgnosticElement> agnosticElementsWithoutOupPutDependencies;
        agnosticElementsWithoutOupPutDependencies = new LinkedList<>();
        for (AgnosticElement agnosticElementVertex : this.getVertexSet()) {
            if (!hasOutgoingEdges(agnosticElementVertex)) {
                agnosticElementsWithoutOupPutDependencies.add(agnosticElementVertex);
            }
        }
        return agnosticElementsWithoutOupPutDependencies;
    }

    //TODO refactor name of agnosticElement to vertex in the parameter of the method
    private boolean hasOutgoingEdges(AgnosticElement agnosticElementVertex) {
        //TODO check if element is contained in the graph
        Set<DefaultEdge> outgoingEdges = agnosticGraph.outgoingEdgesOf(agnosticElementVertex);
        return ((outgoingEdges != null) && (!outgoingEdges.isEmpty()));
    }

    //TODO refactor name of agnosticElement to vertex in the parameter of the method
    public Set<DefaultEdge> getIncomingEdgesOf(AgnosticElement agnosticElement){
        return agnosticGraph.incomingEdgesOf(agnosticElement);
    }

    //TODO refactor name of agnosticElement to vertex in the parameter of the method
    public List<AgnosticElement> getIncomingVertexOf(AgnosticElement agnosticElement){
        List<AgnosticElement> result=null;
        Set<DefaultEdge> incomingVertex= getIncomingEdgesOf(agnosticElement);
        if(incomingVertex!=null){
            result=new LinkedList<>();
            for(DefaultEdge edge : incomingVertex){
                result.add(agnosticGraph.getEdgeSource(edge));
            }
        }
        return result;
    }








    //TODO refactor name of agnosticElement to vertex in the parameter of the method
    public Set<DefaultEdge> getOutcomingEdgesOf(AgnosticElement agnosticElement){
        return agnosticGraph.outgoingEdgesOf(agnosticElement);
    }

    //TODO refactor name of agnosticElement to vertex in the parameter of the method
    public List<AgnosticElement> getOutcomigngVertexOf(AgnosticElement agnosticElement){
        List<AgnosticElement> result=null;
        Set<DefaultEdge> outcomingVertex= getOutcomingEdgesOf(agnosticElement);
        if(outcomingVertex!=null){
            result=new LinkedList<>();
            for(DefaultEdge edge : outcomingVertex){
                result.add(agnosticGraph.getEdgeTarget(edge));
            }
        }
        return result;
    }

}
