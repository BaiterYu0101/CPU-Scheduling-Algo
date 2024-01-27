import java.util.Scanner;

public class SJF {

    public void performSJFScheduling(int n, int[] arrivalTime, int[] burstTime) {
        int pid[] = new int[n];
        int ct[] = new int[n];
        int ta[] = new int[n];
        int wt[] = new int[n];
        int f[] = new int[n];
        int st = 0, tot = 0;
        float avgwt = 0, avgta = 0;

        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            f[i] = 0;
        }

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

        System.out.println("\npid  arrival burst  complete turn waiting");
        for (int i = 0; i < n; i++) {
            avgwt += wt[i];
            avgta += ta[i];
            System.out.println(pid[i] + "\t" + arrivalTime[i] + "\t" + burstTime[i] + "\t" + ct[i] + "\t" + ta[i] + "\t" + wt[i]);
        }

        System.out.println("\naverage tat is " + (float) (avgta / n));
        System.out.println("average wt is " + (float) (avgwt / n));
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter no of process:");
        int n = sc.nextInt();
        int arrivalTime[] = new int[n];
        int burstTime[] = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.println("enter process " + (i + 1) + " arrival time:");
            arrivalTime[i] = sc.nextInt();
            System.out.println("enter process " + (i + 1) + " burst time:");
            burstTime[i] = sc.nextInt();
        }

        SJF sjf = new SJF();
        sjf.performSJFScheduling(n, arrivalTime, burstTime);

        sc.close();
    }
}
