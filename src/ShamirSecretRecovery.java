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
                    // Ensure positive difference for modular inverse calculation
                    BigInteger difference = shares[j].subtract(BigInteger.valueOf(i));

                    // Consider using a library for extendedEuclidean
                    BigInteger inverse = extendedEuclidean(difference, BigInteger.valueOf(p)).getFirst();

                    // Check if the inverse is null (not possible to recover)
                    if (inverse == null) {
                        System.out.println("Secret reconstruction is not possible with these shares (b is not prime).");
                        return null;
                    }

                    // Update the coefficient with the product of inverse and current coefficient
                    coefficients[i] = coefficients[i].multiply(inverse).mod(BigInteger.valueOf(p));
                }
            }
        }

        // Reconstruction of the secret
        BigInteger secret = BigInteger.ZERO;
        for (int i = 0; i < t; i++) {
            // Calculate the product of other Langerage coefficients
            BigInteger product = coefficients[i];
            for (int j = 0; j < t; j++) {
                if (i != j) {
                    product = product.multiply(coefficients[j]).mod(BigInteger.valueOf(p));
                }
            }

            // Add the product of share and product of coefficients to the secret
            secret = secret.add(shares[i].multiply(product).mod(BigInteger.valueOf(p))).mod(BigInteger.valueOf(p));
        }

        return secret;
    }

    public BigInteger getSecret() {
        return secret;
    }

    // Implementation of extended Euclid algorithm
    private static Pair<BigInteger, BigInteger> extendedEuclidean(BigInteger a, BigInteger b) {
        BigInteger q, r, x, y, x0, y0;

        x0 = BigInteger.ONE;
        y0 = BigInteger.ZERO;
        x = BigInteger.ZERO;
        y = BigInteger.ONE;

        while (!b.equals(BigInteger.ZERO)) {
            q = a.divide(b);
            r = a.subtract(q.multiply(b));

            x = y;
            y = y0.subtract(q.multiply(x0));

            a = b;
            b = r;

            x0 = x;
            y0 = y;
        }

        return new Pair<>(y, x0); // (inverse b, x)
    }

    // Class to store x and y values
    private static class Pair<T1, T2> {
        private final T1 first;
        private final T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        public T1 getFirst() {
            return first;
        }

        public T2 getSecond() {
            return second;
        }
    }
}
