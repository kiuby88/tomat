package org.tomat.agnostic.elements;

import org.tomat.agnostic.properties.AgnosticProperty;

import java.util.*;

/**
 * Created by Jose on 09/10/14.
 */
public class AgnosticElementUtils {

    public static String findValueMapUsingCollection(Map<String, String> map,
                                                     Collection<String> keysCollection) {
        return findValueMapUsingCollection(map, ((String[])keysCollection.toArray()));
    }

    public static String findValueMapUsingCollection(Map<String, String> map,
                                                    String[] keysCollection) {
        String key = anyKeyFromCollectionIsAMapKey(keysCollection, map);
        if (key != null) {
            return map.get(key);
        }
        return null;
    }

    public static String anyKeyFromCollectionIsAMapKey(String[] keysCollection,
                                                        Map<String, String> map) {
        String keyLowerCase;
        for (String key : keysCollection) {
            keyLowerCase=key.toLowerCase();
            if (map.containsKey(keyLowerCase)){
                return keyLowerCase;
            }
        }
        return null;
    }

    //TODO this method is able to deleted if it is not used.
    private static boolean isContainsIgnoreCase(Set<String> elementSet, String item){
        for (String element : elementSet){
            if(item.equalsIgnoreCase(element)){
                return true;
            }
        }
        return false;
    }

    public static AgnosticElement findAgnosticElementById(Collection<AgnosticElement> agnosticElements,
                                                          String id) {
        AgnosticElement result = null;
        for (AgnosticElement agnosticElement : agnosticElements)
            if (agnosticElement.getId().equalsIgnoreCase(id))
                result = agnosticElement;
        return result;
    }

    public static Map<String, String> putLowerCaseMapKeys(Map<String, String> map){
        Map<String, String> result =null;
        if(map!=null){
            result=new HashMap<>();
            for(String key : map.keySet()){
                result.put(key.toLowerCase(), map.get(key));
            }
        }
        return result;
    }

    public static boolean containsAValidPropertyByType(AgnosticElement webApplication,
                                                 Class<? extends AgnosticProperty> type) {
        AgnosticProperty property =
                findPropertyByType(webApplication, type);
        return property != null && property.isCompleted();
    }

    public static AgnosticProperty findPropertyByType(AgnosticElement agnosticElement,
                                                Class<? extends AgnosticProperty> type) {
        List<AgnosticProperty> properties = agnosticElement.getProperties();
        return findPropertyByType(properties, type);
    }

    public static AgnosticProperty findPropertyByType(List<AgnosticProperty> properties, Class<? extends AgnosticProperty> type) {
        AgnosticProperty result = null;
        if ((properties != null) && (!properties.isEmpty())) {
            for (AgnosticProperty property : properties) {
                if (type.isAssignableFrom(property.getClass())) {
                    return property;
                }
            }
        }
        return result;
    }
}
