import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class ShamirSecretSharing {
    private final BigInteger prime;
    private final SecureRandom random;

    public ShamirSecretSharing(BigInteger prime) {
        this.prime = prime;
        this.random = new SecureRandom();
    }

    public List<BigInteger[]> splitSecret(BigInteger secret, int n, int t) {
        if (n < t) {
            throw new IllegalArgumentException("Error: n must be greater than or equal to t.");
        }

        List<BigInteger[]> shares = new ArrayList<>();
        BigInteger[] coefficients = new BigInteger[t - 1];

        for (int i = 0; i < t - 1; i++) {
            coefficients[i] = new BigInteger(prime.bitLength(), random).mod(prime);
        }

        for (int i = 1; i <= n; i++) {
            BigInteger x = BigInteger.valueOf(i);
            BigInteger y = secret;

            for (int j = 0; j < t - 1; j++) {
                y = y.add(coefficients[j].multiply(x.pow(j + 1))).mod(prime);
            }
            shares.add(new BigInteger[]{x, y});
        }
        return shares;
    }
}
