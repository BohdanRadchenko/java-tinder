package org.tinder.utils;

import java.net.URISyntaxException;
import java.util.Objects;

public class ResourcesOps {
    public static String dir(String directory) {
        try {
            String path = Objects.requireNonNull(ResourcesOps.class
                            .getClassLoader()
                            .getResource(directory))
                    .toURI()
                    .getPath();
            if (path.startsWith("/")) path = path.substring(1);
            return path;
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Requested path `%s`not found", directory), e);
        }
    }
}
