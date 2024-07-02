import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("**************************************************\n"
                + "*      Welcome to Shamir Secret Scheme (SSS)     *\n"
                + "*                   Professor                    *\n"
                + "*                 Dr. Hamid Mala                 *\n"
                + "*                   Programmer                   *\n"
                + "*          Eng. Sayyed Hossein Hosseini          *\n"
                + "**************************************************");

        while (true) {
            System.out.println("\n**************************************************\n"
                    + "*                   *  Menu  *                   *\n"
                    + "* (1) ->       Shamir Secret Shares              *\n"
                    + "* (2) ->       Shamir Secret Recovery            *\n"
                    + "* (3) ->               Exit                      *\n"
                    + "**************************************************\n");

            System.out.print("Choose your option from the menu : ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    splitSecretOption(scanner);
                    break;
                case 2:
                    recoverSecretOption(scanner);
                    break;
                case 3:
                    System.out.println("\n**************************************************\n"
                            + "*               I hope you enjoyed               *\n"
                            + "*       ->         GOOD LUCK :)        <-        *\n"
                            + "**************************************************");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void splitSecretOption(Scanner scanner) {
        System.out.println("Enter the prime number p:");
        BigInteger prime = scanner.nextBigInteger();

        System.out.println("Enter the number of shares n:");
        int n = scanner.nextInt();

        System.out.println("Enter the threshold t:");
        int t = scanner.nextInt();

        System.out.println("Enter the secret number s:");
        BigInteger secret = scanner.nextBigInteger();

        if (n < t) {
            System.out.println("Error: n must be greater than or equal to t.");
            return;
        }

        if (!prime.isProbablePrime(10)) {
            System.out.println("Error: p must be a prime number.");
            return;
        }

        if (secret.compareTo(prime) >= 0) {
            System.out.println("Error: s must be less than p.");
            return;
        }

        ShamirSecretSharing sss = new ShamirSecretSharing(prime);
        List<BigInteger[]> shares = sss.splitSecret(secret, n, t);

        System.out.println("Generated Shares:");
        for (BigInteger[] share : shares) {
            System.out.println("(" + share[0] + ", " + share[1] + ")");
        }
    }

    private static void recoverSecretOption(Scanner scanner) {
        System.out.println("Enter the prime number p:");
        BigInteger prime = scanner.nextBigInteger();

        System.out.println("Enter the number of shares t:");
        int t = scanner.nextInt();

        List<BigInteger[]> shares = new ArrayList<>();

        for (int i = 0; i < t; i++) {
            System.out.println("Enter x and y for share " + (i + 1) + ":");
            BigInteger x = scanner.nextBigInteger();
            BigInteger y = scanner.nextBigInteger();
            shares.add(new BigInteger[]{x, y});
        }

        SecretRecovery sr = new SecretRecovery(prime);
        BigInteger reconstructedSecret = sr.reconstructSecret(shares);

        System.out.println("Reconstructed Secret: " + reconstructedSecret);
    }
}
