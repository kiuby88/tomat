package org.tomat.translate;

import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.graphs.AgnosticGraph;

/**
 * Created by Jose on 19/10/14.
 */
public interface TechnologyComponent extends TechnologyElement {

    public AgnosticElement getAgnosticElement();
    public void accept(TechnologyVisitorRelationConfiguration visit,
                       AgnosticElement agnosticElement,
                       AgnosticGraph agnosticGraph);

}
