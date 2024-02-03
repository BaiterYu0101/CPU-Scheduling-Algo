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
        int currentProcessIndex = 0;

        while (currentProcessIndex < n || !processQueue.isEmpty()) {
            // add arrived processes to the queue
            while (currentProcessIndex < n && arrivalTimes[currentProcessIndex] <= currentTime) {
                processQueue.offer(new Process(currentProcessIndex, arrivalTimes[currentProcessIndex], burstTimes[currentProcessIndex]));
                currentProcessIndex++;
            }

            // if the queue is not empty, execute the process
            if (!processQueue.isEmpty()) {
                // get the process from the front
                Process currentProcess = processQueue.poll();

                // testing: print process id
                //System.out.println("Process " + currentProcess.id + " is executing");

                // execute for quantum time
                int executeTime = Math.min(quantum, currentProcess.remainingTime);

                // update remaining time
                currentProcess.remainingTime -= executeTime;
                
                // testing: print remaining time
                //System.out.println("Remaining time: " + currentProcess.remainingTime);

                // update finish time
                currentTime += executeTime;
                finishTimes[currentProcess.id] = currentTime;

                // get next process and execute
                while (currentProcessIndex < n && arrivalTimes[currentProcessIndex] <= currentTime) {
                    processQueue.offer(new Process(currentProcessIndex, arrivalTimes[currentProcessIndex], burstTimes[currentProcessIndex]));
                    currentProcessIndex++;
                }

                // requeue the process if it's not finished
                if (currentProcess.remainingTime > 0) {
                    processQueue.offer(currentProcess);
                }

                // update waiting time
                waitingTimes[currentProcess.id] = currentTime - currentProcess.arrivalTime - currentProcess.burstTime;

                // update turnaround time
                turnaroundTimes[currentProcess.id] = finishTimes[currentProcess.id] - currentProcess.arrivalTime;
            } else {
                currentTime++;
            }
        }

        // print table
        System.out.println("Process\tArrival Time\tBurst Time\tFinish Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.printf("%d\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n",
                    i, arrivalTimes[i], burstTimes[i], finishTimes[i], turnaroundTimes[i], waitingTimes[i]);
        }
    }
}
