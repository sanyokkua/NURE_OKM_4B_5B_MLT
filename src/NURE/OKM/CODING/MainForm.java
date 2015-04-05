package NURE.OKM.CODING;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexander on 04.04.2015.
 */
public class MainForm extends JFrame {
    private JTextField textFieldOriginalCode;
    private JTextField textField4B5NResult;
    private JTextField textFieldMLT3Result;
    private JLabel labelOriginalCode;
    private JLabel label4B5BResult;
    private JLabel labelMLT3Result;
    private JPanel jPanel4B5BResultGraph;
    private JPanel jPanelMLT3ResultGraph;
    private JPanel jPanelMain;
    private JButton buttonDoConversion;
    private JTextField textFieldOriginalBinarySequence;
    private JLabel labelOriginalBinary;
    private JLabel label4B5BGraph;
    private JLabel labelMLT3Graph;

    private Encoder encoder;
    private EncoderDrawer encoderDrawer;

    public MainForm() {
        setMinimumSize(jPanelMain.getMinimumSize());
        setContentPane(jPanelMain);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonDoConversion.setEnabled(false);
        setVisible(true);
        setEventHandlers();
    }

    private void setEventHandlers() {
        textFieldOriginalCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (checkCorrectData()) {
                    buttonDoConversion.setEnabled(true);
                } else {
                    buttonDoConversion.setEnabled(false);
                }
            }
        });
        buttonDoConversion.addActionListener((e) -> convertData());

    }

    private boolean checkCorrectData() {
        String temp = textFieldOriginalCode.getText();
        Pattern pattern = Pattern.compile("((\\d+)|(\\d+\\.))+");
        Matcher matcher = pattern.matcher(temp);
        return matcher.matches();
    }

    private void convertData() {
        String[] numbersInText = textFieldOriginalCode.getText().split("\\.");
        ArrayList<Integer> listOfNumbers = new ArrayList<>();
        for (String number : numbersInText) {
            try {
                Integer byteNumber = Integer.parseInt(number);
                listOfNumbers.add(byteNumber);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                return;
            }
        }
        doConversion(listOfNumbers);
    }

    private void doConversion(ArrayList<Integer> listOfNumbers) {
        encoder = new Encoder(listOfNumbers.toArray(new Integer[listOfNumbers.size()]));
        encoderDrawer = new EncoderDrawer(encoder);
        String res4B5B = Arrays.toString(encoder.get4Bto5BArray());
        String resMLT3 = Arrays.toString(encoder.getMLT3Array());
        textFieldOriginalBinarySequence.setText(Arrays.toString(encoder.getOriginBinarySequence()));
        textField4B5NResult.setText(res4B5B);
        textFieldMLT3Result.setText(resMLT3);
        draw4B5B();
        drawMLT3();
    }

    private void draw4B5B() {
        Graphics2D graphics2D = (Graphics2D) jPanel4B5BResultGraph.getGraphics();
        Dimension size = jPanel4B5BResultGraph.getSize();
        encoderDrawer.draw4B5B(graphics2D, size);
    }

    private void drawMLT3() {
        Graphics2D graphics2D = (Graphics2D) jPanelMLT3ResultGraph.getGraphics();
        Dimension size = jPanelMLT3ResultGraph.getSize();
        encoderDrawer.drawMLT3(graphics2D, size);
    }
}
