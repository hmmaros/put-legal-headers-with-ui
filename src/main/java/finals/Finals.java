package finals;

import java.util.Arrays;
import java.util.List;

public class Finals {

    private Finals() {
        // boom
    }

    public final static String CREATOR = "DC";
    public final static String APPNAME = "PutLegalHeaders";
    public final static String EDITION = "v0.1";

    public final static String INPUT_FILE_NAME = "inputText.txt";
    public final static String TEMP_FILE_NAME = "tempFile.txt";

    public final static String DIRECTORIES_ONLY = "DIRECTORIES_ONLY";
    public final static String FILES_ONLY = "FILES_ONLY";

    public final static String HTML_SUFFIX = ".html";
    public final static String ADOC_SUFFIX = ".adoc";

    public final static int FILE_CHOOSER_COLUMS_OF_TEXT_FIELD = 40;
    public final static int FILE_CHOOSER_BUTTON_WIDTH = 80;
    public final static int FILE_CHOOSER_BUTTON_HEIGHT = 25;

    public final static int DISTANCE_BETWEEN_BOTTOM_BUTTONS_MAIN_FRAME = 60;
    public final static int MAIN_FRAME_WIDTH = 600;
    public final static int MAIN_FRAME_HEIGHT = 250;

    public final static List<String> COMMON_START_COMMENTS = Arrays.asList("/*");
    public final static List<String> COMMON_END_COMMENTS = Arrays.asList("*/");

    public final static List<String> HTML_START_COMMENTS = Arrays.asList("<!--");
    public final static List<String> HTML_END_COMMENTS = Arrays.asList("-->");

    public final static List<String> ADOC_START_COMMENTS = Arrays.asList("////");
    public final static List<String> ADOC_END_COMMENTS = Arrays.asList("////");

}
