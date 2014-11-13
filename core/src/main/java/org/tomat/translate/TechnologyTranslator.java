package org.tomat.translate;

import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.translate.brooklyn.exceptions.BrooklynVisitorRelationConfigurationNotSupportedType;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

import java.io.IOException;

/**
 * Created by Kiuby88 on 18/10/14.
 */

public abstract class TechnologyTranslator {

    private AgnosticApplication agnosticApplication;

    public TechnologyTranslator(AgnosticApplication agnosticApplication){
        this.setAgnosticApplication(agnosticApplication);
    }

    public abstract TechnologyTranslator translate() throws NotSupportedTypeByTechnologyException, BrooklynVisitorRelationConfigurationNotSupportedType;

    public abstract TechnologyComponentFactory getTechnologyComponentFactory();

    public abstract void configureRelations(TechnologyComponent technologyComponent) throws BrooklynVisitorRelationConfigurationNotSupportedType;

    public abstract String  print() throws IOException;

    public abstract void print(String file) throws IOException;

    public AgnosticApplication getAgnosticApplication() {
        return agnosticApplication;
    }

    public void setAgnosticApplication(AgnosticApplication agnosticApplication) {
        this.agnosticApplication = agnosticApplication;
    }



}
