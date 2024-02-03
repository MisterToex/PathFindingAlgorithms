package org.toex.dev.app.gfx.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class InputFrame extends JFrame{

    private JPanel panel;

    private GridLayout layout;

    private HashMap<String, JTextField> parameters;

    private JButton calculateButton;

    public InputFrame(String title) {
        super(title);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setLayout(new BorderLayout());

        panel = new JPanel();
        layout = new GridLayout(1, 2, 5, 5);
        panel.setLayout(layout); // 3 rows, 2 columns, with gaps

        // Add a general margin to the entire panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // top, left, bottom, right

        // Add components to the panel
        panel.add(new JLabel()); // Empty cell for spacing

        calculateButton = new JButton("Calculate");
        panel.add(calculateButton);

        super.add(panel, BorderLayout.CENTER);
        super.setSize(300, 20 + layout.getRows() * 40 + (layout.getRows() - 1) * layout.getVgap()); // Adjust the size as needed
        super.setLocationRelativeTo(this);
        super.setVisible(true);
        super.pack();

        parameters = new HashMap<>();
    }

    public void addParam(String paramName) {
        JLabel paramLabel = new JLabel(paramName+ ": ");
        JTextField editorPane = new JTextField();
        parameters.put(paramName, editorPane);
        layout.setRows(layout.getRows()+1);
        panel.add(paramLabel, panel.getComponents().length-2);
        panel.add(editorPane, panel.getComponents().length-2);
        super.setSize(300, 20 + layout.getRows() * 40 + (layout.getRows() - 1) * layout.getVgap()); // Adjust the size as needed
        super.pack();
    }

    public String getParamValue(String paramName) {
        return parameters.get(paramName).getText();
    }

    public void addActionLister(ActionListener actionListener) {
        calculateButton.addActionListener(actionListener);
    }
}
