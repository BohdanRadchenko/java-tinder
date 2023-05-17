package org.tinder.utils;

import java.util.Locale;

public class OsUtil {
    public enum OSType {
        WIN, MAC, LINUX, OTHER
    }

    private static String os = null;
    protected static OSType detectedOS;

    public static void osInit() {
        if (os == null) {
            os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((os.contains("mac")) || (os.contains("darwin"))) {
                detectedOS = OSType.MAC;
            } else if (os.contains("win")) {
                detectedOS = OSType.WIN;
            } else if (os.contains("nux")) {
                detectedOS = OSType.LINUX;
            } else {
                detectedOS = OSType.OTHER;
            }
        }
    }

    public static boolean isWindows() {
        return detectedOS == OSType.WIN;
    }

    public static boolean isMac() {
        return detectedOS == OSType.MAC;
    }
}
