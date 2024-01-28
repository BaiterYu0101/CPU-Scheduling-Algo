import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchedulingGUI {
    public static final int NON_PREEMPTIVE_SJF = 1;
    public static final int PREEMPTIVE_SJF = 2;
    public static final int EXIT = 0;

    private JFrame frame;
    private JComboBox<String> algorithmComboBox;
    private JButton executeButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public SchedulingGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        algorithmComboBox = new JComboBox<>(new String[]{"Select Algorithm", "Non-Preemptive SJF", "Preemptive SJF", "Exit"});
        frame.getContentPane().add(algorithmComboBox, BorderLayout.NORTH);

        executeButton = new JButton("Execute");
        frame.getContentPane().add(executeButton, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void setExecuteButtonActionListener(ActionListener actionListener) {
        executeButton.addActionListener(actionListener);
    }

    public int getSelectedAlgorithm() {
        return algorithmComboBox.getSelectedIndex();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public int getIntInput() {
        String input = JOptionPane.showInputDialog(frame, "Enter a number:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getIntInput(String prompt) {
        String input = JOptionPane.showInputDialog(frame, prompt);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void setTableHeaders(String[] headers) {
        tableModel.setColumnIdentifiers(headers);
    }

    public void addTableRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }
}
