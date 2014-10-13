package org.tomat.tosca.parsers;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Jose on 09/10/14.
 */
public class NodeTemplateParserUtils {

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
}
