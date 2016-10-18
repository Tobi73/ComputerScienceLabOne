import javafx.util.Pair;

import javax.swing.*;
import java.awt.event.*;

public class GUIForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner initialNumeralSystemSpinner;
    private JSpinner newNumeralSystemSpinner;
    private JTextArea resultArea;
    private JTextArea firstNumber;
    private JButton convertButton;
    private JTextArea logArea;
    private JTextArea secondNumber;
    private JButton additionButton;
    private JButton multiplicationButton;
    private JButton divisionButton;

    private Validator myValidator;
    private Converter myConverter;
    private Addition myAddition;
    private Subtraction mySubtraction;
    private Multiplication myMultiplication;
    private Division myDivision;

    public GUIForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        newNumeralSystemSpinner.setModel(new SpinnerNumberModel(2, 2, 16, 1));
        initialNumeralSystemSpinner.setModel(new SpinnerNumberModel(2, 2, 16, 1));

        myValidator = new Validator();
        myConverter = new Converter();
        myAddition = new Addition();
        mySubtraction = new Subtraction();
        myMultiplication = new Multiplication();
        myDivision = new Division();


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.setText("");
                resultArea.setText("");
                if (!myValidator.isInputValid(firstNumber.getText(), (Integer) initialNumeralSystemSpinner.getValue())) {
                    logArea.setText("Неверный формат числа");
                    return;
                }
                Long numInDecimal = myConverter.convertToDecimal(firstNumber.getText(), (Integer) initialNumeralSystemSpinner.getValue());
                resultArea.setText(myConverter.convertToNumericalSys(numInDecimal, (Integer) newNumeralSystemSpinner.getValue()));
            }
        });
        additionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.setText("");
                resultArea.setText("");
                if (!myValidator.isInputValid(firstNumber.getText(), (Integer) initialNumeralSystemSpinner.getValue())) {
                    logArea.setText("Неверный формат числа");
                    return;
                }
                if (!myValidator.isInputValid(secondNumber.getText(), (Integer) newNumeralSystemSpinner.getValue())) {
                    logArea.setText("Неверный формат числа");
                    return;
                }
                String sum = "";
                Long firstNumInDecimal = myConverter.convertToDecimal(firstNumber.getText(), (Integer) initialNumeralSystemSpinner.getValue());
                Long secondNumInDeciaml = myConverter.convertToDecimal(secondNumber.getText(), (Integer) newNumeralSystemSpinner.getValue());
                String firstBinaryNum = myConverter.convertToNumericalSys(firstNumInDecimal, 2);
                String secondBinaryNum = myConverter.convertToNumericalSys(secondNumInDeciaml, 2);
                if (firstNumInDecimal >= 0 && secondNumInDeciaml >= 0) {
                    sum = myAddition.runAdditionOperation(firstBinaryNum, secondBinaryNum);
                } else {
                    Boolean isFirstBigger;
                    if (isFirstBigger = (firstNumInDecimal < 0 && Math.abs(firstNumInDecimal) > Math.abs(secondNumInDeciaml)) || (secondNumInDeciaml < 0 && Math.abs(secondNumInDeciaml) > Math.abs(firstNumInDecimal))) {
                        sum = mySubtraction.runSubtractionOperation(firstBinaryNum, secondBinaryNum, isFirstBigger);
                    } else {
                        sum = mySubtraction.runSubtractionOperation(firstBinaryNum, secondBinaryNum, isFirstBigger);
                    }
                }
                resultArea.setText(sum);
            }
        });
        multiplicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.setText("");
                resultArea.setText("");
                if (!myValidator.isInputValid(firstNumber.getText(), (Integer) initialNumeralSystemSpinner.getValue())) {
                    logArea.setText("Неверный формат числа");
                    return;
                }
                if (!myValidator.isInputValid(secondNumber.getText(), (Integer) newNumeralSystemSpinner.getValue())) {
                    logArea.setText("Неверный формат числа");
                    return;
                }
                String multiplication = "";
                Long firstNumInDecimal = myConverter.convertToDecimal(firstNumber.getText(), (Integer) initialNumeralSystemSpinner.getValue());
                Long secondNumInDeciaml = myConverter.convertToDecimal(secondNumber.getText(), (Integer) newNumeralSystemSpinner.getValue());
                String firstBinaryNum = myConverter.convertToNumericalSys(firstNumInDecimal, 2);
                String secondBinaryNum = myConverter.convertToNumericalSys(secondNumInDeciaml, 2);
                multiplication = myMultiplication.runMultiplicationOperation(firstBinaryNum, secondBinaryNum);
                resultArea.setText(multiplication);
            }
        });
        divisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
                if (!myValidator.isInputValid(firstNumber.getText(), (Integer) initialNumeralSystemSpinner.getValue())) {
                    logArea.setText("Неверный формат числа");
                    return;
                }
                if (!myValidator.isInputValid(secondNumber.getText(), (Integer) newNumeralSystemSpinner.getValue())) {
                    logArea.setText("Неверный формат числа");
                    return;
                }
                Boolean isFirstBigger = true;
                Pair<String, String> division;
                Long firstNumInDecimal = myConverter.convertToDecimal(firstNumber.getText(), (Integer) initialNumeralSystemSpinner.getValue());
                Long secondNumInDeciaml = myConverter.convertToDecimal(secondNumber.getText(), (Integer) newNumeralSystemSpinner.getValue());
                if (Math.abs(firstNumInDecimal) < Math.abs(secondNumInDeciaml)) {
                    isFirstBigger = false;
                }
                String firstBinaryNum = myConverter.convertToNumericalSys(firstNumInDecimal, 2);
                String secondBinaryNum = myConverter.convertToNumericalSys(secondNumInDeciaml, 2);
                if (isFirstBigger) {
                    division = myDivision.runDivisionOperation(firstBinaryNum, secondBinaryNum);
                } else {
                    division = new Pair<>("0", firstBinaryNum);
                }
                resultArea.setText(division.getKey());
                logArea.setText(division.getValue());
            }
        });
    }

    public static void main(String[] args) {
        GUIForm dialog = new GUIForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
