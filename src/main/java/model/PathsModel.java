package model;

public class PathsModel {

    private static String directoryPath;

    public static String getDirectoryPath() {
        return directoryPath;
    }

    public static void setDirectoryPath(String tomcatpath) {
        directoryPath = tomcatpath;
    }

}
