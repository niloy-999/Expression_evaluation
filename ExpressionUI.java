import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;

import javax.swing.*;

public class ExpressionUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
	// components
    private JLabel lblExpression;
    private JLabel lblResult;
    private JLabel lblMain;
    private JLabel exp;

    private JButton btnExit;
    private JButton btnCalculate; 

    private JTextField txtExpression;
    private JTextField txtResult; 
    private JTextField expres; 

    // evaluator object
    private ExpressionEvaluator evaluator;

    // Setup UI
    public ExpressionUI() {

        this.evaluator = new ExpressionEvaluator();

        // frame features
        this.setTitle("Infix Expression Evaluator");
        this.setSize(6000, 320);
        this.setPreferredSize(new Dimension(600, 320));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        this.setLocation(300, 300);
        //this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Create Components
        Font mainFont = new Font("Courier New", Font.BOLD, 20);
        Font font = new Font("Courier New", Font.BOLD, 20);

        // Create main label
        this.lblMain = new JLabel("Infix Expression Evaluator", JLabel.CENTER);
        this.lblMain.setFont(mainFont);
        this.lblMain.setPreferredSize(new Dimension(500, 50));
        this.add(this.lblMain);

        // input field
        this.lblExpression = new JLabel("Expression:");
        this.lblExpression.setPreferredSize(new Dimension(150, 40));
        this.lblExpression.setFont(font);
        this.add(this.lblExpression);

        this.txtExpression = new JTextField();
        this.txtExpression.setFont(font);
        this.txtExpression.setPreferredSize(new Dimension(400, 40));
        this.add(txtExpression);
        //this.txtResult.requestFocus();

        
        this.exp = new JLabel("Postfix:");
        this.exp.setPreferredSize(new Dimension(150, 40));
        this.exp.setFont(font);
        this.add(this.exp);

        this.expres = new JTextField();
        this.expres.setFont(font);
        this.expres.setPreferredSize(new Dimension(400, 40));
        this.expres.setEditable(false);
        this.expres.setBackground(Color.LIGHT_GRAY);
        this.add(this.expres);
        
        
        
        // output
        this.lblResult = new JLabel("Result:");
        this.lblResult.setPreferredSize(new Dimension(150, 40));
        this.lblResult.setFont(font);
        this.add(this.lblResult);

        this.txtResult = new JTextField();
        this.txtResult.setFont(font);
        this.txtResult.setPreferredSize(new Dimension(400, 40));
        this.txtResult.setEditable(false);
        this.txtResult.setBackground(Color.lightGray);
        this.add(this.txtResult);

        // Buttons
        this.btnCalculate = new JButton("Evaluate");
        this.btnCalculate.setPreferredSize(new Dimension(200, 40));
        this.btnCalculate.setFont(font);
        this.btnCalculate.addActionListener(this);
        this.btnCalculate.setBackground(Color.GREEN);
        this.add(this.btnCalculate);

        this.btnExit = new JButton("Exit");
        this.btnExit.setPreferredSize(new Dimension(200, 40));
        this.btnExit.setFont(font);
        this.btnExit.addActionListener(this);
        this.btnExit.setBackground(Color.RED);
        this.add(this.btnExit);

        //pack
        this.pack();
    }

    // error message
    private void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Handle Click Events
    public void actionPerformed(ActionEvent event) {
        Object src = event.getSource();

        if(src.equals(this.btnCalculate)) {
            String expression = this.txtExpression.getText();

            try {
                // check if not 
                if(expression == null || expression.isEmpty()) {
                    throw new ExpressionException("Invalid Expression");
                }

                double result = evaluator.evaluate(expression);
                 
                txtResult.setText(String.valueOf(result));
                expres.setText(evaluator.postfix);
                
                

            } catch(ExpressionException ee) {
                displayError(ee.getMessage());
            }
        } else {
            System.exit(0);
        }
    }

    // main method
    public static void main(String [] args) {
        new ExpressionUI();
    }
}
