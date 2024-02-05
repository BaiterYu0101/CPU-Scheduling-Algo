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
    private String output;
    private SchedulingGUI modifiedSchedulingGUI;
    private String ganttChartOutput;

    public RoundRobin(SchedulingGUI schedulingGUI) {
        schedulingGUI.clearTable();

        schedulingGUI.showMessage("Enter number of processes:");
        int n = schedulingGUI.getIntInput();

        if (n <= 0) {
            schedulingGUI.showMessage("Invalid number of processes. Exiting.");
            return;
        }

        int arrivalTimes[] = new int[n];
        int burstTimes[] = new int[n];


        for (int i = 0; i < n; i++) {
            burstTimes[i] = schedulingGUI.getIntInput("P" + i + " Burst Time: ");

            while (burstTimes[i] < 0) {
                schedulingGUI.showMessage("Invalid burst time. Please enter a non-negative value.");
                burstTimes[i] = schedulingGUI.getIntInput("P" + i + " Burst Time: ");
            }

            arrivalTimes[i] = schedulingGUI.getIntInput("P" + i + " Arrival Time: ");

            while (arrivalTimes[i] < 0) {
                schedulingGUI.showMessage("Invalid arrival time. Please enter a non-negative value.");
                arrivalTimes[i] = schedulingGUI.getIntInput("P" + i + " Arrival Time: ");
            }
        }

        int quantum = schedulingGUI.getIntInput("Enter quantum time: ");

        schedulingGUI.setTableHeaders(new String[]{"Process", "Arrival Time", "Burst Time", "Finish Time", "Turnaround Time", "Waiting Time"});

        output = roundRobinScheduling(arrivalTimes, burstTimes, quantum);
        modifiedSchedulingGUI = schedulingGUI;
    }

    public String roundRobinScheduling(int[] arrivalTimes, int[] burstTimes, int quantum) {
        StringBuilder result = new StringBuilder("Process\tArrival Time\tBurst Time\tFinish Time\tTurnaround Time\tWaiting Time\n");
        int n = arrivalTimes.length; // total number of processes
        Queue<Process> processQueue = new LinkedList<>();

        int[] finishTimes = new int[n];
        int[] turnaroundTimes = new int[n];
        int[] waitingTimes = new int[n];

        int currentTime = 0;
        int currentProcessIndex = 0;
        // Gantt chart related variables
        StringBuilder ganttChart = new StringBuilder("Gantt Chart:\n");
        int ganttTime = 0;

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

                // execute for quantum time
                int executeTime = Math.min(quantum, currentProcess.remainingTime);

                // update remaining time
                currentProcess.remainingTime -= executeTime;

                // update finish time
                currentTime += executeTime;
                finishTimes[currentProcess.id] = currentTime;

                // update gantt chart
                ganttChart.append("|P").append(currentProcess.id).append(":").append(ganttTime).append("-").append(ganttTime + executeTime).append("|");

                ganttTime += executeTime;


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
        for (int i = 0; i < n; i++) {
            result.append(i).append("\t").append(arrivalTimes[i]).append("\t\t").append(burstTimes[i]).append("\t\t").append(finishTimes[i]).append("\t\t").append(turnaroundTimes[i]).append("\t\t").append(waitingTimes[i]).append("\n");
        }

        // print gantt chart
        //System.out.println(ganttChart.toString());
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
