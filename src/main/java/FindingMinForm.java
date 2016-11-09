import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FindingMinForm extends JDialog {
    QuineMcCluskeyMethod method;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField inputArea;
    private JTextField resultArea;
    private JButton CNFButton;
    private JButton generateVector;
    private JButton DNFButton;
    private JPanel drawingPanel;
    private List<StringBuilder> minFormToDraw = null;
    private BinaryFunctionsDrawing drawing = new BinaryFunctionsDrawing();
    private Boolean isDNF = false;

    public FindingMinForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        method = new QuineMcCluskeyMethod();

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
        generateVector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputArea.setText(BinaryVectorGenerator.generateFourVarVector());
            }
        });
        DNFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vector = inputArea.getText();
                if (isInputValid(vector) == false) {
                    resultArea.setText("Неверный формат");
                    return;
                }
                List<StringBuilder> vectors = method.getAbsoluteDNF(vector);
                isDNF = true;
                //resultArea.setText(method.formDMF(method.findMinForm(vectors, method.findImplicants(vectors))));
                minFormToDraw = method.findMinForm(vectors, method.findImplicants(vectors));
                resultArea.setText(method.formDMF(minFormToDraw));
                drawingPanel.repaint();
            }
        });
        CNFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vector = inputArea.getText();
                if (isInputValid(vector) == false) {
                    resultArea.setText("Неверный формат");
                    return;
                }
                List<StringBuilder> vectors = method.getAbsoluteCNF(vector);
                isDNF = false;
                minFormToDraw = method.findMinForm(vectors, method.findImplicants(vectors));
                //resultArea.setText(method.formCMF(method.findMinForm(vectors, method.findImplicants(vectors))));
                resultArea.setText(method.formCMF(minFormToDraw));
                drawingPanel.repaint();
            }
        });
    }

    public static void main(String[] args) {
        FindingMinForm dialog = new FindingMinForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public Boolean isInputValid(String vector) {
        StringBuilder vectorCheck = new StringBuilder(vector);
        if (vector.isEmpty()) {
            return false;
        }
        if (vector.length() != 16) {
            return false;
        }
        for (int i = 0; i < vectorCheck.length(); i++) {
            if (vectorCheck.charAt(i) != '0' && vectorCheck.charAt(i) != '1') {
                return false;
            }
        }
        return true;
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
        drawingPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                /*
                QuineMcCluskeyMethod testMethod = new QuineMcCluskeyMethod();
                List<StringBuilder> vectors = method.getAbsoluteCNF(BinaryVectorGenerator.generateFourVarVector());
                List<StringBuilder> testList = testMethod.findMinForm(vectors, method.findImplicants(vectors));
                for(StringBuilder vector : testList){
                    System.out.println(vector.toString());
                }
                System.out.println(testMethod.formCMF(testList));
                BinaryFunctionsDrawing test = new BinaryFunctionsDrawing();
                */
                if (minFormToDraw != null) {
                    drawing = new BinaryFunctionsDrawing();
                    drawing.drawFunction(g, minFormToDraw, this.getWidth(), this.getHeight(), isDNF);
                }
            }
        };
    }
}
