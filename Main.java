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
                    performPreemptiveSJFScheduling(schedulingGUI);
                    break;
                case SchedulingGUI.NON_PREEMPTIVE_PRIORITY_SJF:
                    PP pp = new PP(schedulingGUI);
                    populateTable(pp.getOutput(), pp.getModifiedSchedulingGUI());
                    break;
                case SchedulingGUI.ROUND_ROBIN:
                    RoundRobin roundRobin = new RoundRobin(schedulingGUI);
                    populateTable(roundRobin.getOutput(), roundRobin.getModifiedSchedulingGUI());
                    break;
                case SchedulingGUI.EXIT:
                    schedulingGUI.showMessage("Exiting the program. Goodbye!");
                    break;
                default:
                    schedulingGUI.showMessage("Invalid choice. Please choose again.");
            }
        });
    }

    private static void performPreemptiveSJFScheduling(SchedulingGUI schedulingGUI) {
        schedulingGUI.clearTable();

        schedulingGUI.showMessage("Enter number of processes:");
        int n = schedulingGUI.getIntInput();

        if (n <= 0) {
            schedulingGUI.showMessage("Invalid number of processes. Exiting.");
            return;
        }

        int arrivalTime[] = new int[n];
        int burstTime[] = new int[n];

        schedulingGUI.showMessage("Enter Burst Time and Arrival Time:");

        for (int i = 0; i < n; i++) {
            burstTime[i] = schedulingGUI.getIntInput("P" + (i + 1) + " Burst Time: ");

            while (burstTime[i] < 0) {
                schedulingGUI.showMessage("Invalid burst time. Please enter a non-negative value.");
                burstTime[i] = schedulingGUI.getIntInput("P" + (i + 1) + " Burst Time: ");
            }

            arrivalTime[i] = schedulingGUI.getIntInput("P" + (i + 1) + " Arrival Time: ");

            while (arrivalTime[i] < 0) {
                schedulingGUI.showMessage("Invalid arrival time. Please enter a non-negative value.");
                arrivalTime[i] = schedulingGUI.getIntInput("P" + (i + 1) + " Arrival Time: ");
            }
        }

        PSJF psjf = new PSJF();
        String result = psjf.performPreemptiveSJFScheduling(n, arrivalTime, burstTime);

        System.out.println(result);

        schedulingGUI.setTableHeaders(new String[]{"pid", "arrival", "burst", "complete", "turn", "waiting"});
        populateTable(result, schedulingGUI);
    }

    private static void populateTable(String result, SchedulingGUI schedulingGUI) {
        String[] lines = result.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String[] rowData = lines[i].split("\\s+");
            schedulingGUI.addTableRow(rowData);
        }
    }
}
