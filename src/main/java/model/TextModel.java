package model;

public class TextModel {

    private static String inputText;
    private static String thesuffix;
    private static String withOrWithoutText;

    public static String getWithOrWithoutText() {
        return withOrWithoutText;
    }

    public static void setWithOrWithoutText(String withOrWithout) {
        withOrWithoutText = withOrWithout;
    }

    public static String getText() {
        return inputText;
    }

    public static void setText(String text) {
        inputText = text;
    }

    public static String getSuffix() {
        return thesuffix;
    }

    public static void setSuffix(String suffix) {
        thesuffix = suffix;
    }

}
