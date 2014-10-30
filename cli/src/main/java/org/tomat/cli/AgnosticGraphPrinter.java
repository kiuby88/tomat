package org.tomat.cli;

import org.tomat.agnostic.Agnostic;
import org.tomat.agnostic.graphs.AgnosticGraph;

import javax.swing.*;

/**
 * Created by Jose on 30/10/14.
 */
public class AgnosticGraphPrinter {

    AgnosticGraph agnosticGraph;

    public AgnosticGraphPrinter(AgnosticGraph agnosticGraph){

        this.agnosticGraph=agnosticGraph;

    }

    public void printGraph(){
        JGraphAdapterDemo applet = new JGraphAdapterDemo(agnosticGraph);
        applet.init();
        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }




}
