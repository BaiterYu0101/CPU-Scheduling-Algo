import java.util.Arrays;

public class PSJF {
    private String output;
    private SchedulingGUI modifiedSchedulingGUI;
    private String ganttChartOutput;

    public PSJF(SchedulingGUI schedulingGUI) {
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
            burstTime[i] = schedulingGUI.getIntInput("P" + (i) + " Burst Time: ");

            while (burstTime[i] < 0) {
                schedulingGUI.showMessage("Invalid burst time. Please enter a non-negative value.");
                burstTime[i] = schedulingGUI.getIntInput("P" + (i) + " Burst Time: ");
            }

            arrivalTime[i] = schedulingGUI.getIntInput("P" + (i) + " Arrival Time: ");

            while (arrivalTime[i] < 0) {
                schedulingGUI.showMessage("Invalid arrival time. Please enter a non-negative value.");
                arrivalTime[i] = schedulingGUI.getIntInput("P" + (i) + " Arrival Time: ");
            }
        }

        schedulingGUI.setTableHeaders(new String[]{"Process", "Arrival Time", "Burst Time", "Complete Time", "Turnaround Time", "Waiting Time"});
        output = performPreemptiveSJFScheduling(n, arrivalTime, burstTime);
        modifiedSchedulingGUI = schedulingGUI;
    }

    private String performPreemptiveSJFScheduling(int n, int[] arrivalTime, int[] burstTime) {
        StringBuilder result = new StringBuilder("pid\tarrival\tburst\tcomplete\tturn\twaiting\n");

        int pid[] = new int[n];
        int ct[] = new int[n];
        int ta[] = new int[n];
        int wt[] = new int[n];
        int f[] = new int[n];
        int k[] = Arrays.copyOf(burstTime, n);
        int st = 0, tot = 0;
        float avgwt = 0, avgta = 0;

        // Gantt chart related variables
        StringBuilder ganttChart = new StringBuilder("Gantt Chart:\n");
        int currentTime = 0;

        while (true) {
            int min = Integer.MAX_VALUE, c = n;
            if (tot == n) {
                break;
            }

            for (int i = 0; i < n; i++) {
                if ((arrivalTime[i] <= currentTime) && (f[i] == 0) && (burstTime[i] < min)) {
                    min = burstTime[i];
                    c = i;
                }
            }

            if (c == n) {
                currentTime++;
            } else {
                burstTime[c]--;
                currentTime++;
                if (burstTime[c] == 0) {
                    ct[c] = currentTime;
                    f[c] = 1;
                    tot++;
                }
                // Update Gantt chart
                ganttChart.append("|P").append(c).append(":").append(currentTime - 1).append("-").append(currentTime).append("|");
            }
        }

        for (int i = 0; i < n; i++) {
            pid[i] = i;
            ta[i] = ct[i] - arrivalTime[i];
            wt[i] = ta[i] - k[i];
            avgwt += wt[i];
            avgta += ta[i];
            result.append(pid[i]).append("\t").append(arrivalTime[i]).append("\t").append(k[i]).append("\t")
                    .append(ct[i]).append("\t").append(ta[i]).append("\t").append(wt[i]).append("\n");
        }

        result.append("\naverage tat is ").append(avgta / n).append("\n");
        result.append("average wt is ").append(avgwt / n).append("\n");

        // Set Gantt chart output
        ganttChartOutput = ganttChart.toString();

        return result.toString();
    }

    public String getOutput() {
        return output;
    }

    public SchedulingGUI getModifiedSchedulingGUI() {
        return modifiedSchedulingGUI;
    }

    public String getGanttChartOutput() {
        return ganttChartOutput;
    }
}
