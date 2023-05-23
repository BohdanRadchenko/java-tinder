package org.tinder.utils;

import org.tinder.enums.ServletPath;

public class QueryString {
    public static String getFirstPathClean(String path) {
        if(path == null) return null;
        return path.replaceAll("^\\/", "").split("\\/")[0];
    }
    public static String getFirstPath(String path) {
        if(path == null) return null;
        String firstPathClean = getFirstPathClean(path);
        if(firstPathClean == null || firstPathClean.isBlank()) return firstPathClean;
        return String.format("/%s", firstPathClean);
    }

    public static boolean isStaticPath(String path) {
        if(path == null || path.isBlank()) return true;
        String startPath = getFirstPath(path);
        return startPath.equals(ServletPath.STATIC.path());
    }
}
