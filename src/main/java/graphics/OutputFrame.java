package graphics;

import controller.Functions;
import finals.Finals;
import finals.Texts;
import logger.MyLogger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;

public class OutputFrame extends JFrame {
    /**
     * The text area which is used for displaying logging information.
     */
    private static Logger logger = MyLogger.myLogger();

    private JTextArea textArea;

    private JButton buttonAddText = new JButton("Add Text to Files");
    private JButton buttonGiveText = new JButton("Give Text");
    private JButton buttonClear = new JButton("Clear");

    private PrintStream standardOut;

    public OutputFrame(List<String> foundedPaths) {
        super("Resulting files");

        configureFrame();

        System.out.println("The list of the founded files:\n");
        if (foundedPaths.size() != 0){
            printLog(foundedPaths);
            System.out.println("\n" + foundedPaths.size() + " files founded!!!");
        }
        else {
            System.out.println("No files are founded!!!");
        }

        // adds event handler for button Start
        buttonAddText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("\nAdding text process started . . .");

                List<String> inputTextLines = Functions.readTextFromFile(Finals.INPUT_FILE_NAME);

                if (inputTextLines.isEmpty()){
                    System.out.println("The input file is empty or there is an invalid character in this file!");
                }
                else {
                    for ( String path:foundedPaths ) {
                        Functions.createNewFileWithNewTextAtTopAndOldBelow(path, inputTextLines);
                        System.out.println("Done: " + path);
                    }
                    JOptionPane.showMessageDialog(OutputFrame.super.rootPane, "Done! Text added in all founded files!", Texts.SUCCESS, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // adds event handler for button Start
        buttonGiveText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                if (Desktop.isDesktopSupported()) {
                    try {
                        JOptionPane.showMessageDialog(OutputFrame.super.rootPane, "Be sure you are using notepad++ and the encoding is utf-8\n If your default .txt app is not notepad++, open the file from the app root folder with notepad++!\n Don't forget to save!!!", Texts.WARNING_TITLE, JOptionPane.INFORMATION_MESSAGE);
                        // String path = new File(Finals.INPUT_FILE_NAME).getAbsolutePath();
                        // Desktop.getDesktop().edit(new File(path));
                        Runtime.getRuntime().exec("cmd.exe /K start " + Finals.INPUT_FILE_NAME);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.warning("Desktop not supported");
                }
            }
        });

        // adds event handler for button Clear
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // clears the text area
                try {
                    textArea.getDocument().remove(0,
                            textArea.getDocument().getLength());
                    standardOut.println("Text area cleared");
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);
        setLocationRelativeTo(null);    // centers on screen
    }

    private void configureFrame() {
        textArea = new JTextArea(50, 10);
        textArea.setEditable(false);
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(textArea));

        // keeps reference of standard output stream
        standardOut = System.out;

        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);

        // creates the GUI
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        add(buttonAddText, constraints);

        constraints.gridx = 1;
        add(buttonGiveText, constraints);

        constraints.gridx = 2;
        add(buttonClear, constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        add(new JScrollPane(textArea), constraints);
    }

    /**
     * Prints log statements for testing in a thread
     */
    private void printLog(List<String> foundedPaths) {
        for (int i=0;i<foundedPaths.size();i++) {
            System.out.println(foundedPaths.get(i));
        }
    }
}