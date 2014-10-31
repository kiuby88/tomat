package org.tomat.agnostic.graphs.printer;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.graphs.AgnosticGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Created by Kiuby88 on 30/10/14.
 */

public class JGraphAdapterPrinter
        extends JApplet implements Runnable{

    private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphModelAdapter<String, DefaultEdge> jgAdapter;

    private AgnosticGraph agnosticGraph;
    Thread t;
    public JGraphAdapterPrinter(AgnosticGraph agnosticGraph) {
        this.agnosticGraph = agnosticGraph;
    }


    public void init() {
        ListenableGraph<String, DefaultEdge> g =
                new ListenableDirectedMultigraph<>(
                        DefaultEdge.class);

        jgAdapter = new JGraphModelAdapter<>(g);
        JGraph jgraph = new JGraph(jgAdapter);

        adjustDisplaySettings(jgraph);
        getContentPane().add(jgraph);
        resize(DEFAULT_SIZE);
        this.setName("TOMAT. Agnostic Graph Viewer");
        addVertex(g);
        addEdges(g);
        randomizeLocations();
        t=new Thread(this);
        t.start();
    }

    private void addVertex(ListenableGraph<String, DefaultEdge>  g){
        for (AgnosticComponent agnosticComponentSource : agnosticGraph.getVertexSet()) {
            g.addVertex(agnosticComponentSource.getId());
        }
    }

    private void addEdges(ListenableGraph<String, DefaultEdge> g){
        for (AgnosticComponent agnosticComponentSource : agnosticGraph.getVertexSet()) {
            addOutcomingEdgesOfAVertex(g, agnosticComponentSource);
        }
    }

    private void addOutcomingEdgesOfAVertex(ListenableGraph<String, DefaultEdge> g, AgnosticComponent agnosticComponentSource) {
        if (agnosticGraph.getOutcomigngVertexOf(agnosticComponentSource) != null) {
            for (AgnosticComponent target : agnosticGraph.getOutcomigngVertexOf(agnosticComponentSource)) {
                g.addEdge(agnosticComponentSource.getId(), target.getId());
            }
        }
    }

    private void adjustDisplaySettings(JGraph jg) {
        jg.setPreferredSize(DEFAULT_SIZE);

        Color c = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter("bgcolor");
        } catch (Exception e) {
        }

        if (colorStr != null) {
            c = Color.decode(colorStr);
        }

        jg.setBackground(c);
    }

    public void randomizeLocations() {
        int H,W;
        Random r = new Random();
        H=this.getHeight();
        W=this.getWidth();

        for(AgnosticComponent agnosticComponentSource : agnosticGraph.getVertexSet()){
            positionVertexAt(agnosticComponentSource.getId(),r.nextInt(W),r.nextInt(H));
        }
    }

    @SuppressWarnings("unchecked") // FIXME hb 28-nov-05: See FIXME below
    private void positionVertexAt(Object vertex, int x, int y) {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
                new Rectangle2D.Double(
                        x,
                        y,
                        bounds.getWidth(),
                        bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);

        // TODO: Clean up generics once JGraph goes generic
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
    }

    @Override
    public void run() {

    }

    /**
     * a listenable directed multigraph that allows loops and parallel edges.
     */
    private static class ListenableDirectedMultigraph<V, E>
            extends DefaultListenableGraph<V, E>
            implements DirectedGraph<V, E> {
        private static final long serialVersionUID = 1L;

        ListenableDirectedMultigraph(Class<E> edgeClass) {
            super(new DirectedMultigraph<V, E>(edgeClass));
        }
    }
}
