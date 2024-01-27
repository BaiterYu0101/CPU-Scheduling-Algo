import java.util.Scanner;

public class PSJF {
    public void performPreemptiveSJFScheduling(int n, int[] arrivalTime, int[] burstTime) {
        int pid[] = new int[n];
        int ct[] = new int[n];
        int ta[] = new int[n];
        int wt[] = new int[n];
        int f[] = new int[n];
        int k[] = new int[n];
        int st = 0, tot = 0;
        float avgwt = 0, avgta = 0;

        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            k[i] = burstTime[i];
            f[i] = 0;
        }

        while (true) {
            int min = 99, c = n;
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
                burstTime[c]--;
                st++;
                if (burstTime[c] == 0) {
                    ct[c] = st;
                    f[c] = 1;
                    tot++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            ta[i] = ct[i] - arrivalTime[i];
            wt[i] = ta[i] - k[i];
            avgwt += wt[i];
            avgta += ta[i];
        }

        System.out.println("pid  arrival  burst  complete turn waiting");
        for (int i = 0; i < n; i++) {
            System.out.println(pid[i] + "\t" + arrivalTime[i] + "\t" + k[i] + "\t" + ct[i] + "\t" + ta[i] + "\t" + wt[i]);
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

        PSJF psjf = new PSJF();
        psjf.performPreemptiveSJFScheduling(n, arrivalTime, burstTime);

        sc.close();
    }
}
