import java.util.LinkedList;
import java.util.Queue;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int remainingTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        int[] arrivalTimes = {0, 1, 5, 6, 7, 8};
        int[] burstTimes = {6, 4, 6, 6, 6, 6};
        int quantum = 3;

        roundRobinScheduling(arrivalTimes, burstTimes, quantum);
    }

    public static void roundRobinScheduling(int[] arrivalTimes, int[] burstTimes, int quantum) {
        int n = arrivalTimes.length; // total number of processes
        Queue<Process> processQueue = new LinkedList<>();

        int[] finishTimes = new int[n];
        int[] turnaroundTimes = new int[n];
        int[] waitingTimes = new int[n];

        int currentTime = 0;
        int processedCount = 0;

        while (processedCount < n) {
            boolean processExecuted = false;

            for (int i = 0; i < n; i++) {
                if (arrivalTimes[i] <= currentTime && burstTimes[i] > 0) {
                    Process currentProcess = new Process(i, arrivalTimes[i], burstTimes[i]);
                    int executionTime = Math.min(currentProcess.remainingTime, quantum);

                    // Update finish time
                    currentTime += executionTime;
                    finishTimes[currentProcess.id] = currentTime;

                    // Update remaining time
                    currentProcess.remainingTime -= executionTime;

                    // Update waiting time
                    waitingTimes[currentProcess.id] = currentTime - currentProcess.arrivalTime - currentProcess.burstTime;

                    // Update turnaround time
                    turnaroundTimes[currentProcess.id] = currentTime - currentProcess.arrivalTime;

                    if (currentProcess.remainingTime == 0) {
                        processedCount++;
                    } else {
                        processQueue.offer(currentProcess);
                    }

                    processExecuted = true;
                }
            }

            if (!processExecuted) {
                // No process executed in the current cycle, move to the next arrival time
                int nextArrivalTime = Integer.MAX_VALUE;
                for (int i = 0; i < n; i++) {
                    if (burstTimes[i] > 0 && arrivalTimes[i] < nextArrivalTime) {
                        nextArrivalTime = arrivalTimes[i];
                    }
                }
                currentTime = Math.max(currentTime, nextArrivalTime);
            }
        }

        // Print table
        System.out.println("Process\tArrival Time\tBurst Time\tFinish Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.printf("%d\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n",
                    i, arrivalTimes[i], burstTimes[i], finishTimes[i], turnaroundTimes[i], waitingTimes[i]);
        }
    }
}
