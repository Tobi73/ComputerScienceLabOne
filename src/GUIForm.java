import javax.swing.*;
import java.awt.event.*;

public class GUIForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner initialNumeralSystemSpinner;
    private JSpinner newNumeralSystemSpinner;
    private JTextArea resultArea;
    private JTextArea numberToConvertArea;
    private JButton convertButton;
    private JTextArea logArea;

    private Validator myValidator;
    private Converter myConverter;

    public GUIForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        newNumeralSystemSpinner.setModel(new SpinnerNumberModel(2, 2, 16, 1));
        initialNumeralSystemSpinner.setModel(new SpinnerNumberModel(2, 2, 16, 1));

        myValidator = new Validator();
        myConverter = new Converter();


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
                if(!myValidator.isInputValid(numberToConvertArea.getText(), (Integer)initialNumeralSystemSpinner.getValue())) {
                    logArea.setText("Неверный формат числа");
                    return;
                }
                Integer numInDecimal = myConverter.ConvertToDecimal(numberToConvertArea.getText(), (Integer) initialNumeralSystemSpinner.getValue());
                resultArea.setText(myConverter.ConvertToNumericalSys(numInDecimal, (Integer) newNumeralSystemSpinner.getValue()));
            }
        });
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        GUIForm dialog = new GUIForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
