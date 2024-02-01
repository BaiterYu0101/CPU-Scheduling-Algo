import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SchedulingGUI schedulingGUI = new SchedulingGUI();

        schedulingGUI.setExecuteButtonActionListener(e -> {
            int selectedAlgorithm = schedulingGUI.getSelectedAlgorithm();

            switch (selectedAlgorithm) {
                case SchedulingGUI.NON_PREEMPTIVE_SJF:
                    performSJFScheduling(schedulingGUI);
                    break;
                case SchedulingGUI.PREEMPTIVE_SJF:
                    performPreemptiveSJFScheduling(schedulingGUI);
                    break;
                case SchedulingGUI.NON_PREEMPTIVE_PRIORITY_SJF:
                    performNonPreemptiveScheduling(schedulingGUI);
                    break;
                case SchedulingGUI.EXIT:
                    schedulingGUI.showMessage("Exiting the program. Goodbye!");
                    break;
                default:
                    schedulingGUI.showMessage("Invalid choice. Please choose again.");
            }
        });
    }

    private static void performNonPreemptiveScheduling(SchedulingGUI schedulingGUI) {
        schedulingGUI.clearTable();

        schedulingGUI.showMessage("Enter number of processes:");
        int n = schedulingGUI.getIntInput();

        if (n <= 0) {
            schedulingGUI.showMessage("Invalid number of processes. Exiting.");
            return;
        }

        int arrivalTime[] = new int[n];
        int burstTime[] = new int[n];
        int priority[] = new int[n];

        schedulingGUI.showMessage("Enter Burst Time, Arrival Time, and Priority:");

        for (int i = 0; i < n; i++) {
            burstTime[i] = schedulingGUI.getIntInput("P" + (i + 1) + " Burst Time: ");

            if (burstTime[i] < 0) {
                schedulingGUI.showMessage("Invalid burst time. Please enter a non-negative value.");
                return;
            }

            arrivalTime[i] = schedulingGUI.getIntInput("P" + (i + 1) + " Arrival Time: ");

            if (arrivalTime[i] < 0) {
                schedulingGUI.showMessage("Invalid arrival time. Please enter a non-negative value.");
                return;
            }

            priority[i] = schedulingGUI.getIntInput("P" + (i + 1) + " Priority: ");
        }

        // Use the fully qualified name if the PP class is in a different package
        // PP pp = new package_name.PP();
        PP pp = new PP();
        String result = pp.performNonPreemptiveScheduling(n, arrivalTime, burstTime, priority);

        schedulingGUI.setTableHeaders(new String[]{"pid", "arrival", "burst", "priority", "complete", "turn", "waiting"});
        populateTable(result, schedulingGUI);
    }

    private static void performSJFScheduling(SchedulingGUI schedulingGUI) {
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

            if (burstTime[i] < 0) {
                schedulingGUI.showMessage("Invalid burst time. Please enter a non-negative value.");
                return;
            }

            arrivalTime[i] = schedulingGUI.getIntInput("P" + (i + 1) + " Arrival Time: ");

            if (arrivalTime[i] < 0) {
                schedulingGUI.showMessage("Invalid arrival time. Please enter a non-negative value.");
                return;
            }
        }

        SJF sjf = new SJF();
        String result = sjf.performSJFScheduling(n, arrivalTime, burstTime);

        schedulingGUI.setTableHeaders(new String[]{"pid", "arrival", "burst", "complete", "turn", "waiting"});
        populateTable(result, schedulingGUI);
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

            if (burstTime[i] < 0) {
                schedulingGUI.showMessage("Invalid burst time. Please enter a non-negative value.");
                return;
            }

            arrivalTime[i] = schedulingGUI.getIntInput("P" + (i + 1) + " Arrival Time: ");

            if (arrivalTime[i] < 0) {
                schedulingGUI.showMessage("Invalid arrival time. Please enter a non-negative value.");
                return;
            }
        }

        PSJF psjf = new PSJF();
        String result = psjf.performPreemptiveSJFScheduling(n, arrivalTime, burstTime);

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
