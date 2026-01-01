import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUICalculator extends JFrame implements ActionListener {

    JTextField display;
    double num1, num2, result;
    char operator;

    public GUICalculator() {

        setTitle("Java GUI Calculator");
        setSize(350, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "√", "x²", "Exit"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        try {
            if (cmd.charAt(0) >= '0' && cmd.charAt(0) <= '9' || cmd.equals(".")) {
                display.setText(display.getText() + cmd);
            }

            else if (cmd.equals("C")) {
                display.setText("");
            }

            else if (cmd.equals("Exit")) {
                System.exit(0);
            }

            else if (cmd.equals("√")) {
                double value = Double.parseDouble(display.getText());
                display.setText(String.valueOf(Math.sqrt(value)));
            }

            else if (cmd.equals("x²")) {
                double value = Double.parseDouble(display.getText());
                display.setText(String.valueOf(value * value));
            }

            else if (cmd.equals("=")) {
                num2 = Double.parseDouble(display.getText());

                switch (operator) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '/':
                        if (num2 == 0) {
                            JOptionPane.showMessageDialog(this, "Cannot divide by zero");
                            return;
                        }
                        result = num1 / num2;
                        break;
                }
                display.setText(String.valueOf(result));
            }

            else {
                num1 = Double.parseDouble(display.getText());
                operator = cmd.charAt(0);
                display.setText("");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }

    public static void main(String[] args) {
        new GUICalculator();
    }
}
