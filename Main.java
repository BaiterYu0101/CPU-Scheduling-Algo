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
                    generateGanttChart(psjf.getGanttChartOutput(), psjf.getModifiedSchedulingGUI());
                    populateTable(psjf.getOutput(), psjf.getModifiedSchedulingGUI());
                    break;
                case SchedulingGUI.NON_PREEMPTIVE_PRIORITY_SJF:
                    PP pp = new PP(schedulingGUI);
                    generateGanttChart(pp.getGanttChartOutput(), pp.getModifiedSchedulingGUI());
                    populateTable(pp.getOutput(), pp.getModifiedSchedulingGUI());
                    break;
                case SchedulingGUI.ROUND_ROBIN:
                    RoundRobin roundRobin = new RoundRobin(schedulingGUI);
                    RoundRobin.generateGanttChart(roundRobin.getGanttChartOutput(), roundRobin.getModifiedSchedulingGUI());
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
        String[] header = { "Gantt Chart" };
        schedulingGUI.setGanttChartHeader(header);

        StringBuilder ganttChart = new StringBuilder();
        StringBuilder ganttNumbers = new StringBuilder();

        for (int i = 1; i < lines.length; i++) {
            String[] rowData = lines[i].split("\\|");
            int columnWidth = 2;

            for (int j = 0; j < rowData.length; j++) {
                String cell = rowData[j].trim();
                if (!cell.isEmpty()) {
                    String[] processInfo = cell.split(":");
                    int processId = Integer.parseInt(processInfo[0].substring(1));
                    int startTime = Integer.parseInt(processInfo[1].split("-")[0]);
                    int endTime = Integer.parseInt(processInfo[1].split("-")[1]);

                    int duration = endTime - startTime;
                    columnWidth = Math.max(columnWidth, duration + 2);

                    if (ganttChart.length() == 0) {
                        ganttChart.append(String.format("P%-" + columnWidth + "s |", processId));
                    } else {
                        ganttChart.append(String.format("P%-" + columnWidth + "s |", processId));
                    }

                    if (j == 1) {
                        ganttNumbers.append(
                                String.format(" %-" + columnWidth + "s  %-" + columnWidth + "s ", startTime, endTime));
                    } else {
                        ganttNumbers.append(String.format(" %-" + columnWidth + "s ", endTime));
                    }
                }
            }

            if (i < lines.length - 1) {
                ganttChart.append("|");
                ganttNumbers.append(" ");
            }
        }

        schedulingGUI.addGanttChartRow(new Object[] { ganttChart.toString() });
        schedulingGUI.addGanttChartRow(new Object[] { ganttNumbers.toString() });
    }
}
