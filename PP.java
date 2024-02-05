//import java.util.Scanner;

public class PP {
    private String result;
    private SchedulingGUI modifiedSchedulingGUI;

    public PP(SchedulingGUI schedulingGUI) {
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

            priority[i] = schedulingGUI.getIntInput("P" + (i) + " Priority: ");
        }

        schedulingGUI.setTableHeaders(new String[]{"Process", "Arrival Time", "Burst Time", "Priority", "Finishing Time", "Turnaround Time", "Waiting Time"});

        result = performNonPreemptiveScheduling(n, arrivalTime, burstTime, priority);
        modifiedSchedulingGUI = schedulingGUI;
    }

    public String performNonPreemptiveScheduling(int n, int[] arrivalTime, int[] burstTime, int[] priority) {
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

        // Sort processes based on arrival time and priority
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arrivalTime[j] > arrivalTime[j + 1] || 
                   (arrivalTime[j] == arrivalTime[j + 1] && priority[j] > priority[j + 1])) {
                    // Swap arrivalTime
                    int tempArrival = arrivalTime[j];
                    arrivalTime[j] = arrivalTime[j + 1];
                    arrivalTime[j + 1] = tempArrival;

                    // Swap burstTime
                    int tempBurst = burstTime[j];
                    burstTime[j] = burstTime[j + 1];
                    burstTime[j + 1] = tempBurst;

                    // Swap priority
                    int tempPriority = priority[j];
                    priority[j] = priority[j + 1];
                    priority[j + 1] = tempPriority;

                    // Swap pid
                    int tempPid = pid[j];
                    pid[j] = pid[j + 1];
                    pid[j + 1] = tempPid;
                }
            }
        }

        StringBuilder result = new StringBuilder("Process\tArrival Time\tBurst Time\tPriority\tFinishing Time\tTurnaround Time\tWaitingTime\n");
        while (true) {
            int c = n, highestPriority = 999;

            if (tot == n) {
                break;
            }

            for (int i = 0; i < n; i++) {
                if ((arrivalTime[i] <= st) && (f[i] == 0) && (priority[i] < highestPriority)) {
                    highestPriority = priority[i];
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
                    .append("\t").append(priority[i]).append("\t").append(ct[i]).append("\t").append(ta[i])
                    .append("\t").append(wt[i]).append("\n");
        }

        result.append("\naverage tat is ").append((float) (avgta / n)).append("\n");
        result.append("average wt is ").append((float) (avgwt / n)).append("\n");

        return result.toString();
    }

    public String getOutput() {
        return result;
    }

    public SchedulingGUI getModifiedSchedulingGUI() {
        return modifiedSchedulingGUI;
    }
}
