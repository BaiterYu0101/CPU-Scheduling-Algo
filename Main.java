import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SchedulingGUI schedulingGUI = new SchedulingGUI();

        schedulingGUI.setExecuteButtonActionListener(e -> {
            int selectedAlgorithm = schedulingGUI.getSelectedAlgorithm();

            switch (selectedAlgorithm) {
                case SchedulingGUI.NON_PREEMPTIVE_SJF:
                    SJF sjf = new SJF(schedulingGUI);
                    populateTable(sjf.getOutput(), sjf.getModifiedSchedulingGUI());
                    break;
                case SchedulingGUI.PREEMPTIVE_SJF:
                    PSJF psjf = new PSJF(schedulingGUI);
                    populateTable(psjf.getOutput(), psjf.getModifiedSchedulingGUI());
                    break;
                case SchedulingGUI.NON_PREEMPTIVE_PRIORITY_SJF:
                    PP pp = new PP(schedulingGUI);
                    populateTable(pp.getOutput(), pp.getModifiedSchedulingGUI());
                    break;
                case SchedulingGUI.ROUND_ROBIN:
                    RoundRobin roundRobin = new RoundRobin(schedulingGUI);
                    generateGanttChart(roundRobin.getGanttChartOutput(), roundRobin.getModifiedSchedulingGUI());
                    populateTable(roundRobin.getOutput(), roundRobin.getModifiedSchedulingGUI());
                    break;
                default:
                    schedulingGUI.showMessage("Invalid choice. Please choose again.");
            }
        });
    }

    private static void populateTable(String result, SchedulingGUI schedulingGUI) {
        String[] lines = result.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String[] rowData = lines[i].split("\\s+");
            schedulingGUI.addTableRow(rowData);
        }
    }

    private static void generateGanttChart(String ganttChartOutput, SchedulingGUI schedulingGUI) {
        String[] lines = ganttChartOutput.split("\n");
        String[] headers = lines[0].split("\\s+");
        schedulingGUI.setGanttChartHeaders(Arrays.copyOfRange(headers, 1, headers.length));

        for (int i = 1; i < lines.length; i++) {
            String[] rowData = lines[i].split("\\s+");
            schedulingGUI.addGanttChartRow(rowData);
        }
    }
}
