package graphics;

import finals.Finals;
import finals.Texts;
import logger.MyLogger;
import model.PathsModel;
import model.TextModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputTextField extends JTextField {

    public JPanel inputPanel = new JPanel();
    public static JTextField inputText;
    public static JLabel label;
    private static Logger logger = MyLogger.myLogger();

    public InputTextField(JPanel panel, int dimensions, String labeltext) {

        inputText = new JTextField(dimensions);
        label  = new JLabel(labeltext);
        this.inputPanel = panel;
        createLabel();
        createTextField();

    }

    private void createLabel() {
        inputPanel.add(label);
    }

    private void createTextField() {
        inputPanel.add(inputText);
        logger.log(Level.INFO, "Text field created");
    }

}
