import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) { // 5 4 153 5442 87 60 81 15 123
        shamirSecretShares();
        ShamirSecretRecovery();
    }

    private static void shamirSecretShares() {
        // Get Inputs
        Scanner scanner = new Scanner(System.in);

        // Receive inputs from the user
        System.out.print("Enter the number of shares (n): ");
        int n = scanner.nextInt();

        System.out.print("Enter the number of people (t): ");
        int t = scanner.nextInt();

        System.out.print("Enter the calculation unit (p): ");
        int p = scanner.nextInt();

        System.out.print("Enter the secret (as a number): ");
        BigInteger secret = new BigInteger(scanner.next());

        // Generate Shares
        ShamirSecretSharing shamirSecretSharing = new ShamirSecretSharing(secret, n, t, p);

        // Show Shares
        BigInteger[] shares = shamirSecretSharing.getShares();
        System.out.println("Shares : ");
        for (int i = 1; i <= n; i++) {
            System.out.println("Share " + i + ": " + shares[i - 1]);
        }
    }

    private static void ShamirSecretRecovery() {
        Scanner scanner = new Scanner(System.in);

        // Receive inputs from the user
        System.out.print("Enter the number of shares (n): ");
        int n = scanner.nextInt();

        System.out.print("Enter the number of people (t): ");
        int t = scanner.nextInt();

        System.out.print("Enter the calculation unit (p): ");
        int p = scanner.nextInt();

        // Get Shares
        BigInteger[] shares = new BigInteger[n];
        for (int i = 1; i <= n; i++) {
            System.out.print("Share " + i + " : ");
            shares[i - 1] = new BigInteger(scanner.next());
        }

        // Reconstruction of the secret
        ShamirSecretRecovery shamirSecretRecovery = new ShamirSecretRecovery(shares, t, p);
        BigInteger secret = shamirSecretRecovery.getSecret();

        // secret show
        if (secret != null) {
            System.out.println("Secret: " + secret);
        } else {
            System.out.println("It is not possible to restore the secret with these shares.");
        }
    }
}

//Share 1: 789
//Share 2: 312
//Share 3: 303
//Share 4: 496
//Share 5: 625
