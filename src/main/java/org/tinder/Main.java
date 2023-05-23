package org.tinder;

import org.tinder.utils.OsUtil;

public class Main {
    private static void init() {
        OsUtil.osInit();
    }

    public static void main(String[] args) {
        init();

        TinderApplication app = new TinderApplication();
        app.run();
    }
}
