package org.tomat.translate.brooklyn.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kiuby88 on 18/10/14.
 */
public class BrooklynApplicationEntity extends BrooklynEntity {

    private List<BrooklynServiceEntity> services;

    protected BrooklynApplicationEntity(Builder builder) {
        super(builder);
        this.setServices(builder.services);
    }

    public void addService(BrooklynServiceEntity brooklynServiceEntity){
        services.add(brooklynServiceEntity);
    }

    public List<BrooklynServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<BrooklynServiceEntity> services) {
        this.services = services;
    }

    public static class Builder extends BrooklynEntity.Builder {

        private List<BrooklynServiceEntity> services;

        public Builder() {
            services = new LinkedList<>();
        }

        public Builder services(List<BrooklynServiceEntity> services) {
            this.services = services;
            return this;
        }

        @Override
        public BrooklynApplicationEntity build() {
            return new BrooklynApplicationEntity(this);
        }
    }
}
