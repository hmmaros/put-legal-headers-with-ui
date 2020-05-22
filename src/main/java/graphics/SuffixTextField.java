package graphics;

import logger.MyLogger;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuffixTextField extends JTextField {

    public JPanel inputPanel = new JPanel();
    public static JTextField inputText;
    public static JLabel label;
    private static Logger logger = MyLogger.myLogger();

    public SuffixTextField(JPanel panel, int dimensions, String labeltext) {

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
