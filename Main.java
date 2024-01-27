import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            int choice;

            do {
                System.out.println("Choose the CPU scheduling algorithm:");
                System.out.println("1. Non-Preemptive SJF");
                System.out.println("2. Preemptive SJF");
                System.out.println("0. Exit");

                try {
                    choice = input.nextInt();

                    switch (choice) {
                        case 1:
                            performSJFScheduling(input);
                            break;
                        case 2:
                            performPreemptiveSJFScheduling(input);
                            break;
                        case 0:
                            System.out.println("Exiting the program. Goodbye!");
                            break;
                        default:
                            System.out.println("Invalid choice. Please choose again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    input.nextLine(); // Clear the invalid input from the buffer
                    choice = -1; // Set to -1 to loop again
                }
            } while (choice != 0);
        }
    }

    private static void performSJFScheduling(Scanner input) {
        System.out.println("Enter number of processes:");
        int n = input.nextInt();

        if (n <= 0) {
            System.out.println("Invalid number of processes. Exiting.");
            return;
        }

        int arrivalTime[] = new int[n];
        int burstTime[] = new int[n];

        System.out.println("Enter Burst Time and Arrival Time:");

        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + " Burst Time: ");
            burstTime[i] = input.nextInt();

            if (burstTime[i] < 0) {
                System.out.println("Invalid burst time. Please enter a non-negative value.");
                return;
            }

            System.out.print("P" + (i + 1) + " Arrival Time: ");
            arrivalTime[i] = input.nextInt();

            if (arrivalTime[i] < 0) {
                System.out.println("Invalid arrival time. Please enter a non-negative value.");
                return;
            }
        }

        SJF sjf = new SJF();
        sjf.performSJFScheduling(n, arrivalTime, burstTime);
    }

    private static void performPreemptiveSJFScheduling(Scanner input) {
        System.out.println("Enter number of processes:");
        int n = input.nextInt();

        if (n <= 0) {
            System.out.println("Invalid number of processes. Exiting.");
            return;
        }

        int arrivalTime[] = new int[n];
        int burstTime[] = new int[n];

        System.out.println("Enter Burst Time and Arrival Time:");

        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + " Burst Time: ");
            burstTime[i] = input.nextInt();

            if (burstTime[i] < 0) {
                System.out.println("Invalid burst time. Please enter a non-negative value.");
                return;
            }

            System.out.print("P" + (i + 1) + " Arrival Time: ");
            arrivalTime[i] = input.nextInt();

            if (arrivalTime[i] < 0) {
                System.out.println("Invalid arrival time. Please enter a non-negative value.");
                return;
            }
        }

        PSJF psjf = new PSJF();
        psjf.performPreemptiveSJFScheduling(n, arrivalTime, burstTime);
    }
}
