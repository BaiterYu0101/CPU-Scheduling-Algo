import java.util.Scanner;

public class PSJF {
    public String performPreemptiveSJFScheduling(int n, int[] arrivalTime, int[] burstTime) {
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

        StringBuilder result = new StringBuilder("pid  arrival  burst  complete turn waiting\n");

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
            result.append(pid[i]).append("\t").append(arrivalTime[i]).append("\t").append(k[i]).append("\t")
                    .append(ct[i]).append("\t").append(ta[i]).append("\t").append(wt[i]).append("\n");
        }

        result.append("\naverage tat is ").append((float) (avgta / n)).append("\n");
        result.append("average wt is ").append((float) (avgwt / n)).append("\n");

        return result.toString();
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
        String result = psjf.performPreemptiveSJFScheduling(n, arrivalTime, burstTime);
        System.out.println(result);

        sc.close();
    }
}
