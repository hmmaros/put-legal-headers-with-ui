package graphics;

import finals.Finals;
import finals.Texts;
import logger.MyLogger;
import model.PathsModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileChooser extends JFrame {



    private static Logger logger = MyLogger.myLogger();

    private JPanel mainpanel;
    private String targetToFind;
    private String directoryOrFileToSelect;
    private JTextField textDirectory;

    private static JTextField txtPath = new JTextField();
    private static JFileChooser browseFiles = new JFileChooser();


    public FileChooser(String  targetToFind, JPanel mainpanel, String directoryOrFileToSelect) {

        this.mainpanel = mainpanel;
        this.targetToFind = targetToFind;
        this.directoryOrFileToSelect = directoryOrFileToSelect;

        createTextField();
        createButtons();

    }

    private void createButtons() {
        JButton findFunctionButton = new JButton();
        findFunctionButton.setPreferredSize(new Dimension(Finals.FILE_CHOOSER_BUTTON_WIDTH, Finals.FILE_CHOOSER_BUTTON_HEIGHT));
        findFunctionButton.setText(Texts.BROWSE_BUTTON);

        mainpanel.add( findFunctionButton );

        logger.log(Level.INFO, "Browse button created");

        findFunctionButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chooseFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                logger.log(Level.INFO, "Browse button action ended");
            }

        });
    }

    private void createTextField() {
        JTextField selectedFileDirectory = new JTextField(targetToFind);
        selectedFileDirectory.setColumns(Finals.FILE_CHOOSER_COLUMS_OF_TEXT_FIELD);
        mainpanel.add(selectedFileDirectory);
        this.textDirectory = selectedFileDirectory;
        logger.log(Level.INFO, "Text field created");

    }

    private void chooseFile() throws IOException {

        if ( directoryOrFileToSelect.equals(Finals.DIRECTORIES_ONLY) )
        {
            // For Directory
            browseFiles.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        else if ( directoryOrFileToSelect.equals(Finals.FILES_ONLY) )
        {
            // For File
            browseFiles.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }


        browseFiles.setAcceptAllFileFilterUsed(false);

        int rVal = browseFiles.showOpenDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION)
        {
            String selectedFile =  browseFiles.getSelectedFile().toString();
            setPathsToPanels(selectedFile);
            PathsModel.setDirectoryPath(selectedFile);
        }

    }

    private void setPathsToPanels(String selectedFile) {
        txtPath.setText(selectedFile);
        textDirectory.setText(selectedFile);
        logger.log(Level.INFO,  "Selected files path: {0}", selectedFile);
    }

}
