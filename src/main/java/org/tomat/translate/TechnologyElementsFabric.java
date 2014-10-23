package org.tomat.translate;

import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

/**
 * Created by Jose on 23/10/14.
 */
public interface TechnologyElementsFabric {

    public TechnologyComponent buildTechnologyComponent(AgnosticElement agnosticElement)
            throws NotSupportedTypeByTechnologyException;
}
