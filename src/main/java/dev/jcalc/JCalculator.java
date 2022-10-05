package dev.jcalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class JCalculator extends JFrame implements ActionListener {


    private JTextField textField;

    private static final Map<String, JButton> buttonMap = new LinkedHashMap<>();

    static {
        buttonMap.putIfAbsent("add", new JButton("+"));
        buttonMap.putIfAbsent("one", new JButton("1"));
        buttonMap.putIfAbsent("two", new JButton("2"));
        buttonMap.putIfAbsent("three", new JButton("3"));
        buttonMap.putIfAbsent("subtract", new JButton("-"));
        buttonMap.putIfAbsent("four", new JButton("4"));
        buttonMap.putIfAbsent("five", new JButton("5"));
        buttonMap.putIfAbsent("six", new JButton("6"));
        buttonMap.putIfAbsent("astra", new JButton("*"));
        buttonMap.putIfAbsent("seven", new JButton("7"));
        buttonMap.putIfAbsent("eight", new JButton("8"));
        buttonMap.putIfAbsent("nine", new JButton("9"));
        buttonMap.putIfAbsent("divide", new JButton("/"));
        buttonMap.putIfAbsent("decimal", new JButton("."));
        buttonMap.putIfAbsent("zero", new JButton("0"));
        buttonMap.putIfAbsent("clear", new JButton("C"));
        buttonMap.putIfAbsent("equal", new JButton("="));
    }
    private String x0, x1, x2;

    public JCalculator() {
        x0 = x1 = x2 = "";
        setTitle("JCalculator");

        setSize(new Dimension(250, 305));
        setResizable(false);

        //Creating the text field for the calculator
        textField = new JTextField(16);
        textField.setEditable(false);



        //Define the j panel
        JPanel panel = new JPanel();
        //Add the text field to the panel
        panel.add(textField);
        Font btnFont = new Font("Times New Roman", Font.BOLD, 20);

        //Add the buttons to the panel in order
        buttonMap.values().forEach(button -> {
            button.setFont(btnFont);
            button.addActionListener(this);
            panel.add(button);
        });

        panel.setBackground(Color.BLUE);
        getContentPane().add(panel);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.charAt(0) == '.') {
            if (!x1.equals("")) {
                x2 = x2 + command;
            } else {
                x0 = x0 + command;
            }
        } else if (command.charAt(0) == 'C') {
            x0 = x1 = x2 = "";
            textField.setText(x0 + x1 + x2);
        } else if (command.charAt(0) == '=') {
            AtomicReference<Double> operation = new AtomicReference<>();
            switch (x1) {
                case "+" -> operation.set(Double.parseDouble(x0) + Double.parseDouble(x2));
                case "-" -> operation.set(Double.parseDouble(x0) - Double.parseDouble(x2));
                case "*" -> operation.set(Double.parseDouble(x0) * Double.parseDouble(x2));
                case "/" -> operation.set(Double.parseDouble(x0) / Double.parseDouble(x2));
            }

            if(operation.get() == null){
                JOptionPane.showMessageDialog(this,"Null","Alert",JOptionPane.ERROR_MESSAGE);
                return;
            }
            textField.setText(x0 + x1 + x2 + "=" + operation.get());
            x0 = Double.toString(operation.get());
            x1 = x2 = "";
        } else {
            if (x1.equals("") || x2.equals("")) {
                x1 = command;
            } else {
                AtomicReference<Double> operation = new AtomicReference<>();
                switch (x1) {
                    case "+" -> operation.set(Double.parseDouble(x0) + Double.parseDouble(x2));
                    case "-" -> operation.set(Double.parseDouble(x0) - Double.parseDouble(x2));
                    case "*" -> operation.set(Double.parseDouble(x0) * Double.parseDouble(x2));
                    case "/" -> operation.set(Double.parseDouble(x0) / Double.parseDouble(x2));
                }
                if(operation.get() == null){
                    JOptionPane.showMessageDialog(this,"Null","Alert",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                x0 = Double.toString(operation.get());
                x1 = command;
                x2 = "";
            }
            textField.setText(x0 + x1 + x2);
        }
    }

    public static void main(String[] args) {
        new JCalculator();
    }
}
