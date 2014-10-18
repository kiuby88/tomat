package org.tomat.agnostic.application;

import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.tosca.parsers.DefinitionParser;

/**
 * Created by Jose on 18/10/14.
 */
public class AgnosticApplication {


    private ApplicationAgnosticMetadata metadata;
    private AgnosticGraph agnosticGraph;

    public AgnosticApplication(DefinitionParser definitionParser) {
        setMetadata(definitionParser.getApplicationAgnosticMetadata());
        setAgnosticGraph(new AgnosticGraph(
                definitionParser.getAgnosticElements(),
                definitionParser.getAgnosticRelations()));
    }

    public AgnosticApplication(ApplicationAgnosticMetadata metadata, AgnosticGraph agnosticGraph) {
        this.metadata = metadata;
        this.agnosticGraph = agnosticGraph;
    }

    //<editor-fold desc="Getters and Setters">
    public ApplicationAgnosticMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ApplicationAgnosticMetadata metadata) {
        this.metadata = metadata;
    }

    public AgnosticGraph getAgnosticGraph() {
        return agnosticGraph;
    }

    public void setAgnosticGraph(AgnosticGraph agnosticGraph) {
        this.agnosticGraph = agnosticGraph;
    }
    //</editor-fold>
}
