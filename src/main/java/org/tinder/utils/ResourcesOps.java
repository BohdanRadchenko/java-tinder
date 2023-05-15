package org.tinder.utils;

import java.net.URISyntaxException;
import java.util.Objects;

public class ResourcesOps {
    public static String dir(String directory) {
        try {
            return Objects.requireNonNull(ResourcesOps.class
                            .getClassLoader()
                            .getResource(directory))
                    .toURI()
                    .getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Requested path `%s`not found", directory), e);
        }
    }
}
