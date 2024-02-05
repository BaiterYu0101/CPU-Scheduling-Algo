import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchedulingGUI {
    public static final int NON_PREEMPTIVE_SJF = 1;
    public static final int PREEMPTIVE_SJF = 2;
    public static final int NON_PREEMPTIVE_PRIORITY_SJF = 3;
    public static final int ROUND_ROBIN = 4;
    public static final int EXIT = 0;

    private JFrame frame;
    private JComboBox<String> algorithmComboBox;
    private JButton executeButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JTable ganttChartTable;
    private DefaultTableModel ganttChartModel;

    public SchedulingGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        algorithmComboBox = new JComboBox<>(new String[]{"Select Algorithm", "Non-Preemptive SJF", "Preemptive SJF", "Non-Preemptive Priority", "Round Robin"});
        frame.getContentPane().add(algorithmComboBox, BorderLayout.NORTH);

        executeButton = new JButton("Execute");
        frame.getContentPane().add(executeButton, BorderLayout.SOUTH);

        // center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // add gantt chart
        ganttChartModel = new DefaultTableModel();
        ganttChartTable = new JTable(ganttChartModel);
        JScrollPane ganttChartScrollPane = new JScrollPane(ganttChartTable);
        centerPanel.add(ganttChartScrollPane);

        // add table below gantt chart
        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        centerPanel.add(scrollPane);

        // add center panel to frame
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

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

    public void setGanttChartHeaders(String[] headers) {
        ganttChartModel.setColumnIdentifiers(headers);
    }

    public void addGanttChartRow(Object[] rowData) {
        ganttChartModel.addRow(rowData);
    }

    public void clearGanttChart() {
        ganttChartModel.setRowCount(0);
    }
}
