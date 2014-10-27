package org.tomat.agnostic.graphs;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.tomat.agnostic.Agnostic;
import org.tomat.agnostic.components.AgnosticComponent;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Jose on 15/10/14.
 */
public class AgnosticGraph implements Agnostic {

    DirectedGraph<AgnosticComponent, DefaultEdge> agnosticGraph;

    public AgnosticGraph(List<AgnosticComponent> agnosticComponents,
                         Map<AgnosticComponent, List<AgnosticComponent>> relations) {
        agnosticGraph =
                new DefaultDirectedGraph<AgnosticComponent, DefaultEdge>(DefaultEdge.class);
        initVertex(agnosticComponents);
        initEdges(relations);

    }

    private void initVertex(List<AgnosticComponent> agnosticComponents) {
        for (AgnosticComponent agnosticComponent : agnosticComponents)
            addVertex(agnosticComponent);
    }

    private void initEdges(Map<AgnosticComponent, List<AgnosticComponent>> relations) {
        Set<AgnosticComponent> agnosticComponentKeySet = relations.keySet();
        for (AgnosticComponent sourceAgnosticComponent : agnosticComponentKeySet)
            for (AgnosticComponent targetAgnosticComponent : relations.get(sourceAgnosticComponent))
                agnosticGraph.addEdge(sourceAgnosticComponent, targetAgnosticComponent);
    }

    //TODO refactor name of agnosticComponent to vertex in the parameter of the method
    private void addVertex(AgnosticComponent agnosticComponent) {
        agnosticGraph.addVertex(agnosticComponent);
    }

    public Set<AgnosticComponent> getVertexSet() {
        return agnosticGraph.vertexSet();
    }

    public int getNumberOfEdges() {
        return agnosticGraph.edgeSet().size();
    }

    /**
     * Returns a {@link List} view of the independent mappings contained in this map.
     * The returned list contains the components which do not have out-going edges.
     *
     * @return The returned list contains the components which do not have out-going edges.
     */
    public List<AgnosticComponent> getIndependentVertex() {
        List<AgnosticComponent> agnosticComponentWithoutOupPutDependencies;
        agnosticComponentWithoutOupPutDependencies = new LinkedList<>();
        for (AgnosticComponent agnosticComponentVertex : this.getVertexSet()) {
            if (!hasOutgoingEdges(agnosticComponentVertex)) {
                agnosticComponentWithoutOupPutDependencies.add(agnosticComponentVertex);
            }
        }
        return agnosticComponentWithoutOupPutDependencies;
    }

    //TODO refactor name of agnosticComponent to vertex in the parameter of the method
    private boolean hasOutgoingEdges(AgnosticComponent agnosticComponentVertex) {
        //TODO check if component is contained in the graph
        Set<DefaultEdge> outgoingEdges = agnosticGraph.outgoingEdgesOf(agnosticComponentVertex);
        return ((outgoingEdges != null) && (!outgoingEdges.isEmpty()));
    }

    //TODO refactor name of agnosticComponent to vertex in the parameter of the method
    public Set<DefaultEdge> getIncomingEdgesOf(AgnosticComponent agnosticComponent){
        return agnosticGraph.incomingEdgesOf(agnosticComponent);
    }

    //TODO refactor name of agnosticComponent to vertex in the parameter of the method
    public List<AgnosticComponent> getIncomingVertexOf(AgnosticComponent agnosticComponent){
        List<AgnosticComponent> result=null;
        Set<DefaultEdge> incomingVertex= getIncomingEdgesOf(agnosticComponent);
        if(incomingVertex!=null){
            result=new LinkedList<>();
            for(DefaultEdge edge : incomingVertex){
                result.add(agnosticGraph.getEdgeSource(edge));
            }
        }
        return result;
    }

    //TODO refactor name of agnosticComponent to vertex in the parameter of the method
    public Set<DefaultEdge> getOutcomingEdgesOf(AgnosticComponent agnosticComponent){
        return agnosticGraph.outgoingEdgesOf(agnosticComponent);
    }

    //TODO refactor name of agnosticComponent to vertex in the parameter of the method
    public List<AgnosticComponent> getOutcomigngVertexOf(AgnosticComponent agnosticComponent){
        List<AgnosticComponent> result=null;
        Set<DefaultEdge> outcomingVertex= getOutcomingEdgesOf(agnosticComponent);
        if(outcomingVertex!=null){
            result=new LinkedList<>();
            for(DefaultEdge edge : outcomingVertex){
                result.add(agnosticGraph.getEdgeTarget(edge));
            }
        }
        return result;
    }


    public boolean containOutcomingRelationByType(AgnosticComponent webAppAgnostic,
                                                   Class<? extends AgnosticComponent> type) {
        return findAgnosticComponentOutComingByType(webAppAgnostic, type) != null;
    }

    public AgnosticComponent findAgnosticComponentOutComingByType(AgnosticComponent agnosticComponent,
                                                                  Class<? extends AgnosticComponent> type) {
        List<AgnosticComponent> outcomingVertex = this
                .getOutcomigngVertexOf(agnosticComponent);
        return findAgnosticComponentOutComingByType(outcomingVertex, type);
    }

    private AgnosticComponent findAgnosticComponentOutComingByType(List<AgnosticComponent> outcomigngVertex,
                                                                   Class<? extends AgnosticComponent> type) {
        AgnosticComponent result = null;
        if ((outcomigngVertex != null) && (!outcomigngVertex.isEmpty())) {
            for (AgnosticComponent agnosticComponent : outcomigngVertex) {
                if (type.isAssignableFrom(agnosticComponent.getClass())) {
                    result =  agnosticComponent;
                }
            }
        }
        return result;
    }

}
