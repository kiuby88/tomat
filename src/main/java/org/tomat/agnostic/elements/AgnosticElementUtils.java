package org.tomat.agnostic.elements;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

        for (String key : keysCollection) {
            if (map.containsKey(key.toLowerCase())){
                return key;
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
}
