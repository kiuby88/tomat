package org.tomat.translate;

import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.graphs.AgnosticGraph;

/**
 * Created by Jose on 19/10/14.
 */
public interface TechnologyComponent extends TechnologyElement {

    public AgnosticComponent getAgnosticComponent();
    public void accept(TechnologyVisitorRelationConfiguration visit,
                       AgnosticComponent agnosticComponent,
                       AgnosticGraph agnosticGraph);

}
