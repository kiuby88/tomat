package org.tomat.translate.brooklyn.entity;

import org.tomat.translate.brooklyn.BrooklynElement;

/**
 * Created by Kiuby88 on 16/10/2014.
 */

public class BrooklynEntity implements BrooklynElement {

    public static String LOCATION_BY_DEFAULT = "localhost";

    private String id;
    private String name;
    private String location;

    protected BrooklynEntity(Builder builder) {
        this.setId(builder.id);
        this.setName(builder.name);
        this.setLocation(builder.location);
    }

    //<editor-fold desc="Getters and Setters>"
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    //</editor-fold>

    public static class Builder {
        private String id;
        private String name;
        private String location;

        public Builder() {
            this.name = null;
            id = null;
            location = LOCATION_BY_DEFAULT;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public BrooklynEntity build() {
            return new BrooklynEntity(this);
        }
    }


}
