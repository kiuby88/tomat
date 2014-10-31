package org.tomat;

/**
 * Created by Jose on 31/10/14.
 */
public class ResourcePathResolver {

    public String getPathOfFile(String file){
        return getClass().getClassLoader().getResource(file).getFile();
    }
}
