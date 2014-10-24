package org.tomat.translate.brooklyn.print;

import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.beans.IntrospectionException;
import java.util.*;

/**
 * Created by Jose on 23/10/14.
 */
public class ReversedPropertyUtils extends PropertyUtils {
    @Override
    protected Set<Property> createPropertySet(Class<? extends Object> type, BeanAccess bAccess)
            throws IntrospectionException {

        Set<Property> result = null;
        if (BrooklynServiceEntity.class.isAssignableFrom(type)) {
            result = reverseOrderProperties(type, bAccess);
        } else if (BrooklynApplicationEntity.class.isAssignableFrom(type)) {
            result = brooklynApplicationSort(type, bAccess);
        } else {
            result = super.createPropertySet(type, bAccess);
        }
        return result;
    }

    private Set<Property> reverseOrderProperties(Class<? extends Object> type, BeanAccess bAccess)
            throws IntrospectionException {
        Set<Property> result = new TreeSet<>(Collections.reverseOrder());
        result.addAll(super.createPropertySet(type, bAccess));
        return result;
    }

    private Set<Property> brooklynApplicationSort(Class<? extends Object> type, BeanAccess bAccess)
            throws IntrospectionException {
        Set<Property> result = new LinkedHashSet<>();

        Property propertyId = null;
        Property propertyName = null;
        Property propertyLocation = null;
        Property propertyServices = null;



        for (Property property : super.createPropertySet(type, bAccess)) {

            if (property.getName().equals("id")) {

                propertyId = property;
            }
            if (property.getName().equals("name")) {
                propertyName = property;
            }
            if (property.getName().equals("location")) {
                propertyLocation = property;


            }
            if (property.getName().equals("services")) {
                propertyServices = property;
            }
        }

        result.add(propertyName);
        result.add(propertyId);
        result.add(propertyLocation);
        result.add(propertyServices);

        return result;
    }

}
