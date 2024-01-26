import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            int n;

            System.out.println("Enter number of processes:");
            n = input.nextInt();

                // input.close();

            SJF[] processes = new SJF[n];

            System.out.println("Enter Burst Time and Arrival Time:");
            for (int i = 0; i < n; i++) {
                System.out.print("P" + (i + 1) + " Burst Time: ");
                int burstTime = input.nextInt();
                System.out.print("P" + (i + 1) + " Arrival Time: ");
                int arrivalTime = input.nextInt();
                processes[i] = new SJF(i + 1, burstTime, arrivalTime);
            }

            // Call SJF scheduling logic from SJF class
            SJF.performSJFScheduling(processes);
        }
    }
}
