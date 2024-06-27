import java.math.BigInteger;

public class ShamirSecretRecovery {
    BigInteger secret;

    public ShamirSecretRecovery(BigInteger[] shares, int t, int p) { // ShamirSecretRecovery
        secret = recoverSecret(shares, t, p);
    }

    private static BigInteger recoverSecret(BigInteger[] shares, int t, int p) {
        // Checking whether the number of shares is sufficient or not
        if (shares.length < t) {
            return null;
        }

        // Calculation of Langerage coefficients
        BigInteger[] coefficients = new BigInteger[t];
        for (int i = 0; i < t; i++) {
            coefficients[i] = BigInteger.ONE;
            for (int j = 0; j < t; j++) {
                if (i != j) {
                    coefficients[i] = coefficients[i].multiply(
                            (shares[j].subtract(BigInteger.valueOf(i))).modInverse(BigInteger.valueOf(p))
                    ).mod(BigInteger.valueOf(p));
                }
            }
        }

        // Reconstruction of the secret
        BigInteger secret = BigInteger.ZERO;
        for (int i = 0; i < t; i++) {
            secret = secret.add(
                    shares[i].multiply(coefficients[i]).mod(BigInteger.valueOf(p))
            ).mod(BigInteger.valueOf(p));
        }

        return secret;
    }

    public BigInteger getSecret() {
        return secret;
    }
}
