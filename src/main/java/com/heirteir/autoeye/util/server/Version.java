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
