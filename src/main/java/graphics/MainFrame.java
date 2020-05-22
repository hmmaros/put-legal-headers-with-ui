package graphics;

import controller.Functions;
import finals.Finals;
import finals.Texts;
import logger.MyLogger;
import model.TextModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame {

    private static Logger logger = MyLogger.myLogger();

    public static final JFrame mainframe = new JFrame(Finals.APPNAME);
    private JPanel stopStartButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    public static JButton startButton = new JButton("Search");
    public final static JPanel mainPanel = new JPanel();
    public static JComboBox comboBox = new JComboBox();


    public MainFrame() {
        JLabel copyright = new JLabel("\u00a9 " + Finals.CREATOR + " " + Finals.EDITION);
        mainframe.add(copyright, BorderLayout.SOUTH);
        mainframe.setPreferredSize(new Dimension(Finals.MAIN_FRAME_WIDTH, Finals.MAIN_FRAME_HEIGHT));
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.pack();
        mainframe.setLocationRelativeTo(null);

        createFindDirectoryFunction();
        createInputTextField();
        createSuffixInputTextField();
        createComboBoxes();
        createStartButton();

        logger.log(Level.INFO, "Main frame created");

        mainframe.getContentPane().add( mainPanel );
        mainframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainframe.setVisible(true);

        mainframe.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                showExitConfirmDialog("The app will close. Are you sure?", "Really Closing?", true);
            }
        });

        // create a file with name inputText.txt and place it in the root folder
        createInputTxtFile();
        createTemporaryTxtFile();
    }

    private void createTemporaryTxtFile() {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(Finals.TEMP_FILE_NAME), Charset.forName("UTF-8"))) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createInputTxtFile() {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(Finals.INPUT_FILE_NAME), Charset.forName("UTF-8"))) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createComboBoxes() {
        JLabel comboboxlabel1 = new JLabel("Find the files ");
        mainPanel.add(comboboxlabel1);

        comboBox.addItem("WITHOUT");
        comboBox.addItem("WITH");

        mainPanel.add(comboBox);
        JLabel comboboxlabel2 = new JLabel(" the given text");
        comboboxlabel2.setPreferredSize(new Dimension(300, 20));
        mainPanel.add(comboboxlabel2);
    }

    private void createFindDirectoryFunction(){

        new FileChooser("Folder directory", mainPanel, Finals.DIRECTORIES_ONLY);
        logger.log(Level.INFO,  "Folder path selected");
    }

    private void createInputTextField() {
        new InputTextField(mainPanel, 30, "Give the text you want to find: ");
    }
    private void createSuffixInputTextField() {
        new SuffixTextField(mainPanel, 20, "Give the suffix-es (separate them with semicolon): ");
    }


    private void createStartButton() {

        stopStartButtonsPanel.add(Box.createHorizontalStrut(Finals.DISTANCE_BETWEEN_BOTTOM_BUTTONS_MAIN_FRAME));

        // Add button to JPanel
        stopStartButtonsPanel.add(BorderLayout.EAST, startButton);

        // And JPanel needs to be added to the JFrame itself!
        mainframe.getContentPane().add(BorderLayout.SOUTH, stopStartButtonsPanel);

        startButton.setEnabled(true);

        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    try {
                        String text = "";
                        text = InputTextField.inputText.getText();
                        TextModel.setText(text);

                        String suffix = "";
                        suffix = SuffixTextField.inputText.getText();
                        TextModel.setSuffix(suffix);
                        if (suffix == null || suffix.isEmpty() || suffix.equals("") || suffix.equals(" ")){
                            throw new NullPointerException();
                        }
                        else if (!suffix.contains("*.")){
                            JOptionPane.showMessageDialog(mainframe, "Right way to give suffix is: *.ts;*.html", Texts.WARNING_TITLE, JOptionPane.INFORMATION_MESSAGE);
                            throw new NullPointerException();
                        }

                        String comboboxValue = String.valueOf(comboBox.getSelectedItem());
                        TextModel.setWithOrWithoutText(comboboxValue);

                        Functions.handleDirectories(mainframe);
                        //startButton.setEnabled(false);


                    } catch (NullPointerException err) {
                        logger.log(Level.WARNING, "ERROR: One or more fields are not completed " + err);
                        JOptionPane.showMessageDialog(mainframe, "Please fill in all fields!", Texts.WARNING_TITLE, JOptionPane.INFORMATION_MESSAGE);

                    }

            }
        });
        logger.log(Level.INFO, "start button created");

    }

    private void showExitConfirmDialog(String message, String title, boolean exit) {
        if (JOptionPane.showConfirmDialog(mainframe,
                message, title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

            if (exit){
                System.exit(0);
            }
        }
    }

}
