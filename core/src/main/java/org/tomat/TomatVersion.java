package org.tomat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

/**
 * Created by Jose on 31/10/14.
 */
public class TomatVersion {

    private final String versionFromStatic = "1.0-SNAPSHOT";
    private final String version;

    public TomatVersion(){
        version=versionFromStatic;
    }

    public String getVersion() {
        return version;
    }
}
