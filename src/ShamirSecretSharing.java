import java.math.BigInteger;
import java.util.Random;

public class ShamirSecretSharing {
    private BigInteger[] shares;
    BigInteger secret;

    public ShamirSecretSharing(BigInteger secret, int n, int t, int p) {
        shares = generateShares(secret, n, t, p);
    }

    private static BigInteger[] generateShares(BigInteger secret, int n, int t, int p) {
        BigInteger[] shares = new BigInteger[n];
        Random random = new Random();

        // Selection of random coefficients
        BigInteger[] coefficients = new BigInteger[t - 1];
        for (int i = 0; i < t - 1; i++) {
            coefficients[i] = new BigInteger(p, random);
        }

        // Calculation of shares
        for (int i = 1; i <= n; i++) {
            BigInteger share = secret;
            for (int j = 1; j < t; j++) {
                share = share.multiply(BigInteger.valueOf(i).modPow(BigInteger.valueOf(j), BigInteger.valueOf(p)));
            }
            shares[i - 1] = share.mod(BigInteger.valueOf(p));
        }

        return shares;
    }

    private static BigInteger recoverSecret(BigInteger[] shares, int t, int p) {
        // Checking whether the number of shares is sufficient or not
        if (shares.length < t) {
            return null;
        }
        return null;
    }

    public BigInteger[] getShares() {
        return shares;
    }
}
