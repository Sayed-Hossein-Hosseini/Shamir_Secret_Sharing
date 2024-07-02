import java.math.BigInteger;
import java.util.List;

public class SecretRecovery {
    private final BigInteger prime;

    public SecretRecovery(BigInteger prime) {
        this.prime = prime;
    }

    public BigInteger reconstructSecret(List<BigInteger[]> shares) {
        BigInteger secret = BigInteger.ZERO;

        for (int i = 0; i < shares.size(); i++) {
            BigInteger[] shareI = shares.get(i);
            BigInteger xi = shareI[0];
            BigInteger yi = shareI[1];
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < shares.size(); j++) {
                if (i != j) {
                    BigInteger xj = shares.get(j)[0];
                    numerator = numerator.multiply(xj.negate()).mod(prime);
                    denominator = denominator.multiply(xi.subtract(xj)).mod(prime);
                }
            }
            BigInteger lagrangeCoefficient = numerator.multiply(denominator.modInverse(prime)).mod(prime);
            secret = secret.add(yi.multiply(lagrangeCoefficient)).mod(prime);
        }
        return secret;
    }
}
