/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util.server;

public enum Version {
    SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, NONE;

    public static Version getVersion(String version) {
        String[] versionArray = version.split("-")[0].split("\\.");
        switch (versionArray[0] + "." + versionArray[1]) {
            case "1.7":
                return Version.SEVEN;
            case "1.8":
                return Version.EIGHT;
            case "1.9":
                return Version.NINE;
            case "1.10":
                return Version.TEN;
            case "1.11":
                return Version.ELEVEN;
            case "1.12":
                return Version.TWELVE;
            default:
                return Version.NONE;
        }
    }
}
