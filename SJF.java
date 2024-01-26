import java.util.Arrays;

public class SJF {
    private int processId;
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int turnaroundTime;

    public SJF(int processId, int burstTime, int arrivalTime) {
        this.processId = processId;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }

    public int getProcessId() {
        return processId;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public static void performSJFScheduling(SJF[] processes) {
        int n = processes.length;
        int total = 0;

        Arrays.sort(processes, (a, b) -> {
            if (a.getArrivalTime() != b.getArrivalTime()) {
                return Integer.compare(a.getArrivalTime(), b.getArrivalTime());
            } else {
                return Integer.compare(a.getBurstTime(), b.getBurstTime());
            }
        });

        processes[0].setWaitingTime(0);

        for (int i = 1; i < n; i++) {
            processes[i].setWaitingTime(0);
            for (int j = 0; j < i; j++) {
                processes[i].setWaitingTime(processes[i].getWaitingTime() + processes[j].getBurstTime());
            }
            total += processes[i].getWaitingTime();
        }

        float avg_wt = (float) total / n;
        total = 0;

        System.out.println("P\tAT\tBT\tWT\tTAT");
        for (int i = 0; i < n; i++) {
            processes[i].setTurnaroundTime(processes[i].getBurstTime() + processes[i].getWaitingTime());
            total += processes[i].getTurnaroundTime();
            System.out.println("P" + processes[i].getProcessId() + "\t"
                    + processes[i].getArrivalTime() + "\t" + processes[i].getBurstTime()
                    + "\t" + processes[i].getWaitingTime() + "\t" + processes[i].getTurnaroundTime());
        }

        float avg_tat = (float) total / n;
        System.out.println("Average Waiting Time= " + avg_wt);
        System.out.println("Average Turnaround Time= " + avg_tat);
    }
}
