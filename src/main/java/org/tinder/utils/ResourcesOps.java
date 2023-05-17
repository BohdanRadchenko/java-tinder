package org.tinder.utils;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class ResourcesOps {

    // TODO: 16.05.2023 назвал бы метод ниже getResourceDirectory 
    public static String dir(String directory) {
//        // TODO: 17.05.2023 отладочній код, потом удалить
//        String resourcePath = directory;
//        System.out.println("directory: "+directory);
//        URL resourceURL = ResourcesOps.class.getClassLoader().getResource(resourcePath);
//        System.out.println("Resource Path: " + resourceURL.getPath());
//        String newPath = resourceURL.getPath().substring(1);
//        System.out.println("newPath -" +newPath);
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
