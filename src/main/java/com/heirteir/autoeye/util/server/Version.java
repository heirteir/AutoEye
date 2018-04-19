/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
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
