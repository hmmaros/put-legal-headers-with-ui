package controller;


import finals.Finals;
import finals.Texts;
import graphics.OutputFrame;
import logger.MyLogger;
import model.PathsModel;
import model.TextModel;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Functions {

    private Functions() {

    }

    private static Logger logger = MyLogger.myLogger();

    private static JFrame putLegalHeadersFrame = new JFrame();
    private static String directoryPath;
    private static String textToFind;
    private static String theComboboxValue;
    private static String suffix;

    public static void handleDirectories(JFrame mainframe) {
        List<String> foundedPaths = new ArrayList<>();

        putLegalHeadersFrame = mainframe;
        logger.log(Level.INFO, "start handle directories");

        directoryPath = PathsModel.getDirectoryPath();
        textToFind = TextModel.getText();
        theComboboxValue = TextModel.getWithOrWithoutText();
        suffix = TextModel.getSuffix();

        findFilesUsingCommandLine(foundedPaths);
        displayResultsOnNewFrame(foundedPaths);
    }

    private static void findFilesUsingCommandLine(List<String> foundedPaths) {
        String commandToFindFilesWithoutText = null;
        try {
            if (TextModel.getWithOrWithoutText().equals("WITHOUT")){
                commandToFindFilesWithoutText = "@for /r %f in (" + suffix + ") do @find /i \"" + textToFind + "\" \"%f\" > nul || echo %f";
            }
            else if (TextModel.getWithOrWithoutText().equals("WITH")) {
                commandToFindFilesWithoutText = "@for /r %f in (" + suffix + ") do @find /i \"" + textToFind + "\" \"%f\" > nul && echo %f";

            }

            executeCommand(commandToFindFilesWithoutText, directoryPath, foundedPaths);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand(String command, String path, List<String> foundedPaths) throws IOException {

        File filePath = new File(path);

        if (command == null){
            System.out.println("An error is occurred: command is null");
        }
        else {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("cmd.exe /K " + command, null, filePath);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            // read the output from the command
            // System.out.println("Here is the list of the files:\n");
            String si = null;
            while ((si = stdInput.readLine()) != null) {
                if (si.isEmpty()) {
                    break;
                }
                foundedPaths.add(si);
                // System.out.println(si);

            }
        }


    }

    private static void displayResultsOnNewFrame(List<String> pathsWithText) {
        new OutputFrame(pathsWithText).setVisible(true);
    }


    /*public static List<String> readTextFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(filePath);
        // read file into stream, try-with-resources
        *//*CharsetDecoder dec = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE).replaceWith("©");*//*

        // try (Stream<String> streamLines = Files.lines(Paths.get(filePath), Charset.forName("UTF-8"))){
        try (Reader r=Channels.newReader(FileChannel.open(path), dec, -1);
             BufferedReader streamLines = new BufferedReader(r)){

         // lines = streamLines.collect(Collectors.toList());
            lines = streamLines.lines()
                    .collect(Collectors.toList());

        } catch (java.io.UncheckedIOException me) {
            me.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }*/

    public static List<String> readTextFromFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try(BufferedReader in = new BufferedReader( new InputStreamReader( new FileInputStream(filePath), "UTF-8"))){
            String str;

            while ((str = in.readLine()) != null) {
                // System.out.println(str);
                lines.add(str);
            }

            in.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return lines;
    }

    private static void writeToFileAndAppendText(List<String> lines, String pathNameOfTheFile) throws IOException {

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(pathNameOfTheFile, true), "UTF-8"));
        try {
            for (String line:lines) {
                out.write(line + "\n");
            }

        } finally {
            out.close();
        }
    }

    public static void createNewFileWithNewTextAtTopAndOldBelow(String pathOfOldFile, List<String> inputTextLines) {

        List<String> lines = readTextFromFile(pathOfOldFile);

        try {

            writeToFileAndAppendText(lines, Finals.TEMP_FILE_NAME);
            deleteFileContent(pathOfOldFile);
            appendComments(pathOfOldFile, Finals.HTML_START_COMMENTS, Finals.COMMON_START_COMMENTS, Finals.ADOC_START_COMMENTS);
            writeToFileAndAppendText(inputTextLines, pathOfOldFile);
            appendComments(pathOfOldFile, Finals.HTML_END_COMMENTS, Finals.COMMON_END_COMMENTS, Finals.ADOC_END_COMMENTS);
            writeToFileAndAppendText(lines, pathOfOldFile);
            deleteFileContent(Finals.TEMP_FILE_NAME);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(putLegalHeadersFrame, "Cannot add text in all files", Texts.WARNING_TITLE, JOptionPane.INFORMATION_MESSAGE);
                e.printStackTrace();
            }
    }

    private static void appendComments(String pathOfOldFile, List<String> htmlComments, List<String> commonComments, List<String> adocComments) throws IOException {
        if (pathOfOldFile.endsWith(Finals.HTML_SUFFIX))
        {
            writeToFileAndAppendText(htmlComments, pathOfOldFile);
        }
        else if (pathOfOldFile.endsWith(Finals.ADOC_SUFFIX))
        {
            writeToFileAndAppendText(adocComments, pathOfOldFile);
        }
        else
        {
            writeToFileAndAppendText(commonComments, pathOfOldFile);
        }
    }

    private static void deleteFileContent(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
    }

}
