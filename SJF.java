public class SJF {
    private String output;
    private SchedulingGUI modifiedSchedulingGUI;

    public SJF(SchedulingGUI schedulingGUI) {
        schedulingGUI.clearTable();
        schedulingGUI.clearGanttChart();

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
        output = performSJFScheduling(n, arrivalTime, burstTime);
        modifiedSchedulingGUI = schedulingGUI;
    }

    public String performSJFScheduling(int n, int[] arrivalTime, int[] burstTime) {
        int pid[] = new int[n];
        int ct[] = new int[n];
        int ta[] = new int[n];
        int wt[] = new int[n];
        int f[] = new int[n];
        int st = 0, tot = 0;
        float avgwt = 0, avgta = 0;

        for (int i = 0; i < n; i++) {
            pid[i] = i;
            f[i] = 0;
        }

        StringBuilder result = new StringBuilder("pid  arrival burst  complete turn waiting\n");

        while (true) {
            int c = n, min = 999;

            if (tot == n) {
                break;
            }

            for (int i = 0; i < n; i++) {
                if ((arrivalTime[i] <= st) && (f[i] == 0) && (burstTime[i] < min)) {
                    min = burstTime[i];
                    c = i;
                }
            }

            if (c == n) {
                st++;
            } else {
                ct[c] = st + burstTime[c];
                st += burstTime[c];
                ta[c] = ct[c] - arrivalTime[c];
                wt[c] = ta[c] - burstTime[c];
                f[c] = 1;
                tot++;
            }
        }

        for (int i = 0; i < n; i++) {
            avgwt += wt[i];
            avgta += ta[i];
            result.append(pid[i]).append("\t").append(arrivalTime[i]).append("\t").append(burstTime[i])
                    .append("\t").append(ct[i]).append("\t").append(ta[i]).append("\t").append(wt[i]).append("\n");
        }

        result.append("\naverage tat is ").append(avgta / n).append("\n");
        result.append("average wt is ").append(avgwt / n).append("\n");

        return result.toString();
    }

    public String getOutput() {
        return output;
    }

    public SchedulingGUI getModifiedSchedulingGUI() {
        return modifiedSchedulingGUI;
    }
}
