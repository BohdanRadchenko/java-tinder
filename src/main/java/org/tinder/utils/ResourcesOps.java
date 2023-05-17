package org.tinder.utils;

import java.net.URISyntaxException;
import java.util.Objects;

public class ResourcesOps {

    private static String dirToOsType(String directory) {
        if (OsUtil.isWindows()) return directory;
        return directory.startsWith("/") ? directory.substring(1) : directory;
    }

    private static String pathToOsType(String path) {
        if (!OsUtil.isWindows()) return path;
        return path.startsWith("/") ? path.substring(1) : path;
    }

    public static String dir(String directory) {
        directory = dirToOsType(directory);
        try {
            String path = Objects.requireNonNull(ResourcesOps.class
                            .getClassLoader()
                            .getResource(directory))
                    .toURI()
                    .getPath();
            return pathToOsType(path);
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Requested path `%s`not found", directory), e);
        }
    }
}
