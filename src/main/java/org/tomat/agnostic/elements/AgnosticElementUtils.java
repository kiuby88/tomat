package org.tomat.agnostic.elements;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Jose on 09/10/14.
 */
public class AgnosticElementUtils {

    public static String findValueMapUsingCollection(Map<String, String> map, Collection<String> keysCollection) {
        String result = null;
        String key = aKeyContainedInMap(map, keysCollection);
        if (key != null) {
            return map.get(key);
        }
        return result;
    }

    private static String aKeyContainedInMap(Map<String, String> map, Collection<String> keysCollection) {
        String result = null;
        for (String key : keysCollection) {
            if (map.containsKey(key))
                return key;
        }
        return result;
    }

    public static AgnosticElement findAgnosticElementById(Collection<AgnosticElement> agnosticElements, String id) {
        AgnosticElement result = null;
        for (AgnosticElement agnosticElement : agnosticElements)
            if (agnosticElement.getId().equalsIgnoreCase(id))
                result = agnosticElement;
        return result;
    }
}
