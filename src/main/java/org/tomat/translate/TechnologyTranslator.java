package org.tomat.translate;

import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

/**
 * Created by Jose on 18/10/14.
 */
public abstract class TechnologyTranslator {

    private AgnosticApplication agnosticApplication;

    public TechnologyTranslator(AgnosticApplication agnosticApplication){
        this.setAgnosticApplication(agnosticApplication);
    }

    public abstract TechnologyTranslator translate() throws NotSupportedTypeByTechnologyException;

    public abstract TechnologyComponent getTechnologyComponentTranslation(AgnosticElement agnosticElement)
            throws NotSupportedTypeByTechnologyException;

    /**
     * Este se supone genera el yaml
     */
    public abstract void getTranslation();

    public AgnosticApplication getAgnosticApplication() {
        return agnosticApplication;
    }

    public void setAgnosticApplication(AgnosticApplication agnosticApplication) {
        this.agnosticApplication = agnosticApplication;
    }



}
