import java.math.BigInteger;
import java.util.Random;

public class ShamirSecretSharing {
    private static BigInteger[] generateShares(BigInteger secret, int n, int t, int p) {
        BigInteger[] shares = new BigInteger[n];
        Random random = new Random();

        // Selection of random coefficients
        BigInteger[] coefficients = new BigInteger[t - 1];
        for (int i = 0; i < t - 1; i++) {
            coefficients[i] = new BigInteger(p, random);
        }



        return shares;
    }
}
