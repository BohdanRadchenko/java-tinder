package org.tinder.utils;

import java.net.URISyntaxException;
import java.util.Objects;

public class ResourcesOps {

    private static String substringFirst(String string, boolean condition) {
        return condition && string.startsWith("/") ? string.substring(1) : string;
    }

    public static String dir(String directory) {
        directory = substringFirst(directory, !OsUtil.isWindows());
        try {
            String path = Objects.requireNonNull(ResourcesOps.class
                            .getClassLoader()
                            .getResource(directory))
                    .toURI()
                    .getPath();
            return substringFirst(path, OsUtil.isWindows());
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Requested path `%s`not found", directory), e);
        }
    }
}
