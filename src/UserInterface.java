import java.math.BigInteger;
import java.util.Scanner;

public class UserInterface {
    public static void main() {
        System.out.println("**************************************************\n"
                + "*      Welcome to Shamir Secret Scheme (SSS)     *\n"
                + "*                   Professor                    *\n"
                + "*                 Dr. Hamid Mala                 *\n"
                + "*                   Programmer                   *\n"
                + "*          Eng. Sayyed Hossein Hosseini          *\n"
                + "**************************************************");
        executive();
    }

    private static void executive() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n**************************************************\n"
                    + "*                   *  Menu  *                   *\n"
                    + "* (1) ->       Shamir Secret Shares              *\n"
                    + "* (2) ->       Shamir Secret Recovery            *\n"
                    + "* (3) ->               Exit                      *\n"
                    + "**************************************************\n");

            System.out.print("Choose your option from the menu : ");

            switch (scanner.nextInt()) {
                case 1:
                    shamirSecretShares();
                    break;

                case 2:
                    ShamirSecretRecovery();
                    break;

                case 3:
                    System.exit(0);

                default:
                    System.out.println("");
            }
        }
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
