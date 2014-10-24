package org.tomat.translate;

import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

/**
 * Created by Jose on 18/10/14.
 */
//TODO describe it is a abstract fabric client. The fabric is described by
public abstract class TechnologyTranslator {

    private AgnosticApplication agnosticApplication;

    public TechnologyTranslator(AgnosticApplication agnosticApplication){
        this.setAgnosticApplication(agnosticApplication);
    }

    public abstract TechnologyTranslator translate() throws NotSupportedTypeByTechnologyException;

    public abstract TechnologyElementsFactory getTechnologyElementFactory();

    public abstract void getTranslation();

    public abstract void configureRelations(TechnologyComponent technologyComponent);

    public AgnosticApplication getAgnosticApplication() {
        return agnosticApplication;
    }

    public void setAgnosticApplication(AgnosticApplication agnosticApplication) {
        this.agnosticApplication = agnosticApplication;
    }



}
