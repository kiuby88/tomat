package org.tomat.agnostic.graphs.printer;

import org.tomat.agnostic.graphs.AgnosticGraph;

import javax.swing.*;

/**
 * Created by Kiuby88 on 30/10/14.
 */
public class AgnosticGraphJGraphPrinter implements AgnosticGraphPrinter {

    AgnosticGraph agnosticGraph;

    public AgnosticGraphJGraphPrinter(AgnosticGraph agnosticGraph){

        this.agnosticGraph=agnosticGraph;

    }

    @Override
    public void printGraph(){
        JGraphAdapterPrinter applet = new JGraphAdapterPrinter(agnosticGraph);
        applet.init();
        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("TOMAT -- Agnostic Graph Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }




}
